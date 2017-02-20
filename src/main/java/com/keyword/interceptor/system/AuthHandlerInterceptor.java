package com.keyword.interceptor.system;

import com.keyword.annotation.IgnoreAuth;
import com.keyword.utils.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限验证
 * User: zhanglin
 * Date: 2015/12/7
 * Time: 9:46
 */

public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = Logger.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            // 验证权限
            IgnoreAuth auth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            if (auth != null && auth.validate()) {
                flag = true;
            }
        }

        if (!flag) {
            response.sendRedirect(request.getContextPath() + "/loginxiezilou");

            // 转发
//            request.setAttribute("msg", "没有权限");
//            request.getRequestDispatcher("login").forward(request, response);
        }
        return flag;
    }
}
