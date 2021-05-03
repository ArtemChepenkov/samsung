package com.example.fridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Shell extends Activity {

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
    }

    public void openSQL(View view) {
        Intent intent = new Intent(Shell.this,MainActivity.class);
        startActivity(intent);
    }

    public void openInf(View view) {
        Intent intent = new Intent(Shell.this,Fridge.class);
        startActivity(intent);
    }
}
