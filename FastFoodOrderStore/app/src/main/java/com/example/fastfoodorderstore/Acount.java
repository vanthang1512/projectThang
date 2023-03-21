package com.example.fastfoodorderstore;

public class Acount{
    private String email;
    private String username;
    private String phone;
    private String address;
    private String photo;

    public Acount() {
    }

    public Acount(String email, String username, String phone, String address, String photo) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
