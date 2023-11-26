package com.example.exam5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLDao {
    public static DpHelper dpHelper;
    public static SQLiteDatabase db;

    //创建数据库和表
    public static void createtable(Context context) {
        //构建一个 DpHelper 对象，通过构造函数将数据库名指定为 student.db
        dpHelper = new DpHelper(context, "student.db", null, 1);
        //使用getWritableDatabase方法获取一个SQLiteDataBase对象，然后就可以用这个对象进行增删改查操作
        db = dpHelper.getWritableDatabase();
    }

    //向数据库中添加数据
    public static void insert(String id, String name, String sex, String phone){
        db.execSQL("INSERT INTO student(id,name,sex,phone) VALUES(?,?,?,?)",
                new String[]{id, name, sex, phone});
    }

    //删除记录
    public static void delete(String id){
        db.execSQL("DELETE FROM student WHERE id = ?",
                new String[]{id});
    }

    //更新数据库记录（当学号没有更新时）
    public static void update(String id, String name, String sex, String phone){
        db.execSQL("UPDATE student SET name = ?, sex = ?, phone = ? WHERE id = ?",
                new String[]{name, sex, phone, id});
    }

    //更新数据库记录（当学号也要进行更新时）
    public static void updateNewId(String newId, String name, String sex, String phone, String id){
        db.execSQL("UPDATE student SET id = ?, name = ?, sex = ?, phone = ? WHERE id = ?",
                new String[]{newId, name, sex, phone, id});
    }

    // 查询某一记录
    public static Student select(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM student WHERE id = ?", new String[]{id});
        Student student = null;   //创建一个Student类的对象以保存查询到的数据

        if (cursor != null && cursor.moveToFirst()) {
            // 从cursor中获取数据的索引
            int idColumn = cursor.getColumnIndex("id");
            int nameColumn = cursor.getColumnIndex("name");
            int sexColumn = cursor.getColumnIndex("sex");
            int phoneColumn = cursor.getColumnIndex("phone");

            do{
                //保存查询到的学生数据
                String studentId = cursor.getString(idColumn);
                String studentName = cursor.getString(nameColumn);
                String studentSex = cursor.getString(sexColumn);
                String studentPhone = cursor.getString(phoneColumn);
                student = new Student(studentId, studentName, studentSex, studentPhone);
            }while (cursor.moveToNext());
        }
        // 关闭Cursor
        if (cursor != null) {
            cursor.close();
        }
        return student;
    }

    //查询全部记录
    public static List<Student> selectAll(){
        List<Student> students = new ArrayList<>(); //初始化一个空的ArrayList<Student>
        Cursor cursor = db.rawQuery("SELECT * FROM student", null);
        if (cursor != null && cursor.moveToFirst()) {
            // 从cursor中获取数据的索引
            int idColumn = cursor.getColumnIndex("id");
            int nameColumn = cursor.getColumnIndex("name");
            int sexColumn = cursor.getColumnIndex("sex");
            int phoneColumn = cursor.getColumnIndex("phone");

            do { //循环遍历查询的结果
                // 保存查询到的学生数据
                String studentId = cursor.getString(idColumn);
                String studentName = cursor.getString(nameColumn);
                String studentSex = cursor.getString(sexColumn);
                String studentPhone = cursor.getString(phoneColumn);

                Student student = new Student(studentId, studentName, studentSex, studentPhone); //将每一行的数据都保存为一个Student对象
                students.add(student); //添加到列表中
            } while (cursor.moveToNext());
        }
        // 关闭Cursor
        if (cursor != null) {
            cursor.close();
        }
        return students;
    }

}
