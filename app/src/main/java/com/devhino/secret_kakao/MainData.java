package com.devhino.secret_kakao;

import java.util.ArrayList;

public class MainData {
    private int iv_profile;
    private String pl_name;
    private String pl_text;

    private String pl_id;

    private ArrayList<String> list;

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public MainData(int iv_profile, String pl_name, String pl_text, String pl_id, ArrayList<String> list) {
        this.iv_profile = iv_profile;
        this.pl_name = pl_name;
        this.pl_text = pl_text;
        this.pl_id = pl_id;
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList() {
        this.list = list;
    }

    public String getPl_id() {
        return pl_id;
    }

    public void setPl_id(String pl_id) {
        this.pl_id = pl_id;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getPl_name() {
        return pl_name;
    }

    public void setPl_name(String pl_name) {
        this.pl_name = pl_name;
    }

    public String getPl_text() {
        return pl_text;
    }

    public void setPl_text(String pl_text) {
        this.pl_text = pl_text;
    }
}
