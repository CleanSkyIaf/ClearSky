package com.example.clearsky;

/**
 * Created by ofek13 on 10/07/2017.
 */

class newUser {

    private String _userName;
    private String _password;
    private String _firstName;
    private String _lastName;
    private boolean _isAdmin = false;
    private boolean _isAcceptedUser = false;

    public newUser(String userName, String passName, String firstName, String lastName) {

        this._userName = userName;
        this._password = passName;
        this._firstName = firstName;
        this._lastName = lastName;
    }

    public String getFirstName() { return _firstName; }

    public String getLastName() { return _lastName; }

    public String getUserName() {
        return _userName;
    }

    public String getPassword() { return _password; }

    public boolean getIsAdmin() { return _isAdmin; }

    public boolean getIsAcceptedUser() { return _isAcceptedUser; }
}
