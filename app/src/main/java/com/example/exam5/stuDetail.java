package com.example.exam5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class stuDetail extends AppCompatActivity {
    TextView tvId, tvName, tvSex, tvPhone;
    ImageButton imgBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_detail);
        imgBt = this.findViewById(R.id.imageButton);
        tvId = this.findViewById(R.id.id);
        tvName = this.findViewById(R.id.name);
        tvSex = this.findViewById(R.id.sex);
        tvPhone = this.findViewById(R.id.phone);

        // 接收参数getId，保存为id
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        // 调用查询某一学生记录的select方法获取选中的学生的信息，保存到一个Student对象中
        Student student = SQLDao.select(id);

        //将选中的学生信息填写到对应的TextView中
        tvId.setText(student.getId());
        tvName.setText(student.getName());
        tvSex.setText(student.getSex());
        tvPhone.setText(student.getPhone());

        imgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stuDetail.this, MainActivity.class);
                stuDetail.this.startActivity(intent);
            }
        });
    }
}