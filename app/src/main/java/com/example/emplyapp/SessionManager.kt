package com.example.emplyapp

import android.content.Context
import android.content.SharedPreferences

class SessionManager {
    var pref: SharedPreferences
    var edior: SharedPreferences.Editor
    var context: Context
    var PRIVATE_MODE: Int = 0

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        edior = pref.edit()
    }
    companion object {
        val PREF_NAME: String = "SessionDemo"
        val IS_LOGIN: String = "isLogin"
        val KEY_ID: String = "Login_id"
        val KEY_ROLE: String = "Login_role"
        val KEY_USERNAME: String = "username"
    }
    fun createLoginSession(Login_role: String, Login_id: String, username: String) {
        edior.putBoolean(IS_LOGIN,true)
        edior.putString(KEY_ROLE,Login_role)
        edior.putString(KEY_ID,Login_id)
        edior.putString(KEY_USERNAME,username)
        edior.commit()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }
}