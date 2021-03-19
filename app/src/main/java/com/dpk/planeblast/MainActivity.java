package com.dpk.planeblast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
private PlaneAnim planeAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planeAnim=new PlaneAnim(this);
        setContentView(planeAnim);
    }
}