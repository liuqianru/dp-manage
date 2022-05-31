package com.platform.modules.cache;

import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.service.SysOrgService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CacheFactory {
	private static SysCache cache;
	private static Log log = LogFactory.getLog(CacheFactory.class);

	public static SysCache getCacheInstance() {
		if (cache == null) {
			cache = new SysCacheSimpleLocation();
		}
		return cache;
	}

	/**
	 * 缓存流
	 *
	 * @param cacheMode
	 */
	public static void CacheFlow(String cacheMode) {
		SysCacheLogic cacheLogic = new SysCacheLogic();
		SysDBCacheLogic dbCacheLogic = new SysDBCacheLogic();

		if ("sys".equals(cacheMode)) {
			// 系统参数缓存
			cacheLogic.cacheSys(true);
		} else if ("dept".equals(cacheMode)) {
			// 部门数据缓存
			dbCacheLogic.cacheDept(true);
		} else if ("all".equals(cacheMode)) {
			//dbCacheLogic.clearAllCache();
			cacheLogic.cacheSys(false);
			dbCacheLogic.cacheDept(false);
		}
	}
}
