package com.cj.utils;

import java.util.Random;
/**
 * 生成随机字母和数字工具类
 * @author zhaolm
 *
 */
public class RandomCharAndNumUtil {
    public static final Integer LENGTH = 50;
    /**
     * 默认生成LENGTH位随机字母和数字
     * @return
     */
    public static String getRandomCharAndNumr() {  
        String str = "";  
        Random random = new Random();  
        for (int i = 0; i < RandomCharAndNumUtil.LENGTH; i++) {  
            boolean b = random.nextBoolean();  
            if (b) { // 字符串  
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
                str += (char) (65 + random.nextInt(26));// 取得大写字母  
            } else { // 数字  
                str += String.valueOf(random.nextInt(10));  
            }  
        }  
        return str;  
    }  
    /** 
     * 获取随机字母数字组合 
     *  
     * @param length 
     *            字符串长度 
     * @return 
     */  
    public static String getRandomCharAndNumr(Integer length) {  
        String str = "";  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            boolean b = random.nextBoolean();  
            if (b) { // 字符串  
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
                str += (char) (65 + random.nextInt(26));// 取得大写字母  
            } else { // 数字  
                str += String.valueOf(random.nextInt(10));  
            }  
        }  
        return str;  
    }  
}
