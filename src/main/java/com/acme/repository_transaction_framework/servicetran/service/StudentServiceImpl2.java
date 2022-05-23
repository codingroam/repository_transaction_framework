package com.acme.repository_transaction_framework.servicetran.service;

import com.acme.repository_transaction_framework.annotation.Transaction;
import com.acme.repository_transaction_framework.repository.dao.StudentDao;
import com.acme.repository_transaction_framework.repository.factory.DaoFactory;
import com.acme.repository_transaction_framework.repository.po.StudentPO;

import java.util.UUID;

public  class StudentServiceImpl2 implements StudentService {
        @Override
        @Transaction
        public void updateStudent(StudentService studentService) {
            DaoFactory daoFactory = DaoFactory.getInstnce();
            StudentDao studentDao = daoFactory.getDao(StudentDao.class);

            // List<StudentPO> all = studentDao.findAll();
            StudentPO byPrimaryKey = studentDao.findByPrimaryKey(1);
            System.out.println("查询结果："+byPrimaryKey.toString());
            byPrimaryKey.setName(UUID.randomUUID().toString().substring(0,10));
            studentDao.updateByPrimaryKey(byPrimaryKey,1);
            StudentPO PrimaryKey = studentDao.findByPrimaryKey(1);

            System.out.println("更新后的查询结果："+PrimaryKey);
           // int i = 1/0;
            System.out.println("end");
        }


        public void updateStudentRequireNew(){
            DaoFactory daoFactory = DaoFactory.getInstnce();
            StudentDao studentDao = daoFactory.getDao(StudentDao.class);

            // List<StudentPO> all = studentDao.findAll();
            StudentPO byPrimaryKey = studentDao.findByPrimaryKey(2);
            System.out.println("查询结果RequireNew："+byPrimaryKey.toString());
            byPrimaryKey.setName(UUID.randomUUID().toString().substring(0,10));
            studentDao.updateByPrimaryKey(byPrimaryKey,2);
            StudentPO PrimaryKey = studentDao.findByPrimaryKey(2);

            System.out.println("更新后的查询结果RequireNew："+PrimaryKey);
            System.out.println("end");
            //int i = 1/0;
        }


    }