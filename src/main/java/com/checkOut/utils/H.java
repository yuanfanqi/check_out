package com.checkOut.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.investoday.cms.common.enums.ClientIpHeaderEnum;
//import com.investoday.cms.common.model.app.SysPermission;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.checkOut.common.model.sys.SysPermission;

/**
 * Helper助手类
 *
 * @author: Raymee
 * @E-mail: leohaoo@vip.qq.com
 * @date: 2017年4月26日 上午11:18:04
 * @version: 1.0
 * @since:
 */
public class H {

    private static Logger logger = LoggerFactory.getLogger(H.class);
    static Pattern p = Pattern.compile("[A-Z]([a-z\\d]+)?");

    /**
     * 检测验证属性值
     *
     * @param bindingResult
     */
    public static String checkValidation(BindingResult bindingResult) {
        logger.info("===========================进入验证属性值方法");

        String str = null;
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError objectError : bindingResult.getAllErrors()) {

                sb.append(objectError.getDefaultMessage()).append("</br>");
            }
            str = sb.toString();
            if (H.isBlank(str)) {
                return null;
            }
        }
        return str;
    }

    /**
     * 检查菜单验证信息
     *
     * @param bindingResult
     * @return
     */
    public static String checkValidation(BindingResult bindingResult, SysPermission sysPermission) {
        logger.info("===========================进入验证属性值方法");
        String str = null;
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                if (sysPermission.getType() == (short) 1) {
                    if (((FieldError) objectError).getField().equals("permissionName")) {
                        sb.append("菜单名称：").append(objectError.getDefaultMessage()).append("</br>");
                    } else if (((FieldError) objectError).getField().equals("url")) {
                        sb.append("菜单URL：").append(objectError.getDefaultMessage()).append("</br>");
                    } else if (((FieldError) objectError).getField().equals("sort")) {
                        sb.append("排序号：").append(objectError.getDefaultMessage()).append("</br>");
                    }
                }
                if (sysPermission.getType() == (short) 2) {
                    if (((FieldError) objectError).getField().equals("permissionName")) {
                        sb.append("菜单名称：").append(objectError.getDefaultMessage()).append("</br>");
                    } else if (((FieldError) objectError).getField().equals("permissionSign")) {
                        sb.append("授权标识：").append(objectError.getDefaultMessage()).append("</br>");
                    }
                }
            }
            str = sb.toString();

            if (H.isBlank(str)) {
                return null;
            }
        }
        return str;
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = p.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 获取客户端ip地址(可以穿透代理)
     *
     * @param request
     * @return
     */
//    public static String getClientIpAddress(HttpServletRequest request) {
//        ClientIpHeaderEnum[] c = ClientIpHeaderEnum.values();
//        String ip = "127.0.0.1";
//        for (ClientIpHeaderEnum clientIpHeaderEnum : c) {
//            ip = request.getHeader(clientIpHeaderEnum.getName());
//            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
//                return ip;
//            }
//        }
//        ip = request.getRemoteAddr();
//        if (ip.equals("0:0:0:0:0:0:0:1")) {
//            ip = "127.0.0.1";
//        }
//        return ip;
//    }

    /**
     * 校验对象是否不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        boolean isNotBlank = true;
        if (obj == null) {
            isNotBlank = false;
        } else if (StringUtils.isEmpty(obj.toString().trim())) {
            isNotBlank = false;
        } else if (obj instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection<?>) obj)) {
                isNotBlank = false;
            }
        } else if (obj instanceof Map) {
            if (CollectionUtils.isEmpty((Collection<?>) obj)) {
                isNotBlank = false;
            }
        }
        return isNotBlank;
    }

    /**
     * 校验对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        boolean isBlank = false;
        if (obj == null) {
            isBlank = true;
        } else if (StringUtils.isEmpty(obj.toString().trim())) {
            isBlank = true;
        } else if (obj instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection<?>) obj)) {
                isBlank = true;
            }
        } else if (obj instanceof Map) {
            if (CollectionUtils.isEmpty((Collection<?>) obj)) {
                isBlank = true;
            }
        }
        return isBlank;
    }

    /***
     * 密码加密
     * @param source
     * @param salt
     * @param hashIterations
     * @return
     */
    public static String encSource(String source, String salt, int hashIterations) {
        SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
        return simpleHash.toString();
    }

    /***
     * salt加密
     * @return
     */
    public static String makeSalt() {
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        salt = salt.replace("-", "").toUpperCase();
        return salt;
    }

    /**
     * 创建唯一标识id
     *
     * @return
     */
    public static String createGuId() {
        UUID uuid = UUID.randomUUID();
        String guId = uuid.toString();
        guId = guId.replace("-", "").toUpperCase();
        return guId;
    }
    
    /**
     * 帮助分页操作
     * @param page
     * @param limit
     * @param total
     * @return
     */
    public static Map<String, Integer> pageOperation(Integer page, Integer limit, Integer total){
		Map<String, Integer> map = new HashMap<>();
		map.put("start", (page-1) * limit);
		map.put("end", page * limit);
		Integer pages = null;
		if(0 == total % limit){
			pages = total/limit;
		}else{
			pages = total/limit == 0 ? 1 : total/limit+1;
		}
		map.put("pages", pages);
		return map;
	}
}
