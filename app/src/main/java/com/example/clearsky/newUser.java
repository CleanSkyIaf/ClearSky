package com.example.clearsky;

/**
 * Created by ofek13 on 10/07/2017.
 */

class newUser {
    private String _userName;
    private String _password;
    private String _firstName;
    private String _lastName;

    public newUser(String _userName, String _passName, String _firstName, String _lastName) {
        this._userName = _userName;
        this._password = _passName;
        this._firstName = _firstName;
        this._lastName = _lastName;
    }

    public String getFirstName() { return _firstName; }

    public String getLastName() { return _lastName; }

    public String getUserName() {
        return _userName;
    }

    public String getPassword() { return _password; }
}
