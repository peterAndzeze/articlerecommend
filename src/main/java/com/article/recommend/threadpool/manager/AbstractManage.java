package com.article.recommend.threadpool.manager;

import com.article.recommend.threadpool.assist.ManagerContext;
import com.article.recommend.threadpool.assist.ManagerControlListener;
import org.apache.log4j.Logger;

/**
 * 抽象管理器
 */
public abstract class AbstractManage implements ManagerControlListener {
	
		protected ManagerContext managerContext = null;
		protected Logger logger = Logger.getLogger(getClass());
		public ManagerContext getManagerContext() {
			return managerContext;
		}
		public abstract void start() throws Exception;
		
		public AbstractManage(ManagerContext managerContext){
			this.managerContext = managerContext;
			this.managerContext.setAbstractManage(this);
		}
}
