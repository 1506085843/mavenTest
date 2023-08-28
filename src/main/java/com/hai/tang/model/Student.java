package com.hai.tang.model;


public class Student {

    public String name;
    public int id;
    public int score;

    public Student() {
    }

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Student(int id, String name, int score) {
        this.name = name;
        this.id = id;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(String name, int id) {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
