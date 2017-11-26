package com.example.zz.ebuy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.ContentValues;
import android.widget.EditText;
import android.widget.Toast;

public class signin extends AppCompatActivity {

    private MyDatabaseHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        dbHelper =new MyDatabaseHelper(this,"UserData.db",null,1) ;
        dbHelper.getWritableDatabase();

        Button signyes= findViewById(R.id.signyes);
        Button signno = findViewById(R.id.signno);
        final EditText signin_username=findViewById(R.id.signin_username);
        final EditText signin_passward=findViewById(R.id.signin_passward);
        final EditText resignin_passward=findViewById(R.id.resignin_passward);

        signno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(signin.this, MainActivity.class);
                startActivity(intent);
            }
        });
        signyes .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()&&!check2()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("username", signin_username.getText().toString().trim());
                    values.put("password", signin_passward.getText().toString().trim());
                    db.insert("userdata", null, values);
                    values.clear();
                    Toast.makeText(signin.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin.this, Main2Activity.class);
                    startActivity(intent);
                }
            }
            private boolean check1(){
                if(signin_username.getText().toString().trim().equals("")){
                    Toast.makeText(signin.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(signin_passward.getText().toString().trim().equals("")){
                    Toast.makeText(signin.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if(!signin_passward.getText().toString().trim().equals(resignin_passward.getText().toString().trim())) {
                    Toast.makeText(signin.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
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
                        if (username.equals(signin_username.getText().toString().trim())){
                            Toast.makeText(signin.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();
                sdb.close();
                return false;
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        dbHelper.close();
    }

}