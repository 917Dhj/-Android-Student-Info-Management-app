package com.example.exam5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class addStu extends AppCompatActivity {
    EditText etId, etName, etSex, etPhone;
    Button bt;
    ImageButton imgBt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stuinfo);
        imgBt = this.findViewById(R.id.imageButton);
        tv = this.findViewById(R.id.title);
        tv.setText("添加新学生");
        etId = this.findViewById(R.id.id);
        etName = this.findViewById(R.id.name);
        etSex = this.findViewById(R.id.sex);
        etPhone = this.findViewById(R.id.phone);
        bt = this.findViewById(R.id.submit);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入的信息
                String InputId = etId.getText().toString();
                String InputName = etName.getText().toString();
                String InputSex = etSex.getText().toString();
                String InputPhone = etPhone.getText().toString();

                //识别输入的完整性
                if (InputId.length() * InputName.length() > 0){ //判断学号和姓名是否为空
                    //获取当前数据库中的所有学生信息
                    List<Student> students = SQLDao.selectAll();
                    List<String> allId = new ArrayList<>(); //获取所有学生的学号
                    for (Student student : students) { //遍历学生信息列表，把id都添加到allId中
                        allId.add(student.getId());
                    }
                    if(!allId.contains(InputId)){ //判断新输入的学号是否与数据库中原有的学号相同，如果不相同则执行添加操作
                        SQLDao.insert(InputId, InputName, InputSex, InputPhone); //向数据库中添加用户输入的信息
                        Intent intent = new Intent(addStu.this, MainActivity.class); //返回主界面
                        addStu.this.startActivity(intent);
                        Toast.makeText(addStu.this, "新学生添加成功", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(addStu.this, "学号重复，请更换学号", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(addStu.this, "请将姓名或学号填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 为左上角的返回键设置点击事件
        imgBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addStu.this, MainActivity.class); //返回主界面
                addStu.this.startActivity(intent);
            }
        });
    }
}