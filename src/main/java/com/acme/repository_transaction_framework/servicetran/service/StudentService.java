package com.acme.repository_transaction_framework.servicetran.service;

import com.acme.repository_transaction_framework.annotation.Transaction;

public interface StudentService extends BaseService {




    @Transaction
    public void updateStudent(StudentService studentService);

    @Transaction(type="requireNew")
    void updateStudentRequireNew();
}
