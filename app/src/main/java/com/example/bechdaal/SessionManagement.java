package com.example.bechdaal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREFERENCE = "session";
    String SESSION_KEY = "session_user";
    String SESSION_KEY_NAME = "session_user_name";
    String SESSION_KEY_PHONE = "session_user_phone";
    String SESSION_KEY_PASSWORD = "session_user_password";

    String SESSION_ADMIN_KEY = "session_admin_user";
    String SESSION_ADMIN_KEY_EMAIL = "session_admin_email";
    String SESSION_ADMIN_KEY_NAME = "session_admin_name";
    String SESSION_ADMIN_KEY_PHONE = "session_admin_phone";
    String SESSION_ADMIN_KEY_PASSWORD = "session_admin_password";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        String e = user.getEmail();
        String n = user.getName();
        String p = user.getPhone();
        String pwd = user.getPassword();

        editor.putString(SESSION_KEY,e).commit();
        editor.putString(SESSION_KEY_NAME,n).commit();
        editor.putString(SESSION_KEY_PHONE,p).commit();
        editor.putString(SESSION_KEY_PASSWORD,pwd).commit();
    }

    public void saveAdminSession(Admin admin){
        String a = admin.getAdminid();
        String e = admin.getAdminEmail();
        String n = admin.getAdminName();
        String p = admin.getAdminPhone();
        String pwd = admin.getAdminPassword();

        editor.putString(SESSION_ADMIN_KEY,a).commit();
        editor.putString(SESSION_ADMIN_KEY_EMAIL,e).commit();
        editor.putString(SESSION_ADMIN_KEY_NAME,n).commit();
        editor.putString(SESSION_ADMIN_KEY_PHONE,p).commit();
        editor.putString(SESSION_ADMIN_KEY_PASSWORD,pwd).commit();
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY,"none");
    }
    public String getSessionName(){
        return sharedPreferences.getString(SESSION_KEY_NAME,"name");
    }
    public String getSessionPhone(){
        return sharedPreferences.getString(SESSION_KEY_PHONE,"phone");
    }
    public String getSessionPwd(){
        return sharedPreferences.getString(SESSION_KEY_PASSWORD,"password");
    }



    public String getAdminSession(){
        return sharedPreferences.getString(SESSION_ADMIN_KEY,"none");
    }
    public String getAdminSessionEmail(){
        return sharedPreferences.getString(SESSION_ADMIN_KEY_EMAIL,"email");
    }
    public String getAdminSessionName(){
        return sharedPreferences.getString(SESSION_ADMIN_KEY_NAME,"name");
    }
    public String getAdminSessionPhone(){
        return sharedPreferences.getString(SESSION_ADMIN_KEY_PHONE,"phone");
    }
    public String getAdminSessionPwd(){
        return sharedPreferences.getString(SESSION_ADMIN_KEY_PASSWORD,"password");
    }



    public String setSessionPwd(String pwd){
        editor.putString(SESSION_KEY_PASSWORD,pwd).commit();
        return "Password changed";
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,"none").commit();
        editor.putString(SESSION_KEY_NAME,"none").commit();
        editor.putString(SESSION_KEY_PHONE,"none").commit();
        editor.putString(SESSION_KEY_PASSWORD,"none").commit();
    }
    public  void removeAdminSession() {
        editor.putString(SESSION_ADMIN_KEY,"none").commit();
    }

}
