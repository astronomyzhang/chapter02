package org.smart4j.chapter02.Utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 集合工具类
 */
public final class CollectionUtil {

    /**
     *判断Collection是否为空
     *@date 2019-11-28 9:39
     *@param collection
     *@return boolean
     *@throws
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     *描述信息
     *@creator Garwen
     *@date 2019-11-28 9:42
     *@param collection
     *@return boolean
     *@throws
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    /**
     *判断map是否为空
     *@creator Garwen
     *@date 2019-11-28 9:47
     *@param map
     *@return boolean
     *@throws
     */
    public static boolean isEmpty(Map<?, ?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     *判断map是否非空
     *@creator Garwen
     *@date 2019-11-28 9:47
     *@param map
     *@return boolean
     *@throws
     */
    public static boolean isNotEmpty(Map<?, ?> map){
        return !isEmpty(map);
    }

}
