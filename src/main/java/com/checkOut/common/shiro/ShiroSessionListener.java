package com.checkOut.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.checkOut.common.mapper.sys.SysUserMapper;
import com.checkOut.common.model.sys.SysUser;
import com.checkOut.utils.H;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * shiro管理的会话监听器
 *
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @version 1.0
 * @date 创建时间：2017年5月23日 下午9:21:12
 * @since
 */
public class ShiroSessionListener implements SessionListener {

    private Pattern p = Pattern.compile("id=[\\d]+");

    @Autowired
    private SysUserMapper sysUserMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStart(Session session) {
        logger.info("会话创建了...");
    }

    @Override
    public void onStop(Session session) {
        logger.info("会话停止了...");
        this.changeLoginStatus(session);
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("会话过期了...");
        this.changeLoginStatus(session);
    }

    /**
     * 当用户会话停止或者过期的时候改变数据库中用户的登录状态
     *
     * @param session
     * @return updateCount - 是否改变状态成功[1:成功|:0失败]
     */
    private int changeLoginStatus(Session session) {
        int updateCount = 0;
        Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        if (object != null) {
            String value = object.toString();
            logger.info("value=" + value);

            Matcher matcher = p.matcher(value);
            if (matcher.find()) {
                String res = matcher.group();
                logger.info("res=" + res);

                if (H.isNotBlank(res)) {
                    res = res.replace("id=", "");
                    SysUser record = new SysUser();
                    record.setId(Integer.valueOf(res));
                    record.setOnline(false);
                    updateCount = sysUserMapper.updateByPrimaryKeySelective(record);
                    logger.info("updateCount=" + updateCount);
                }
            }
        }
        return updateCount;
    }
}
