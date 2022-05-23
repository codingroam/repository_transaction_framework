package com.acme.repository_transaction_framework.repository.po;


import com.acme.repository_transaction_framework.annotation.Table;
import com.acme.repository_transaction_framework.repository.po.BasePO;

@Table(name="student",key="id")
public class StudentPO extends BasePO {

    private int id;
    private String no;
    private String name;
    private int age;
    private int gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "StudentPO{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
