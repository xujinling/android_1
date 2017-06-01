package com.android.listview;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<InfoBean> listBean;
    private DataAdapter dataAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView= (ListView) findViewById(R.id.listView);   //初始化控件listView;
        listBean=new ArrayList<>();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置dialog对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.back);
                builder.setTitle("温馨提示！");
                LayoutInflater layoutInflater=getLayoutInflater();
                View v=layoutInflater.inflate(R.layout.dialog_layout,null);
                final EditText dialog_title= (EditText) v.findViewById(R.id.dialog_title);
                final EditText dialog_money= (EditText) v.findViewById(R.id.dialog_money);
                final DatePicker dialog_time= (DatePicker) v.findViewById(R.id.dialog_time);
                builder.setView(v); //将布局添加到对话框中
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        InfoBean infoBean=new InfoBean();
                        infoBean.setTitle(dialog_title.getText().toString());
                        infoBean.setMoney(dialog_money.getText().toString());
                        int year=dialog_time.getYear();
                        int month=dialog_time.getMonth();
                        int day=dialog_time.getDayOfMonth();
                        String timeData=year+"/"+(month+1)+"/"+day;
                        infoBean.setTime(timeData);
                        dbHelper.insertData(infoBean);  //将数据插入到数据库
                        listBean.add(infoBean);
                        dataAdapter.notifyDataSetInvalidated();   //刷新列表
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();    //显示对话框
            }
        });
        dbHelper=new DBHelper(this);    //dbHelper对象
        showList();
        dataAdapter=new DataAdapter(listBean,this);
        listView.setAdapter(dataAdapter);   //为listView设置适配器

    }

    //从数据库中获取数据
    public void showList(){
        //查询数据
        Cursor cursor=dbHelper.getCursor();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    InfoBean infoBean=new InfoBean();
                    infoBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    infoBean.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                    infoBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                    listBean.add(infoBean); //将数据添加到list集合中
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,ChartActivity.class);
            intent.putExtra("charts", (Serializable) listBean);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
