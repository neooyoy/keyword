package com.cj.constant;

import java.io.IOException;

import com.cj.mybatis.domain.BaseDomain;
import com.cj.utils.PropUtil;


public class UbanConstant {
    /** 静态文件版本*/
    public static String STATIC_VERSION;

    /** js服务器地址 */
    public static String JSPATH;
    /** 域名地址 */
    public static String PROPATH;
    /**
     * 阿里云消息队列CONSUMERID
     */
    public static String CONSUMERID;
    /**
     * 阿里云消息队列ACCESSKEY
     */
    public static String ACCESSKEY;
    /**
     * 阿里云消息队列SECRETKEY
     */
    public static String SECRETKEY;
    /**
     * 阿里云消息队列TOPIC
     */
    public static String TOPIC;
    /**
     * 阿里云消息队列启动线程数
     */
    public static Integer CONSUMETHREADNUMS;
    /**
     * 路由开关，默认开启
     */
    public static Boolean BUSINESSLOGROUTERFLAG;

    public static ThreadLocal<BaseDomain> businessLogLocal = new ThreadLocal<BaseDomain>();
    /**
     * session前缀
     */
    public static String SESSION_PREFIX;
    /**
     * session加解密token
     */
    public static String SESSION_TOKEN;

    /**
     * 重新加载常量
     *
     * @throws IOException
     */
    public static void reloadConfig() throws Exception {
        // js常量相关
        JSPATH = PropUtil.getPropValue("config", "jspath");
        SESSION_PREFIX = PropUtil.getPropValue("config", "session.prefix");
        SESSION_TOKEN = PropUtil.getPropValue("config", "session.token");
        STATIC_VERSION = PropUtil.getPropValue("config", "static.version");
    }
}
