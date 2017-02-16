package com.basic.controller;import java.util.Locale;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.servlet.HandlerInterceptor;import org.springframework.web.servlet.LocaleResolver;import org.springframework.web.servlet.ModelAndView;import com.abs.AbsDBExecutor;import com.abs.AbsGlobal;import com.basic.util.Global;import com.mgr.interf.IConstants;/** *  * 登录服务器全局控制跳转类 *  * @author Aiyoyoyo *  */@Controllerpublic class CtrlMapping extends AbsDBExecutor implements IConstants,		HandlerInterceptor {	@Autowired	private LocaleResolver localeResolver;	@Override	public void afterCompletion(HttpServletRequest request,			HttpServletResponse response, Object handler, Exception ex)			throws Exception {	}	/**	 * 访问错误入口，所有错误通过该方法监控<br/>	 * url: /APP_ERROR/{code}	 * 	 * @param code	 *            指定特定的错误类型,在web.xml中配置	 * @param request	 * @return	 */	@RequestMapping(APP_PATH + APP_ERROR + "/{code}")	public String error(@PathVariable String code, HttpServletRequest request) {		AbsGlobal.getServiceLogger().error("请求错误，代码：" + code);		request.setAttribute(APP_ERROR_R_ERRCODE, code);		return Global.getAppModule(APP_ERROR, request);	}	/**	 * 网站访问入口，所有未定义RequestMapping的页面都通过该方法进行监控	 * 	 * @param app	 *            访问路径	 * @param request	 * @return	 */	// @RequestMapping( APP_PATH )	// public String indexMonitor( HttpServletRequest request ) {	// AbsGlobal.getServiceLogger().debug( "访问应用程序默认入口" ) ;	// return Global.getAppModule( APP_INDEX , request ) ;	// }	/**	 * 语言切换，自动通过cookie保存，默认取系统语言环境<br/>	 * url: /{mapping}?lang={zh/en/other}	 * 	 * @param tabDemo	 * @param lang	 * @param path	 * @param request	 * @param response	 * @return	 */	@RequestMapping(APP_PATH + APP_LOCAL)	public String locale(@RequestParam(APP_LOCAL_R_LANG) String lang,			@RequestParam(APP_LOCAL_R_APP) String app,			HttpServletRequest request, HttpServletResponse response) {		AbsGlobal.getServiceLogger().debug("切换语言： " + lang);		Locale locale = new Locale(lang);		// 切换语言		localeResolver.setLocale(request, response, locale);		request.getSession().setAttribute(SESSION_LOCALE, locale);		AbsGlobal.getServiceLogger().debug("返回应用模块： " + app);		return Global.getAppModule(app, request);	}	@Override	public void postHandle(HttpServletRequest request,			HttpServletResponse response, Object handler,			ModelAndView modelAndView) throws Exception {	}	/**	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行	 * ，而且所有的Interceptor中的preHandle方法都会在	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的	 * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。	 */	@Override	public boolean preHandle(HttpServletRequest request,			HttpServletResponse response, Object handler) throws Exception {		// TODO Auto-generated method stub		AbsGlobal.getServiceLogger().debug("拦截请求：");		return true;	}}