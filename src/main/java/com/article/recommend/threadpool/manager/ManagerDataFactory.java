package com.article.recommend.threadpool.manager;


import com.article.recommend.threadpool.assist.ManagerContext;

public class ManagerDataFactory {
	private static ManagerDataFactory managerDataFactory;
	private AbstractManage abstractManage = null;
	private ManagerContext context = null;
//	private MessageManage messageManage = null;
	
	/**
	 * 
	 */
	private ManagerDataFactory(){
		
	}
	
	/**
	 * 创建工厂
	 * @return ManagerDataFactory 
	 */
	public static ManagerDataFactory createDataFactory(){
		if(managerDataFactory==null){
			managerDataFactory = new ManagerDataFactory();
		}
		return managerDataFactory;
	}
	
	/**
	 * 返回生成数据的管理类实例。
	 * @param context 生成报表上下文对象
	 * @return messageManage 消息管理对象
	 */
	public synchronized AbstractManage getAbstractManage(ManagerContext context)
	{
		if(abstractManage == null){
			abstractManage = new ManagerImple(context);
		}
		return abstractManage;
	}
	/**
	 * 
	 * @return ManagerContext
	 */
	public synchronized ManagerContext getManagerContext()
	{
		if(context == null){
			context = new ManagerContext();
		}
		return context;
	}
}
