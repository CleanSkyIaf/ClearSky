package com.example.clearsky;

/**
 * Created by ofek13 on 10/07/2017.
 */

class User {
    private static String _userName;

    public static String getUserName() {
        return _userName;
    }

    public static void setUserName(String userName) {
        _userName = userName;
    }
}
