package com.example.traininfo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginManager {
    private static String username;
    private static String password;
    //private String imei;
    private File userfile;
    private File passfile;
    private final String filename_username = "login_user";
    private final String filename_password = "login_password";
    private final File path;
    private final Context context;
    private static boolean isSet;

    LoginManager(Context c) {
        this.context = c;
        path= c.getFilesDir();
        this.userfile = new File(path, filename_username);
        this.passfile = new File(path, filename_password);
        username=readFile(userfile);
        password=readFile(passfile);

    }

    private String readFile(File file) {
        byte[] bytes= new byte[(int)file.length()];
        String st;
        try {
            FileInputStream inputStream= new FileInputStream(file);
            inputStream.read(bytes);
            st= new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            isSet=false;
            return "";
        }
        isSet=true;
        return st;
    }

    boolean writeCredentials (String u, String p) {
        FileOutputStream userstream;
        FileOutputStream passwstream;
        try {
            //scrittura username
            userstream= new FileOutputStream(this.userfile);
            userstream.write(u.getBytes());
            userstream.close();

            //scrittura password
            passwstream= new FileOutputStream(this.passfile);
            passwstream.write(p.getBytes());
            passwstream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        username=u;
        password=p;
        isSet=true;
        Log.d("LoginManager","Credenziali archiviate correttamente");

        return true;
    }

    boolean deleteCredential() {
        if(userfile.delete() && passfile.delete()) {
            Log.d("LoginManager","Credenziali rimosse correttamente");
            isSet=false;
            return true;
        } else {
            Log.d("LoginManager","Errore eliminazione credenziali");
            return false;
        }
    }

    boolean isSet() {
        return  isSet;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
