package com.example.memorengandroid.service.ApiModel;

import com.google.gson.JsonObject;

public class ResponseModel {
    public JsonObject data;
    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
