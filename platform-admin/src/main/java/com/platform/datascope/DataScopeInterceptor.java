/*
 * 项目名称:platform-plus
 * 类名称:DataScopeInterceptor.java
 * 包名称:com.platform.datascope
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2019/2/14 09:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.datascope;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
import com.platform.common.utils.Constant;
import com.platform.common.utils.ShiroUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author 孙珊珊
 * <p>
 * mybatis 数据权限拦截器
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor extends SqlExplainInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);

        // 不是SELECT操作直接返回
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        StringBuilder filterSql = new StringBuilder(boundSql.getSql());
        Object parameterObject = boundSql.getParameterObject();
        // liuqianru add start
        IPage page = null;
        if (parameterObject instanceof IPage) {
            page = (IPage)parameterObject;
        } else if (parameterObject instanceof Map) {
            Iterator var8 = ((Map)parameterObject).values().iterator();

            while(var8.hasNext()) {
                Object arg = var8.next();
                if (arg instanceof IPage) {
                    page = (IPage)arg;
                    break;
                }
            }
        }
        // liuqianru add end

        //参数中DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);

        if (dataScope == null) {
            return invocation.proceed();
        } else {
            SysUserEntity user = ShiroUtils.getUserEntity();
            if (null != user) {
                //如果不是超级管理员，则只能查询本机构及子机构数据
                if (!user.getUserName().contains("admin")) {
                    String userAlias = dataScope.getUserAlias();
                    String orgAlias = dataScope.getOrgAlias();
                    String alias = dataScope.getOrgNos();
                    boolean self = dataScope.getSelf();
                    String userId = dataScope.getUserId(); // 所属人
                    String newsql = filterSql.toString();
                    String beforsql="";
                    if(newsql.contains("ORDER BY")){
                        Integer wz = newsql.indexOf("ORDER BY", newsql.indexOf("ORDER BY") + 1);
                        if (StringUtils.isNotBlank(alias)) {
                            beforsql+=" and ("+orgAlias+" in ("+alias+")";
                            if (self) {
                                beforsql+=" or "+userAlias+"='"+user.getUserId()+"' ";
                            }
                            if (StringUtils.isNotBlank(userId)) {  //liuqianru add
                                beforsql+=" or "+userId+"='"+user.getUserId()+"' ";
                            }
                            beforsql+=" ) ";
                        } else if (self) {
                            beforsql+=" and ("+userAlias+"='"+user.getUserId()+"' ";
                            if (StringUtils.isNotBlank(userId)) {  //liuqianru add
                                beforsql+=" or "+userId+"='"+user.getUserId()+"' ";
                            }
                            beforsql+=" ) ";
                        }

                        filterSql.insert(wz,beforsql);
                        System.out.println(filterSql);
                    } else {
                        if (StringUtils.isNotBlank(alias)) {
                            beforsql+=" and ("+orgAlias+" in ("+alias+")";
                            if (self) {
                                beforsql+=" or "+userAlias+"='"+user.getUserId()+"' ";
                            }
                            if (StringUtils.isNotBlank(userId)) {
                                beforsql+=" or "+userId+"='"+user.getUserId()+"' ";
                            }
                            beforsql+=" ) ";
                        } else if (self) {
                            beforsql+=" and ("+userAlias+"='"+user.getUserId()+"' ";
                            if (StringUtils.isNotBlank(userId)) {
                                beforsql+=" or "+userId+"='"+user.getUserId()+"' ";
                            }
                            beforsql+=" ) ";
                        }

                        filterSql.append(beforsql);
                        System.out.println(filterSql);
                    }

                    // liuqianru add start
//                    if (page.isSearchCount()) {
//                        Connection connection = (Connection)invocation.getArgs()[0];
//                        String originalSql = filterSql.toString();
//                        if (filterSql.toString().contains("WITH selectTemp")) {
//                            int first = filterSql.toString().indexOf("(");
//                            int last = filterSql.toString().lastIndexOf(")");
//                            originalSql = filterSql.toString().substring(first + 1, last);
//                        }
//                        SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(true, null, originalSql);
//                        PreparedStatement statement = connection.prepareStatement(sqlInfo.getSql());
//                        ResultSet resultSet = statement.executeQuery();
//                        if (resultSet.next()) {
//                            long total = resultSet.getLong(1);
//                            page.setTotal(total);
//                        }
//                    }
                    // liuqianru add end
                }
                metaObject.setValue("delegate.boundSql.sql", filterSql.toString());
            }
            return invocation.proceed();
        }
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                } else {
                    if (val instanceof Map) {
                        for (Object v : ((Map<?, ?>) val).values()) {
                            if (v instanceof DataScope) {
                                return (DataScope) v;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
