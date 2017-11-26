package com.example.zz.ebuy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Shop> shopList=new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private ShopAdapter ShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();

        dbHelper =new MyDatabaseHelper(this,"userdata.db",null,1);
        dbHelper.getWritableDatabase();

        mDrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);

        final TextView shop_name=findViewById(R.id.shop_name);

        Intent intent=getIntent();
        String username_str=intent.getStringExtra("username");

        initShops();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView .setLayoutManager(layoutManager);
        ShopAdapter = new ShopAdapter(shopList);
        recyclerView.setAdapter(ShopAdapter);

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navView.setCheckedItem(R.id.username_head);
        View headerView = navView.getHeaderView(0);
        ImageView icon_image = headerView.findViewById(R.id.touxiang);
        TextView username_header=headerView.findViewById(R.id.username_head);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor=sdb.query("userdata",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                String username=cursor.getString(cursor.getColumnIndex("username"));
                String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
                String userimage=cursor.getColumnName(cursor.getColumnIndex("icon_image"));
                if (username.equals(username_str)){
                    username_header.setText(nickname);
                    Glide.with(this).load(userimage).asBitmap().placeholder(R.drawable.shop).error(R.drawable.shop).into(icon_image);
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        navView.setCheckedItem(R.id.username_head);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
    }
    public void initShops() {
        try {
            SQLiteDatabase sdb = dbHelper.getReadableDatabase();
            Cursor cursor=sdb.query("shopdata",null,null,null,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    String shop_name=cursor.getString(cursor.getColumnIndex("shopname"));
                    String shop_image=cursor.getString(cursor.getColumnIndex("shopimage"));
                    shopList.add(new Shop(shop_name,shop_image));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(Main2Activity.this,"加载错误",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu .toolbar,menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);break;
            case R.id.AddShop:
                Intent intent=new Intent(Main2Activity.this,Add.class);
                startActivity(intent);break;
            case R.id.AddGood:
                intent = new Intent(Main2Activity.this, AddGood.class);
                startActivity(intent);break;
            case R.id.SearchShop:
                intent=new Intent(Main2Activity.this,SearchShop.class);
                startActivity(intent);break;
            case R.id.SearchGood:
                intent=new Intent(Main2Activity.this,SearchGood.class);
                startActivity(intent);break;
            default:
        }
        return true;
    };

}
