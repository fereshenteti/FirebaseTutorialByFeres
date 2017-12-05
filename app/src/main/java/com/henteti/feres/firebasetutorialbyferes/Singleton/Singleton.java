package com.henteti.feres.firebasetutorialbyferes.Singleton;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Feres on 12/5/2017.
 */

public class Singleton {

    private static volatile Singleton mInstance = null;

    private FirebaseUser user;

    public static Singleton getInstance(Context context) {

        synchronized (Singleton.class) {
            if (mInstance == null) {
                mInstance = new Singleton(context);
            }
        }
        return mInstance;
    }

    private Singleton(Context context) {

    }


    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }
}
