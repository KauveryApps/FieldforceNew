package com.kauveryhospital.fieldforce;

public class User {
    private int id;
    private String name, email, gender,password,is_status,sessions,is_ohc,nickname,location;

    public User(String name, String password, String is_status, String sessions, String is_ohc, String nickname, String location) {

        this.name = name;
        this.password=password;
        this.is_status= this.is_status;
        this.sessions=sessions;
        this.is_ohc=is_ohc;
        this.nickname=nickname;
        this.location=location;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

public  String getPassword(){
        return password;
}
public  void setPassword(String password){
        this.password=password;
}

public String getIs_status(){
        return  is_status;
}
public void setIs_status(String is_status){
        this.is_status=is_status;
}
public  String getSessions(){
        return  sessions;
}

public  String getIs_ohc(){
        return  is_ohc;
}
public void setIs_ohc(String is_ohc){
        this.is_ohc=is_ohc;
}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
