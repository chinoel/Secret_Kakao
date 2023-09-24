package com.devhino.secret_kakao;

public class MainChatList {

    private String name;
    private String chat;
    private String time;

    public String getname() {
        return name;
    }

    public void setPl_name(String pl_name) {
        this.name = pl_name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MainChatList(String pl_name, String chat, String time) {
        this.name = pl_name;
        this.chat = chat;
        this.time = time;
    }
}
