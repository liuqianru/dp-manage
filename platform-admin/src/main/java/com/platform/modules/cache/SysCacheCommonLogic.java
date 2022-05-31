package com.platform.modules.cache;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysCacheCommonLogic extends SysCacheLogic {
	private static Log log = LogFactory.getLog(SysCacheCommonLogic.class);

	public synchronized void createStaticHtmlFiles() {
		createTop();
		createBottom();
	}

	private void createTop() {
	}

	private void createBottom() {
	}

}
