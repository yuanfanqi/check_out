package com.checkOut.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Raymee
 * @E-mail: leohaoo@vip.qq.com
 * @date: 2017年5月15日 下午10:17:44
 * @version: 1.0
 * @since: 
 */
public class ShiroUtil extends SecurityUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	public static Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		logger.info("是否生成了session:"+session.getId());
		return session;
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		logger.info("是否有key值:"+key);
		String kaptcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}
}
