package com.everythingcrms.entity;

public class UserTest {
    int age;
    String name;
    String gender;
    String id;



    public UserTest(int age, String name, String gender, String id) {
        this.age = age;
        this.name=name;
        this.gender=gender;
        this.id = id;


    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
    public String getId(){
        return id;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;

    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }

}
