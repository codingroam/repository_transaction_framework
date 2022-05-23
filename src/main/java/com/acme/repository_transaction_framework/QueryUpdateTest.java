package com.acme.repository_transaction_framework;

import com.acme.repository_transaction_framework.repository.dao.StudentDao;
import com.acme.repository_transaction_framework.repository.factory.DaoFactory;
import com.acme.repository_transaction_framework.repository.po.StudentPO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QueryUpdateTest {

    private static Logger logger = LoggerFactory.getLogger(QueryUpdateTest.class);
    private DaoFactory instnce = DaoFactory.getInstnce();
    private StudentDao studentDao = instnce.getDao(StudentDao.class);


    @Test
    public void testQuery(){

        List<StudentPO> allList = studentDao.findAll();
        logger.info("查询全部结果为：");
        allList.forEach(System.out :: println);
        StudentPO byPrimaryKey = studentDao.findByPrimaryKey(1);
        logger.info("查询主键为1的数据为："+byPrimaryKey.toString());
    }

    @Test
    public void testUpdate(){

        StudentPO studentPO = new StudentPO();
        studentPO.setName("wang");
        studentPO.setId(1);
        studentPO.setAge(28);
        studentPO.setGender(1);
        studentDao.updateByPrimaryKey(studentPO,1);
        logger.info("更新："+studentPO);
        this.testQuery();

    }

}
