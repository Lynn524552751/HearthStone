package com.basic.util;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.abs.AbsGlobal;
import com.mgr.interf.IConstants;

/**
 * 全局的公共类，提供公共的函数
 * 
 * @author aiyoyoyo
 * 
 */
public class Global extends AbsGlobal implements IConstants {
	/**
	 * 邮件服务器
	 */
	private static JavaMailSenderImpl serviceMail;
	/**
	 * 由Spring注入初始模版
	 */
	private static String defTheme = "default";

	/**
	 * 获取功能模块路径
	 * 
	 * @param app
	 * @param request
	 * @return
	 */
	public static String getAppModule(String app, HttpServletRequest request) {
		AbsGlobal.getServiceLogger().debug("转到模版：" + app);
		String path = (String) request.getSession().getAttribute(SESSION_PATH);
		if (path == null) {
			request.getSession().setAttribute(SESSION_PATH, APP_PATH);
		}
		request.setAttribute(SESSION_PATH, APP_PATH);
		return Global.getThemeTpl(request) + "/" + app;
	}

	public static String getDefTheme() {
		return defTheme;
	}

	public static JavaMailSenderImpl getServiceMail() {
		return serviceMail;
	}

	/**
	 * 获取当前的模版
	 * 
	 * @param request
	 * @return
	 */
	public static String getThemeTpl(HttpServletRequest request) {
		String tpl = (String) request.getSession().getAttribute(
				SESSION_TEMPLATE);
		if (tpl == null) {
			AbsGlobal.getServiceLogger().debug("未找到预加载模版，将使用默认模版：" + defTheme);
			tpl = APP_PATH + defTheme;

			request.getSession().setAttribute(SESSION_TEMPLATE, tpl);
		}
		request.setAttribute(SESSION_TEMPLATE, tpl);
		return tpl;
	}

	/**
	 * 跳转到功能模块
	 * 
	 * @param app
	 * @param request
	 * @return
	 */
	public static String retAppModule(String app, HttpServletRequest request) {
		AbsGlobal.getServiceLogger().debug("重定向到：" + app);
		return "redirect:" + APP_PATH + app;
	}

	public static void sendSimpleMail(String mail, String subject,
			String content) {
		AbsGlobal.getServiceLogger().debug(
				"发送简易邮件：" + mail + ", " + subject + ", " + content);

		try {
			MimeMessage message = serviceMail.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					true, "UTF-8");
			messageHelper.setFrom(serviceMail.getUsername());
			messageHelper.setTo(mail);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);

			serviceMail.send(message);
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"邮件发送失败：" + mail + ", " + subject + ", " + content);
		}
	}

	/**
	 * 获取当前的模版
	 * 
	 * @param request
	 * @return
	 */
	public static String setThemeTpl(String template, HttpServletRequest request) {
		String tpl = APP_PATH + template;

		request.getSession().setAttribute(SESSION_TEMPLATE, tpl);
		request.setAttribute(SESSION_TEMPLATE, tpl);
		return tpl;
	}

	public void setDefTheme(String defTheme) {
		if (defTheme.equals("")) {
			defTheme = "default";
		}
		Global.defTheme = defTheme;
	}

	public void setServiceMail(JavaMailSenderImpl serviceMail) {
		Global.serviceMail = serviceMail;
	}
}
