package com.example.clearsky;

/**
 * Created by ofek13 on 10/07/2017.
 */

class User {
    private static String _userName;
    private static String _passName;
    private static String _firstName;
    private static String _lastName;

    public static String get_firstName() { return _firstName; }

    public static void set_firstName(String _firstName) {
        User._firstName = _firstName;
    }

    public static String get_lastName() { return _lastName; }

    public static void set_lastName(String _lastName) {
        User._lastName = _lastName;
    }

    public static String getUserName() {
        return _userName;
    }

    public static void setUserName(String userName) {
        _userName = userName;
    }

    public static String get_passName() { return _passName; }

    public static void set_passName(String _passName) {
        User._passName = _passName;
    }
}
