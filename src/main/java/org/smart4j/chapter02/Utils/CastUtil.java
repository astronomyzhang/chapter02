package org.smart4j.chapter02.Utils;

import org.apache.commons.lang3.StringUtils;

public class CastUtil  {
    /**
     * 转为String类型
     */
    public static String castString(Object obj){
        return CastUtil.castString(obj, "");
    }

    /**
     * 转为String类型，可指定默认值
     */
    public static String castString(Object obj, String defaultValue){
        return obj!=null ? String.valueOf(obj):defaultValue;
    }

    /**
     * 转为double类型
     */
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj, 0.0);
    }

    /**
     * 转为double类型，可指定默认值
     */
    public static double castDouble(Object obj, double defaultValue){
        double value = defaultValue;
        if(obj!=null){
            String strValue = castString(obj);
            if(StringUtils.isNotEmpty(strValue)){
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value=defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为long类型
     */
    public static long castLong(Object obj){
        return castLong(obj, 0);
    }

    /**
     * 转为long类型，可指定默认值
     */
    public static long castLong(Object obj, long defaultValue){
        long value = defaultValue;
        if(obj!=null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    value = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为int类型
     */
    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    /**
     * 转为int类型，可指定默认值
     */
    public static int castInt(Object obj, int defaultValue){
        int value = defaultValue;
        if(obj!=null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为boolean类型，默认为 false
     */
    public static Boolean castBoolean(Object obj){
        return castBoolean(obj, false);
    }

    /**
     * 转为Boolean类型，可指定默认值
     */
    public static Boolean castBoolean(Object obj, Boolean defaultValue){
        Boolean value = defaultValue;
        if(obj!=null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                value = Boolean.parseBoolean(strValue);
            }
        }
        return value;
    }
}

