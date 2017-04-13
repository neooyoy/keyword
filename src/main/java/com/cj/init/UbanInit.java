package com.cj.init;

import com.cj.constant.UbanConstant;
import com.cj.utils.Logger;
import com.cj.utils.PropUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

@Component
public class UbanInit implements ServletContextAware{
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void setServletContext(ServletContext servletContext) {
        String rootPath = servletContext.getRealPath("/");
        if (!rootPath.endsWith(String.valueOf(File.separatorChar)))
            rootPath += File.separatorChar;

        PropUtil.setROOT_PATH(rootPath);
        UbanConstant.PROPATH = servletContext.getContextPath();

        try {
            UbanConstant.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //xss文件加载
//        XSSSecurityManager.init(PropUtil.getPROP_ROOT() + "conf/xss_security_config.xml");
    }
    
}
