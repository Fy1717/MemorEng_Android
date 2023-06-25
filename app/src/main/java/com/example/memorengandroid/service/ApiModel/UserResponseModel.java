package com.example.memorengandroid.service.ApiModel;

import com.google.gson.JsonObject;

public class UserResponseModel {
    public JsonObject data;
    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
