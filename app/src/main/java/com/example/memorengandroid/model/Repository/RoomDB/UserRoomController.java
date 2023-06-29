package com.example.memorengandroid.model.Repository.RoomDB;

import android.content.Context;
import android.util.Log;

import java.net.ContentHandler;

public class UserRoomController {
    private UserRoomDatabase db;

    public UserRoomController(Context context) {
        this.db = UserRoomDatabase.getDatabase(context);
    }

    public UserEntity getUserFromRoom() {
        try {
            return db.userDao().getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setUserToRoom(String name, String surname, String username, String email,
                              String password, String accessToken, String accessTokenExpiration,
                              String refreshToken, String refreshTokenExpiration, Boolean rememberMe) {
        deleteUserInRoom();

        UserEntity userEntity = new UserEntity(
                1, name, surname, email, username, password,
                accessToken, accessTokenExpiration, refreshToken, refreshTokenExpiration,
                rememberMe);

        try {
            userEntity.setUid(1);
            userEntity.setEmail(email);
            userEntity.setUsername(username);
            userEntity.setName(name);
            userEntity.setSurname(surname);
            userEntity.setPassword(password);
            userEntity.setAccessToken(accessToken);
            userEntity.setAccessTokenExpiration(accessTokenExpiration);
            userEntity.setRefreshToken(refreshToken);
            userEntity.setRefreshTokenExpiration(refreshTokenExpiration);
            userEntity.setRememberMe(rememberMe);

            db.userDao().add(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            userEntity = db.userDao().getUser();

            Log.i("ROOM_CONTROLLER", "User from room : " + userEntity.getName() + " " + userEntity.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserInRoom(String name, String surname, String username, String email,
                                 String password, String accessToken, String accessTokenExpiration,
                                 String refreshToken, String refreshTokenExpiration, Boolean rememberMe) {
        setUserToRoom(name, surname, username, email,
                password, accessToken, accessTokenExpiration,
                refreshToken, refreshTokenExpiration, rememberMe);
    }

    public void deleteUserInRoom() {
        try {
            db.userDao().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
