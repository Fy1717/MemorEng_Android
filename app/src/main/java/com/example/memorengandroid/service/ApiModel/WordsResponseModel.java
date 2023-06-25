package com.example.memorengandroid.service.ApiModel;

import com.google.gson.JsonArray;

public class WordsResponseModel {
    public JsonArray data;
    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }
}
