package org.smart4j.chapter02.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter02.helper.DatabaseHelper;
import org.smart4j.chapter02.model.Customer;
import org.smart4j.chapter02.service.CustomerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Unit Test
 *@author Garwen
 *@date 2019-11-29 10:17
 */

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() throws IOException {
        String file = "sql/customer_init.sql";
        DatabaseHelper.executeSqlfile(file);
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomerTest(){
        Customer customer = customerService.getCustomer(1);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "customer03");
        map.put("contact", "Sam");
        map.put("telephone", "12345678754");
        map.put("email", "sam@gmail.com");
        boolean result = customerService.createCustomer(map);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest(){
        long id=2;
        Map<String, Object> fieldmap = new HashMap<>();
        fieldmap.put("contact", "Eric");
        boolean result = customerService.updateCustomer(id, fieldmap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest(){
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
