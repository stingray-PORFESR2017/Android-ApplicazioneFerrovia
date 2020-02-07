package com.example.traininfo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


class LoginManager {
    private static String username;
    private static String password;
    private static File userfile;
    private static File passfile;
    private static final String filename_username = "login_user";
    private static final String filename_password = "login_password";
    private final File path;
    private static Context context;
    private static boolean isSet;

    LoginManager(Context c) {
        this.context = c;
        path= c.getFilesDir();
        this.userfile = new File(path, filename_username);
        this.passfile = new File(path, filename_password);
        username = readFile(userfile);
        password = readFile(passfile);

    }

    private String readFile(File file) {
        byte[] bytes= new byte[(int)file.length()];
        String st;
        try {
            FileInputStream inputStream= new FileInputStream(file);
            inputStream.read(bytes);
            st= new String(bytes);
        } catch (Exception e) {
            Log.d("LoginManager", "File non presente"+file.getAbsolutePath());
            isSet=false;
            return "";
        }
        isSet=true;
        return st;
    }



    static void writeCredentials (final String u, final String p) {

        AsyncTaskVerifyCredential verifyCredential= new AsyncTaskVerifyCredential(u, p, context, new AsyncTaskVerifyCredential.AsyncLoginResponse() {
            @Override
            public void loginResponse(boolean result) {
                if (result){
                    FileOutputStream userstream;

                    FileOutputStream passwstream;

                    try {
                        //scrittura username
                        userstream= new FileOutputStream(userfile);
                        userstream.write(u.getBytes());
                        userstream.close();

                        //scrittura password
                        passwstream= new FileOutputStream(passfile);
                        passwstream.write(p.getBytes());
                        passwstream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSet=false;
                        LoginFrag.interface_login();
                        return;
                    }

                    username=u;
                    password=p;


                    isSet=true;
                    LoginFrag.interface_logout();
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                    Log.d("LoginManager","Credenziali archiviate correttamente");
                    return;
                } else {
                    LoginFrag.interface_login();
                    isSet=false;
                    Toast.makeText(context, R.string.incorrect_credential, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        verifyCredential.execute();

    }

    static boolean deleteCredential() {
        if(userfile.delete() && passfile.delete()) {
            Log.d("LoginManager","Credenziali rimosse correttamente");
            isSet=false;
            LoginFrag.interface_login();
            return true;
        } else {
            Log.d("LoginManager","Errore eliminazione credenziali");
            return false;
        }
    }

    static boolean isSet() {
        return  isSet;
    }

    static String getUsername() {
        return isSet() ? username : null;
    }

    static String getPassword() {
        return isSet() ? password : null;
    }

}
