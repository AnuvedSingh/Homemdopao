package com.example.asus.homemdopao;

import android.app.Application;
import android.graphics.Typeface;

import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

/**
 * Created by Asus on 09/11/2016.
 */

public class Font extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "font/oswalt.ttf"))
                .create();
        TypefaceHelper.init(typeface);
    }
}
