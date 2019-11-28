package org.smart4j.chapter02.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.Utils.PropsUtil;
import org.smart4j.chapter02.helper.DatabaseHelper;
import org.smart4j.chapter02.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);

    /**
     *获取客户列表
     *@creator Garwen
     *@date 2019-11-28 14:45
     *@param
     *@return java.util.List<org.smart4j.chapter02.model.Customer>
     *@throws
     */
    public List<Customer> getCustomerList(){
        String sql = "select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);

    }

    public Customer getCustomer(long id){
        return null;
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldmap){
        return true;
    }

    public boolean createCustomer(Map<String, Object> fieldmap){
        return true;
    }

    public boolean deleteCustomer(long id){
        return true;
    }
}
