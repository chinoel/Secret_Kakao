package com.devhino.secret_kakao;

public class inChatData {
    private String times;
    private String text;
    private String name;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public inChatData(String name, String text, String times) {
        this.name = name;
        this.text = text;
        this.times = times;
    }
}
