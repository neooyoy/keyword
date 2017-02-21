package com.keyword.interceptor.system;

import com.keyword.annotation.BusinessLog;
import com.keyword.annotation.IgnoreAuth;
import com.keyword.constant.Constant;
import com.keyword.dao.system.UserDao;
import com.keyword.dao.system.UserRoleDao;
import com.keyword.domain.system.User;
import com.keyword.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录验证 User: zhanglin Date: 2015/12/4 Time: 11:14
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    public static final int MENU_TYPE = 1;
    public static final int FUNCTION_TYPE = 2;

    protected Logger logger = Logger.getLogger(this.getClass());

    @Value("#{config['sso.login.url.root']}")
    private String sso_login_url_root;

    @Value("#{config['sso.login.url.login']}")
    private String sso_login_url_login;

    @Value("#{config['sso.login.cookie.name']}")
    private String sso_login_cookie_name;

    @Value("#{config['sso.login.sys.key']}")
    private String sso_login_sys_key;

    @Value("#{config['sso.login.sys.token']}")
    private String sso_login_sys_token;

    @Value("#{config['cookieName']}")
    private String cookieName;


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 登录验证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BusinessLog businessLog = ((HandlerMethod) handler).getMethodAnnotation(BusinessLog.class);
        if (businessLog != null) {
            ModifyActionidUtil.modifyActionid(businessLog.value(), request);
        }

        User u = null;

        /**访问网站前台不需要验证*/
        if (request.getServletPath().equals("/")
                || request.getServletPath().startsWith("/login")
                || request.getServletPath().startsWith("/querylist")
                || request.getServletPath().startsWith("/detail")) {
            return true;
        }

        boolean authFlag = false;
        String url = "";
        String base = request.getContextPath();

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {

            //不需要验证权限
            IgnoreAuth auth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            if (auth != null && auth.validate()) {
                return true;
            }

            // 读取session
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {//验证session是否存在
                u = (User) session.getAttribute("user");

                /**普通用户只能访问楼盘管理相关页面*/
                if (!request.getServletPath().startsWith("/keycontentController/")
                        && u.getRoleId().equals(Constant.COMMON_ROLE_ID)) {
                    url = base + "/keycontentController/importExcel";
                    authFlag = true;
                }

            } else {
                url = base + "/";
                authFlag = true;
            }
        }

        if (authFlag) {
            response.sendRedirect(url);
        }

        return true;
    }

    /**
     * 初始化 session的用户信息
     *
     * @param userid
     * @return
     * @throws Exception
     */
 /*   private User initUserInfo(int userid) throws Exception {
        User u = this.userDao.selectByUserId(userid);
        if (u != null) {
            UserRole userRole = userRoleDao.selectByUserId(u.getId());
            if (userRole != null) {
                u.setRoleId(userRole.getRoleId());
            }
        }
        return u;
    }*/
}
