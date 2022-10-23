package com.example.anees;

public class userinfo {
    private String Name;
    private String User;
    private String Email;
    private String phone;
    private String password;
    public userinfo() {
    }
    public String getUserName() {
        return Name;
    }
    public void setUserName(String NameName) {
        this.Name = NameName;
    }

    public String getUserEmail() {
        return Email;
    }
    public void setUserEmail(String Email) {
        this.Email = Email;
    }

    public String getUserUser() {
        return User;
    }
    public void setUserUser(String User) {
        this.User = User;
    }

    public String getUserpassword() {
        return password;
    }
    public void setUserpassword(String Password) {
        this.password = Password;
    }

    public String getUserphone() {
        return phone;
    }
    public void setUserphone(String Phone) {this.phone = Phone;}

}
