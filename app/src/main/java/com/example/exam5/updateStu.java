package com.example.exam5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class updateStu extends AppCompatActivity {
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
        tv.setText("修改学生信息");
        etId = this.findViewById(R.id.id);
        etName = this.findViewById(R.id.name);
        etSex = this.findViewById(R.id.sex);
        etPhone = this.findViewById(R.id.phone);
        bt = this.findViewById(R.id.submit);

        //接收变量listId
        Intent intent = getIntent();
        int listId = intent.getIntExtra("listId", 0);

        List<Student> students = SQLDao.selectAll(); //获取数据库中全部学生信息并储存到列表
        Student student = students.get(listId); //根据索引获取选中的学生对象

        //将选中的学生信息填写到对应的EditText中
        etId.setText(student.getId());
        etName.setText(student.getName());
        etSex.setText(student.getSex());
        etPhone.setText(student.getPhone());

        //为提交按钮设置点击事件
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入的信息
                String InputId = etId.getText().toString();
                String InputName = etName.getText().toString();
                String InputSex = etSex.getText().toString();
                String InputPhone = etPhone.getText().toString();

                //识别输入的完整性
                String oriId = student.getId(); //保存一下选中的学生原本的学号
                if (InputId.length() * InputName.length() > 0){ //判断学号和姓名是否为空
                    //获取当前数据库中的所有学生信息
                    List<Student> students = SQLDao.selectAll();
                    List<String> allId = new ArrayList<>(); //获取所有学生的学号
                    for (Student student : students) { //遍历学生信息列表，把id都添加到allId中
                        allId.add(student.getId());
                    }
                    if (Integer.parseInt(InputId) != Integer.parseInt(oriId)){ //如果新输入的学号与原来的不一样
                        //判断新输入的学号是否与数据库中有重复，如果没有重复则执行更新操作
                        if (!allId.contains(InputId)){
                            SQLDao.updateNewId(InputId, InputName, InputSex, InputPhone, oriId); // 更新学生信息
                            Intent intent = new Intent(updateStu.this, MainActivity.class); //返回主界面
                            updateStu.this.startActivity(intent);
                            Toast.makeText(updateStu.this, "修改学生成功", Toast.LENGTH_SHORT).show();
                        }
                        else { // 如果新输入的学号与数据库中的重复了
                            Toast.makeText(updateStu.this, "学号重复，请更换学号", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else { //新输入的学号与原来的一样
                        SQLDao.update(InputId, InputName, InputSex, InputPhone); //更新学生信息
                        Intent intent = new Intent(updateStu.this, MainActivity.class); //返回主界面
                        updateStu.this.startActivity(intent);
                        Toast.makeText(updateStu.this, "修改学生成功", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(updateStu.this, "请将姓名或学号填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 为左上角的返回键设置点击事件
        imgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateStu.this, MainActivity.class); //返回主界面
                updateStu.this.startActivity(intent);
            }
        });
    }
}