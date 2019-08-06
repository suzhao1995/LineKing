package com.rs.common.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author 汪航
 * @Description 验证工具类
 * @create 2019-08-05 12:47
 */
public class BPUtil {

    //参数验证
    public static void check(boolean condition, String message) throws Exception {
        if (condition) {
            throw new Exception(message);
        }
    }
    //字符串是否相等验证
    public static void check(boolean condition, String message, Object... obj) throws Exception {
        if (condition) {
            throw new Exception(StrUtil.format(message, obj));
        }
    }
    //数字验证
    public static boolean biggerZero(Number num) {
        return num != null && num.doubleValue() > 0;
    }
}
