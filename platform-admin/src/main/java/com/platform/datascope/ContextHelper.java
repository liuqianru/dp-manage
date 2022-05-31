package com.platform.datascope;

import com.platform.common.utils.Constant;
import com.platform.common.utils.ShiroUtils;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.cache.SysDBCacheLogic;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysRoleOrgEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserRoleEntity;
import com.platform.modules.sys.entity.SysUserSuperviseEntity;
import com.platform.modules.sys.service.SysRoleOrgService;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.service.SysUserSuperviseService;
import com.platform.modules.util.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * HttpServlet相关的工具类
 * 
 * @author Kreo
 * 
 */
@Component
public class ContextHelper extends AbstractController {

	private static Log log = LogFactory.getLog(ContextHelper.class);
	@Autowired
	private SysRoleOrgService sysRoleOrgService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserSuperviseService sysUserSuperviseService;

	private static SysUserRoleService sysUserRoleServicestatic;

	private static SysRoleOrgService sysRoleOrgServicestatic;

	private static SysUserSuperviseService sysUserSuperviseServiceStatic;

	@PostConstruct
	public void init() {
		sysRoleOrgServicestatic = this.sysRoleOrgService;
		sysUserRoleServicestatic = this.sysUserRoleService;
		sysUserSuperviseServiceStatic = this.sysUserSuperviseService;
	}

	/**
	 * 查询部门权限共通函数
	 * 所有调用接口的地方可用此方法
	 * liuqianru add
	 */
	public static String getPermitDepts(String userPerm) {
		SysUserEntity user = ShiroUtils.getUserEntity();
		Set<String> orgs = new HashSet<>();
		if (!user.getUserName().contains("admin")) {
			// 优先取默认角色部门和监管部门
			orgs = setSearchDepts(user.getUserId(), user.getOrgNo());
			// 没有则取角色里勾选的部门
			if (orgs.isEmpty()) {
				orgs = setSearchDepts(userPerm, user.getUserId(), user.getOrgNo());
			}
		} else {
			return "-1";
		}
		return StringUtils.join(orgs.toArray(), ",");
	}

	/**
	 * 为DataScope方法提供返回部门list
	 * @param sros
	 */
	public static Set<String> setSearchDeptPermit4Search(List<SysRoleOrgEntity> sros, String dept) {
		Set<String> depts = new HashSet<>();
		if(sros!=null&&sros.size()>0){
			String str = (String) CacheFactory.getCacheInstance().get(SysDBCacheLogic.CACHE_DEPT_PREFIX_SUB + dept);
			String[] s = (String[]) JSONUtil.toObject(str, String[].class);// 转换成数组
			for(SysRoleOrgEntity d:sros){
				if(d!=null&&d.getOrgNo()!=null&&!d.getOrgNo().equals("")){
					if(d.getOrgNo().equals("1")){//只有本部门权
						depts.add(dept);
					}else if(d.getOrgNo().equals("2")){ //本部门权及子部门
						if(s!=null&&s.length>0){
							for(int i=0;i<s.length;i++){
								depts.add(s[i]);
							}
						}
						depts.add(dept);
					}else if(d.getOrgNo().equals("3")){ //仅子部门
						if(s!=null&&s.length>0){
							for(int i=0;i<s.length;i++){
								depts.add(s[i]);
							}
						}
					}else{
						depts.add(d.getOrgNo());
					}
				}
			}
		}
		return depts;
	}

	public static Set<String> setSearchPermitDept(List<SysUserRoleEntity> roles, String dept, List<SysUserSuperviseEntity> deptlist) {
		Set<String> depts = new HashSet<>();
		if(roles != null && roles.size() > 0) {
			String str = (String) CacheFactory.getCacheInstance().get(SysDBCacheLogic.CACHE_DEPT_PREFIX_SUB + dept);
			String[] s = (String[]) JSONUtil.toObject(str, String[].class);// 转换成数组
			for(SysUserRoleEntity r : roles) {
				if (r.getOrgnoselect() != null) {
					if (r.getOrgnoselect() == 2) { //本部门权及子部门
						if (s != null && s.length > 0) {
							for (int i = 0; i < s.length; i++) {
								depts.add(s[i]);
							}
						}
						depts.add(dept);
					} else if (r.getOrgnoselect() == 3) { //仅子部门
						if (s != null && s.length > 0) {
							for (int i = 0; i < s.length; i++) {
								depts.add(s[i]);
							}
						}
					}
				}
			}
		}
		// 监管部门
		if (deptlist.size() > 0) {
			for(SysUserSuperviseEntity userSupervise : deptlist) {
				if (userSupervise.getSelfchecked()) {  // 部门权限
					depts.add(userSupervise.getOrgNo());
				}
				if (userSupervise.getSubchecked() != null && userSupervise.getSubchecked()) { // 子部门权限
					String str = (String) CacheFactory.getCacheInstance().get(SysDBCacheLogic.CACHE_DEPT_PREFIX_SUB + userSupervise.getOrgNo());
					String[] s = (String[]) JSONUtil.toObject(str, String[].class);// 转换成数组
					if (s != null && s.length > 0) {
						for (int i = 0; i < s.length; i++) {
							depts.add(s[i]);
						}
					}
				}
			}
		}
		return depts;
	}

