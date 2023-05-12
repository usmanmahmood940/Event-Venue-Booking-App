package com.example.wedding_hall;

import java.io.Serializable;

public class User implements Serializable {
    public String email, phone, names, address, category,image,token;

    public User() {

    }

    public User(String email, String phone, String names, String address, String category) {
        this.email = email;
        this.names = names;
        this.phone = phone;
        this.address = address;
        this.category = category;

    }
    public User(String email,  String phone, String names, String address, String category,String url) {
        this.email = email;
        this.names = names;
        this.phone = phone;
        this.address = address;
        this.category = category;
        this.image = url;

    }
    public User(String email,  String phone, String names, String address, String category,String url,String token) {
        this.email = email;
        this.names = names;
        this.phone = phone;
        this.address = address;
        this.category = category;
        this.image = url;
        this.token = token;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
