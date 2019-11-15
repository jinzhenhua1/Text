package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text.MyApplication.MyApplication;
import com.example.text.bean.User;
import com.xiaoyehai.landsurvey.greendao.UserDao;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    private EditText et_name;
    private TextView tv_name;
    private Button but_add;

    private UserDao userDao;
    private List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        initView();
        initData();
    }

    private void initData(){
        userDao = MyApplication.getmDaoSession().getUserDao();

        showData();//显示数据

    }

    private void initView(){
        et_name = findViewById(R.id.et_name);
        tv_name = findViewById(R.id.tv_name);
        but_add = findViewById(R.id.but_add);

        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                if(name == null || "".equals(name)){
                    Toast.makeText(getApplicationContext(),"名称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(name,"");
                userDao.insert(user);
                showData();//刷新数据
            }
        });
    }

    /**
     * 展示数据
     */
    private void showData(){
        list = userDao.loadAll();
        String name = "";
        if(list != null){
            for(User u : list){
                name += u.getName() + "\n";
            }
        }
        tv_name.setText(name);
    }
}
