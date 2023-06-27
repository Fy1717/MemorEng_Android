package com.example.memorengandroid.model.Repository.RoomDB;

import android.app.Application;

public class UserRepository {
    UserRoomDatabase userRoomDatabase;
    UserDao userDao;
    private UserEntity user;

    public UserRepository(Application application) {
        userRoomDatabase = UserRoomDatabase.getDatabase(application);
        userDao = userRoomDatabase.userDao();
        user = userDao.getUser();
    }

    public void insertUser(UserEntity userEntity) {
        userRoomDatabase.userDao().add(userEntity);
    }

    public UserEntity getUser() {
        return user;
    }
}