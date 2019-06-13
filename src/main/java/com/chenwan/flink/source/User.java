package com.chenwan.flink.source;

import java.math.BigDecimal;

/**
 * @program: flink-learn
 * @description:
 * @author: cliffcw
 * @create: 2019-06-13 21:13
 */
public class User {
    public int id;
    public String name;
    public String password;
    public long age;

    public User() {
    }

    public User(int id, String name, String password, long age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
