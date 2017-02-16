package com.mgr.interf ;

public interface IConstants {
	/**
	 * 应用入口，修改需要重新编译该文件，
	 * 示例： <br/>
	 * "/" http://localhost/{app} <br/>
	 * "/MGR/" http://localhost/MGR/{app}
	 * */
	String	APP_PATH			= "/" ;

	String	APP_ERROR			= "error" ;
	String	APP_ERROR_R_ERRCODE	= "errCode" ;

	String	APP_LOCAL			= "locale" ;
	String	APP_LOCAL_R_LANG	= "lang" ;
	String	APP_LOCAL_R_APP		= "app" ;

	String	APP_INDEX			= "index" ;

	String	SESSION_TEMPLATE	= "tpl" ;
	String	SESSION_PATH		= "path" ;
	String	SESSION_LOCALE		= "locale" ;

	// 以上为全局页面访问对象、任意工程通用

}
