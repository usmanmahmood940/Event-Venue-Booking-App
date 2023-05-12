package com.example.wedding_hall;

public class FeedBack {
    String name, detail,email,category;

    public FeedBack(){

    }
    public FeedBack(String name, String feedback)
    {
        this.name = name;
        this.detail = feedback;
    }

    public FeedBack(String name, String feedback, String category) {
        this.name = name;
        this.detail = feedback;
        this.category = category;
    }

    public FeedBack(String name, String detail, String email, String category) {
        this.name = name;
        this.detail = detail;
        this.email = email;
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

