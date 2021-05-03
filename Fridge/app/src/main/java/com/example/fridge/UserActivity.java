package com.example.fridge;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class UserActivity extends Activity {
    EditText name, year;
    Button btn_save, btn_delete;

    long userId = 0;
    DataBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name = findViewById(R.id.name);
        year = findViewById(R.id.year);
        btn_delete = findViewById(R.id.btn_delete);
        btn_save = findViewById(R.id.btn_save);
        adapter = new DataBaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            userId = extras.getLong("id");
        if (userId > 0) {
            adapter.open();

            Prod user = adapter.getUser(userId);

            /*name.setText(user.getName());
            year.setText(String.valueOf(user.getDays()));*/

            adapter.close();
        }
        if (userId == 0) {
            btn_delete.setVisibility(View.GONE);
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name = name.getText().toString();
                int u_year = Integer.parseInt(year.getText().toString());
                final Prod user = new Prod(userId, u_name, u_year);
                final int NOTIFICATION_ID = 1;
                long DELAY =  1000L*user.getDays();//86400000
                final NotificationManager[] nm = new NotificationManager[1];
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nm[0] = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification.Builder builder = new Notification.Builder(getApplicationContext());
                        Intent intent = new Intent(UserActivity.this,Shell.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(UserActivity.this,0,intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setTicker("Новое уведомление")
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle("У "+user.getName()+" истёк срок годности");
                        Notification notification = builder.build();
                        nm[0].notify((int) (System.currentTimeMillis() / 1000 ),notification);
                    }
                }, DELAY);
                adapter.open();

                if (userId > 0)
                    adapter.update(user);
                else
                    adapter.insert(user);
                adapter.close();
                goHome();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.open();
                adapter.delete(userId);
                adapter.close();
                goHome();
            }
        });

    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
