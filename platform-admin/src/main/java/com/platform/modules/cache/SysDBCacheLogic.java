package com.platform.modules.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.platform.modules.cache.SysCacheLogic;
import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.service.SysOrgService;
import com.platform.modules.util.ToolsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


/**
 * 缓存数据库中的数据到内存,全部采用JSON形式存储
 * 
 * @author 骏宇
 * 
 */
@Component
public final class SysDBCacheLogic extends SysCacheLogic {
	private static Log log = LogFactory.getLog(SysDBCacheLogic.class);
	@Autowired
	private SysOrgService sysOrgService;

	private static SysOrgService sysOrgServicestatic;

	@PostConstruct
	public void init() {
		sysOrgServicestatic = this.sysOrgService;
	}

	// 子部门列表前缀
	public final static String CACHE_DEPT_PREFIX_SUB = "dept-sub-";
	// 父部门列表前缀
	public final static String CACHE_DEPT_PREFIX_PARENT = "dept-parent-";

	public synchronized void cacheDept(boolean delFlag) {
		// 是否需要先清空原缓存
		if (delFlag) {
			log.info("开始清空原部门缓存数据");
			cache.del(CACHE_DEPT_PREFIX_SUB + "*");
			cache.del(CACHE_DEPT_PREFIX_PARENT + "*");
			log.info("原部门缓存数据清空完毕");
		}
		log.info("开始缓存部门数据");
		List<SysOrgEntity> l = sysOrgServicestatic.queryAll(null);
		// 建立一个特殊的map key:value dept_code:parent_dept
		Map<String, String> m = new HashMap<>();
		for (Iterator<SysOrgEntity> it = l.iterator(); it.hasNext();) {
			SysOrgEntity dept = (SysOrgEntity) it.next();
			m.put(dept.getOrgNo(), dept.getParentNo());
		}
		// 开始遍历
		Map<String, String> sub_map = new HashMap<>();
		Map<String, String> parent_map = new HashMap<>();
		
		for (Iterator<Map.Entry<String, String>> it = m.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> d1 = (Map.Entry<String, String>) it.next();
			for (Iterator<Map.Entry<String, String>> it2 = m.entrySet().iterator(); it2.hasNext();) {
				Map.Entry<String, String> d2 = (Map.Entry<String, String>) it2.next();
				if (ToolsUtil.checkParent(m, d2.getKey(), d1.getKey())) {
					cacheMap(sub_map, CACHE_DEPT_PREFIX_SUB + d1.getKey(), d2.getKey());
				}
				if (ToolsUtil.checkParent(m, d1.getKey(), d2.getKey())) {
					cacheMap(parent_map, CACHE_DEPT_PREFIX_PARENT + d1.getKey(), d2.getKey());
				}
			}
			
		}

		cacheValue2JSON(sub_map);
		cacheValue2JSON(parent_map);
		log.info("缓存部门数据完成");
	}

}
