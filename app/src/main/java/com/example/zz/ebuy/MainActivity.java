package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();

        final Button login = findViewById(R.id.login);
        final Button signin= findViewById(R.id.signin);
        final EditText login_username=findViewById(R.id.login_username);
        final EditText login_passward=findViewById(R.id.login_passward);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ebuy);
        toolbar.setTitle("eBuy");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check1()) {
                    if (check2()){
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean check1(){
                if(login_username.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(login_passward.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    return true;
                }
                return false;
            }
            private boolean check2() {
                SQLiteDatabase sdb = dbHelper.getReadableDatabase();
                Cursor cursor=sdb.query("userdata",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        String username=cursor.getString(cursor.getColumnIndex("username"));
                        String password=cursor.getString(cursor.getColumnIndex("password"));
                        if (username.equals(login_username.getText().toString().trim())&&password.equals(login_passward.getText().toString().trim())){
                            return true;
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();
                sdb.close();
                return false;
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("signin");
                startActivity(intent);
            }
        });

    }
    protected void onRestart(){
        super.onRestart();
    }
}
