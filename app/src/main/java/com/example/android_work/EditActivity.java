package com.example.android_work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    //布局的seekbar
    private SeekBar seekBar;
    //这个为布局中改变的数字
    private Button rate;
    public static final String JINDU = "jindu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        rate = findViewById(R.id.btn5);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jindu = rate.getText().toString();
                Intent i = new Intent();
                i.putExtra(JINDU,jindu);
                setResult(1,i);
                finish();
            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        rate.setText(i + "%");
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}