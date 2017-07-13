package com.example.clearsky;

/**
 * Created by ofek13 on 10/07/2017.
 */

class User {
    private static String _userName;
    private static String _firstName;
    private static boolean _isAdmin;

    public static boolean getIsAdmin() {
        return _isAdmin;
    }

    public static void set_isAdmin(boolean _isAdmin) {
        User._isAdmin = _isAdmin;
    }

    public static String get_firstName() { return _firstName; }

    public static void set_firstName(String _firstName) {
        User._firstName = _firstName;
    }

    public static String getUserName() {
        return _userName;
    }

    public static void setUserName(String userName) {
        _userName = userName;
    }

}
