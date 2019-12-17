package com.example.milkteaapplication.Model;

public class User {
    private String Phone;
    private String Fullname;
    private String Password;
    private String Role;

    public User() {
    }

    public User( String phone, String fullname, String password, String role) {
        this.Phone = phone;
        this.Fullname = fullname;
        this.Password = password;
        this.Role = role;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "Phone='" + Phone + '\'' +
                ", Fullname='" + Fullname + '\'' +
                ", Password='" + Password + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}
