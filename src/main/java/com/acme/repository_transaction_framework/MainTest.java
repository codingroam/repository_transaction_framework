package com.acme.repository_transaction_framework;

import com.acme.repository_transaction_framework.servicetran.factory.ServiceFactory;
import com.acme.repository_transaction_framework.servicetran.service.StudentService;
import com.acme.repository_transaction_framework.servicetran.service.StudentServiceImpl;
import com.acme.repository_transaction_framework.servicetran.service.StudentServiceImpl2;

/**
 * 测试requireNew事务
 *
 *
 */
public class MainTest {

    public static void main(String[] args) {


        ServiceFactory serviceFactory = ServiceFactory.getInstnce();
        StudentService studentService = serviceFactory.getService(StudentServiceImpl.class);
        StudentService studentService2 = serviceFactory.getService(StudentServiceImpl2.class);
        studentService.updateStudent(studentService2);



    }
}