	/**
	 * 读取默认角色部门和监管部门
	 * @param userId
	 * @param dept
	 * @return 返回所有有权限的部门list
	 */
	public static Set<String> setSearchDepts(String userId,String dept) {
		// 判断是否有默认角色部门（仅子部门权限 || 本部门及子部门权限）
		List<SysUserRoleEntity> roles = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("rolePerm", "'397076822ac95125c279c18875f8b81c','5d3ec74da121e069221541823facef7e'");
		roles = sysUserRoleServicestatic.queryRoleList(map);
		// 用户的监管部门
		List<SysUserSuperviseEntity> deptlist = new ArrayList<>();
		deptlist = sysUserSuperviseServiceStatic.queryAll(map);
		// 拼接用户的部门
		Set<String> orgs = new HashSet<>();
		orgs = setSearchPermitDept(roles, dept, deptlist);
		return orgs;
	}

	/**
	 * 读取角色里勾选的部门
	 * @param userPerm
	 * @param userid
	 * @param dept
	 * @return
	 */
	public static Set<String> setSearchDepts(String userPerm,String userid,String dept) {
		List<SysRoleOrgEntity> sros = new ArrayList<>();
		Map<String, Object> m = new HashMap<>();
		m.put("userId", userid);
		m.put("userPerm", userPerm);
		sros = sysRoleOrgServicestatic.queryOrgNoIsselect(m);
		Set<String> orgs = new HashSet<>();
		orgs = setSearchDeptPermit4Search(sros, dept);
		return orgs;
	}

	/**
	 * 订单列表专用权限,读取角色中的订单类型
	 * @param userPerm
	 * @param userid
	 * @return 订单类型list
	 */
	public static Set<String> setOrdertypesm(String userPerm,String userid) {
		Set<String> list = new HashSet<>();
		if (userid.length() > 2) { //非管理员
			List<SysUserRoleEntity> surs = new ArrayList<>();
			Map map = new HashMap();
			map.put("userId", userid);
			surs = sysUserRoleServicestatic.queryRoleList(map);
			list = setOrdertypes(surs);
		} else{  //管理员
			for(int i=0; i<11; i++){
				list.add(i+"");
			}
		}
		return list;
	}

	/**
	 * 仓库权限，读取角色中的仓库
	 * @param userPerm
	 * @param userid
	 * @return 仓库类型list
	 */
	public static Set<String> setWarelistsm(String userPerm,String userid) {
		Set<String> list = new HashSet<>();
		if (userid.length()>2) { //管理员
			List<SysUserRoleEntity> surs=new ArrayList<>();
			Map map = new HashMap();
			map.put("userId", userid);
			surs = sysUserRoleServicestatic.queryRoleList(map);
			list = setWarelists(surs);
		} else{  //管理员
		}
		return list;
	}

	public static Set<String> setOrdertypes(List<SysUserRoleEntity> sros) {
		Set<String> dset = new HashSet<>();
		if(sros!=null&&sros.size()>0){
			for(SysUserRoleEntity d:sros){
				if(d!=null&&d.getOrdertype()!=null){
					String[] str=d.getOrdertype().split(",");
					for (int i=0;i<str.length;i++) {
						dset.add(str[i]);
					}
				}
			}
		}
		return dset;
	}

	public static Set<String> setWarelists(List<SysUserRoleEntity> sros) {
		Set<String> dset = new HashSet<>();
		if(sros!=null&&sros.size()>0){
			for(SysUserRoleEntity d:sros){
				if(d!=null&&d.getWarelist()!=null){
					String[] str=d.getWarelist().split(",");
					for (int i=0;i<str.length;i++) {
						dset.add(str[i]);
					}
				}
			}
		}
		return dset;
	}
}
