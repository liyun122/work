package com.example.android_work;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {


    @BindView(R.id.et2)
    EditText et;

    private DatePicker datapicker;
    private Calendar cal;//显示当前日期
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String str;
    private String str1;
    private String shixiang;
    public static final String INFO = "info";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        initView();

        datapicker = (DatePicker) findViewById(R.id.dp1);
        //获取日历的对象
        cal= Calendar.getInstance();
        //获取年月日时分秒信息
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;//注意点 ，要加一
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        str1 = (year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分");
        setTitle(str1);
        datapicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // TODO Auto-generated method stub
                month++;//注意点。要加一
                str = (year+"年"+month+"月"+day+"日 "+hour+"时"+minute+"分");
                setTitle(year+"年"+month+"月"+day+"日 "+hour+"时"+minute+"分");
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shixiang = et.getText().toString();
                Info info = new Info(str,shixiang);
                Intent i = new Intent();
                i.putExtra(INFO,info);
                setResult(0,i);
                finish();
            }
        });

    }
    private void initView(){
        datapicker = (DatePicker) findViewById(R.id.dp1);

        //这句隐藏头布局
        ((LinearLayout) ((ViewGroup)datapicker.getChildAt(0)).getChildAt(0)).setVisibility(View.GONE);
        cal=Calendar.getInstance();
    }//隐藏dp的表头
}
