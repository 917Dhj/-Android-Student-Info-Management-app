package com.example.exam5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button bt;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = this.findViewById(R.id.listview);
        registerForContextMenu(lv); //为listview注册一个上下文菜单
        bt = this.findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addStu.class);
                MainActivity.this.startActivity(intent);
            }
        });
        SQLDao.createtable(this); //创建数据库和student表
        ViewInit(); //配置listview

        // 为listview设置点击事件，点击任意一个学生就会跳转到该学生的信息展示页面
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position代表了点击的listview项的位置，然后用getItemAtPosition方法就可以获得该listview项的内容
                //注意listview的每一个项都是一个HashMap，所以保存的时候也要用HashMap
                HashMap<String, String> student = (HashMap<String, String>) parent.getItemAtPosition(position);
                String getId = student.get("id"); // 从HashMap中获取该学生的学号
                Intent intent = new Intent(MainActivity.this, stuDetail.class);
                intent.putExtra("id", getId); // 将选中学生的学号传递给stuDetail
                MainActivity.this.startActivity(intent);
            }
        });
    }


    //为listview适配SimpleAdapter并显示数据库中的信息
    private void ViewInit() {
        List<Student> students = SQLDao.selectAll(); //selectAll函数返回一个List<Student>类型的对象
        List<String> allId = new ArrayList<>(); //allId是一个保存了所有学生学号的list
        List<String> allName = new ArrayList<>(); //allName是一个保存了所有学生姓名的list
        for (Student student : students) { //遍历列表，把id都添加到allId中，把name都添加到allName中
            allId.add(student.getId());
            allName.add(student.getName());
        }
        //转换为数组
        String[] Ids = allId.toArray(new String[0]);
        String[] Names = allName.toArray(new String[0]);

        // 定义Map<>类型的ArrayList集合，把每一对id和name的值放入map中，再把map加入到list中
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        for (int i = 0; i < Ids.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id",Ids[i]);
            map.put("name",Names[i]);
            lists.add(map);
        }
        // 定义具体的SimpleAdapter对象，并绑定到listview
        simpleAdapter = new SimpleAdapter(this, lists, R.layout.listview_layout,
                new String[]{"id", "name"}, new int[]{R.id.id, R.id.name});
        lv.setAdapter(simpleAdapter);
    }

    //为listview创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //将XML实例化为Menu类的对象
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_layout, menu);
    }

    //设置上下文菜单的响应事件
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId(); //获取点击的菜单项的id
        int listId = (int) info.id; //获取长按位置的listview项的索引，储存为listId
        if (itemId == R.id.updateStu) { //点击修改学生时响应
            Intent intent = new Intent(MainActivity.this, updateStu.class);
            intent.putExtra("listId", listId); // 将listId的值传递给updateStu
            MainActivity.this.startActivity(intent); // 跳转到updateStu页面
        } else if (itemId == R.id.delStu) { //点击删除学生时响应
            List<Student> students = SQLDao.selectAll(); //获取数据库中全部学生信息并储存到列表
            Student student = students.get(listId); //根据索引获取列表中对应学生对象
            String stuId = student.getId(); // 在该学生对象中获取其学号保存为stuId
            SQLDao.delete(stuId); //执行删除操作
            Intent intent = new Intent(MainActivity.this, MainActivity.class); //刷新页面来更新listview的内容
            startActivity(intent);
            Toast.makeText(MainActivity.this, "学生已删除", Toast.LENGTH_SHORT).show();
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }
}