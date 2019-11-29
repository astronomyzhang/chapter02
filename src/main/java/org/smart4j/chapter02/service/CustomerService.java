package org.smart4j.chapter02.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.helper.DatabaseHelper;
import org.smart4j.chapter02.model.Customer;

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

    /**
     *
     *@author Garwen
     *@date 2019/11/28 22:40
     *@params [id]
     *@return org.smart4j.chapter02.model.Customer
     *@throws
     */
    public Customer getCustomer(long id){
        String sql = "SELECT * FROM " + Customer.class.getSimpleName().toLowerCase() + " WHERE id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 更新客户
     *@author Garwen
     *@date 2019/11/28 22:43
     *@params [id, fieldmap]
     *@return boolean
     *@throws
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldmap){
        return DatabaseHelper.updateEntity(Customer.class, id,fieldmap);
    }

    /**
     *创建客户
     *@author Garwen
     *@date 2019/11/28 22:43
     *@params [fieldmap]
     *@return boolean
     *@throws
     */
    public boolean createCustomer(Map<String, Object> fieldmap){
        return DatabaseHelper.insertEntity(Customer.class, fieldmap);
    }

    /**
     *删除客户
     *@author Garwen
     *@date 2019/11/28 22:44
     *@params [id]
     *@return boolean
     *@throws
     */
    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
