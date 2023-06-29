package com.example.memorengandroid.model.Repository.RoomDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "access_token")
    private String accessToken;
    @ColumnInfo(name = "access_token_expiration")
    private String accessTokenExpiration;
    @ColumnInfo(name = "refresh_token")
    private String refreshToken;
    @ColumnInfo(name = "refresh_token_expiration")
    private String refreshTokenExpiration;
    @ColumnInfo(name = "remember_me")
    private Boolean rememberMe;

    public UserEntity(int uid, String name, String surname, String email, String username, String password,
                String accessToken, String accessTokenExpiration, String refreshToken, String refreshTokenExpiration,Boolean rememberMe) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.rememberMe = rememberMe;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(String accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(String refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
}
