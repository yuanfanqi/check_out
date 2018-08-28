package com.checkOut.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.checkOut.common.mapper.sys.SysUserMapper;
import com.checkOut.common.model.ActiveUser;
import com.checkOut.common.model.sys.SysUser;
import com.checkOut.common.shiro.ShiroRealm;
import com.checkOut.common.shiro.ShiroUtil;
import com.checkOut.utils.H;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登录控制器
 * 
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月21日 下午8:38:16
 * @version 1.0
 * @since
 */
@Controller
@RequestMapping(value = "/sys")
@Api(value = "SysLoginController", tags="系统登录控制器")
public class SysLoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Producer producer;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ShiroRealm shiroRealm;

	// 跳转到登陆页
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) {
		return "login";
	}

	@RequestMapping(value = "/doLogin", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "执行登录", notes = "do login operate")
	@ResponseBody
	public Map<String, Object> doLogin(String username, String password, String randomcode, HttpServletRequest request) throws ServletException, IOException {
        logger.info("\n\n★进入登陆校验方法======================================================\n");

        Map<String, Object> result = new HashMap<>(16);

		// 校验验证码是否正确
		String validateCode = ShiroUtil.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		logger.info("保存在session中的验证码=" + validateCode);

		if (!validateCode.equalsIgnoreCase(randomcode)) {
			result.put("status", false);
			result.put("msg", "验证码错误");
		} else {

			Subject subject = ShiroUtil.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);

			try {
				subject.login(token);
				result.put("status", true);
				result.put("msg", "登录成功");
				ActiveUser activeUser = (ActiveUser)ShiroUtil.getSubject().getPrincipal();
				SysUser record = new SysUser();
				record.setId(activeUser.getId());
//				record.setLoginIp(H.getClientIpAddress(request));
				record.setLoginTime(Calendar.getInstance().getTime());
				record.setOnline(true);
				int updateCount = sysUserMapper.updateByPrimaryKeySelective(record);
				logger.info("数据库中用户登录状态="+updateCount);

			} catch (UnknownAccountException e) {
				result.put("status", false);
				result.put("msg", "账号或密码不正确");
			} catch (IncorrectCredentialsException e) {
				result.put("status", false);
				result.put("msg", "账号或密码不正确");
			} catch (LockedAccountException e) {
				result.put("status", false);
				result.put("msg", "账号已被锁定,请联系管理员");
			} catch (DisabledAccountException e) {
				result.put("status", false);
				result.put("msg", "账号已过期,请联系管理员");
			} catch (AuthenticationException e) {
				result.put("status", false);
				result.put("msg", "系统错误,用户认证失败");
			} catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return result;
	}

	@RequestMapping(value = "/randcode")
	@ApiOperation(value = "请求获取验证码", notes = "get random code")
	public void randcode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("\n\n★进入请求获取验证码方法======================================================\n");

		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg");

		// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		// 生成文字验证码
		String text = producer.createText();
		logger.info("是否生成了验证码文字:" + H.isNotBlank(text));

		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		logger.info("是否生成了图片验证码:" + (image != null));

		// 保存到shiro session
		ShiroUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = null;
        try {
			out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
        } catch (IOException e) {
            logger.error("获取验证码输出流失败!", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                	logger.error("关闭验证码输出流失败!");
                    e.printStackTrace();
                }
            }
        }
	}

	@ApiOperation(value = "跳转到欢迎页", notes = "go to welcome page")
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String toWelcome(ModelMap map, HttpSession session) {
        logger.info("\n\n★进入跳转到欢迎页方法======================================================\n");

		map.addAttribute("name", "raymee");
		Collection<Session> sessions = sessionDao.getActiveSessions();
		logger.info("当前活动session个数=" + sessions.size());

		Enumeration<String> enums = session.getAttributeNames();
        //DefaultSubjectContext_PRINCIPALS_SESSION_KEY
        while (enums.hasMoreElements()) {
            // 获取session键值
			String name = enums.nextElement().toString();
			// 根据键值取session中的值
			Object value = session.getAttribute(name);
			// 打印结果
			logger.info("name=" + name);
			logger.info("value=" + value);
		}
		return "welcome";
	}
	
	@ApiOperation(value = "退出登录", notes = "do logout operate")
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(){
        logger.info("\n\n★进入退出登录方法======================================================\n");

		ShiroUtil.getSubject().logout();
		return "login";
	}
	
	@ApiOperation(value = "刷新缓存", notes = "do logout operate")
	@RequestMapping(value = "/refresh", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> refresh(){
        logger.info("\n\n★进入刷新缓存方法======================================================\n");

        Map<String, Object> result = new HashMap<>(16);
        result.put("status", true);
		result.put("msg", "刷新缓存成功!");
		try {
			shiroRealm.clearCached();
		} catch (Exception e) {
			result.put("status", false);
			result.put("msg", "刷新缓存失败!");
			logger.error("刷新缓存失败!", e);
		}
		return result;
	}
}
