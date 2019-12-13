package com.example.text.netty.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.text.R;
import com.example.text.bean.Student;
import com.example.text.netty.NettyListener;

import org.apache.commons.lang3.StringUtils;

import io.netty.util.internal.StringUtil;

public class NettyActivity extends AppCompatActivity {
    private EditText activity_netty_et_ip1;
    private EditText activity_netty_et_ip2;
    private EditText activity_netty_et_ip3;
    private EditText activity_netty_et_ip4;

    private Button activity_netty_btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netty);
        initView();
    }

    private void initView(){
        activity_netty_et_ip1 = findViewById(R.id.activity_netty_et_ip1);
        activity_netty_et_ip2 = findViewById(R.id.activity_netty_et_ip2);
        activity_netty_et_ip3 = findViewById(R.id.activity_netty_et_ip3);
        activity_netty_et_ip4 = findViewById(R.id.activity_netty_et_ip4);

        activity_netty_btn_connect = findViewById(R.id.activity_netty_btn_connect);
        activity_netty_btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip1 = activity_netty_et_ip1.getText().toString();
                String ip2 = activity_netty_et_ip2.getText().toString();
                String ip3 = activity_netty_et_ip3.getText().toString();
                String ip4 = activity_netty_et_ip4.getText().toString();
                if(StringUtils.isEmpty(ip1) || StringUtils.isEmpty(ip2) || StringUtils.isEmpty(ip3) || StringUtils.isEmpty(ip4)){
                    Toast.makeText(NettyActivity.this,"IP不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                Student student = new Student();
                student.setName("张三");
                student.setClassName("3班");
                student.setLevel("高一");

                NettyController controller = new NettyController(ip,8080);
                controller.sendTest(student, "01", new NettyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("失败");
                    }

                    @Override
                    public void messageReceive(String resp) {
                        System.out.println("成功");
                    }
                });

            }
        });
    }
}
