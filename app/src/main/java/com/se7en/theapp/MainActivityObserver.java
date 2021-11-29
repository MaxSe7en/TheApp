package com.se7en.theapp;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class MainActivityObserver implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner){
        Log.i("Tag", "created");
    }
    @Override
    public void onResume(@NonNull LifecycleOwner owner){
        Log.i("Tag", "resumed");
    }
    @Override
    public void onPause(@NonNull LifecycleOwner owner){
        Log.i("Tag", "paused");
    }
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner){
        Log.i("Tag", "Destroyed");
    }


    private void getMain(){

    }

}
