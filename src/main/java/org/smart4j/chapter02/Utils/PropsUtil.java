package org.smart4j.chapter02.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {
    private static final Logger LOGGER=LoggerFactory.getLogger(PropsUtil.class);
    /**
     * load properties
     */
    public static Properties loadProps(String fileName){
        Properties props=null;
        InputStream is=null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is==null){
                throw new FileNotFoundException(fileName + "file is not found.");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }

        return props;


    /**
     * 获取字符型属性，默认为空字符串
     */
    public static String getString(Properties props){
        return getString(props, "");
    }

    /**
     * 获取字符型属性，可指定默认值
     */
    public static String getString(){

    }
}
