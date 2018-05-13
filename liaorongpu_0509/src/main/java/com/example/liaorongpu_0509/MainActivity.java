package com.example.liaorongpu_0509;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText etxt_hao;
    private EditText etxt_pwd;
    private Button btn_log;
    private Button btn_zc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        etxt_hao = findViewById(R.id.etxt_hao);
        etxt_pwd = findViewById(R.id.etxt_pwd);
        btn_log = findViewById(R.id.btn_log);
        btn_zc = findViewById(R.id.btn_zc);
        //添加点击事件
        btn_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转页面
                Intent intent = new Intent(MainActivity.this,MainActivity_ZhuCe.class);
                startActivity(intent);
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录成功跳转 获取输入框中的数据
                //获取输入框中的内容
                String hao = etxt_hao.getText().toString();
                String pwd = etxt_pwd.getText().toString();
                //请求数据
                requestDataLog(hao,pwd);
            }
        });
    }

    private void requestDataLog(String hao, String pwd) {
        new MyAsyn().execute("http://120.27.23.105/user/login?mobile="+hao+"&password="+pwd+"");
    }

    class MyAsyn extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                if(urlConnection.getResponseCode() == 200){
                    InputStream inputStream = urlConnection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b))!=-1){
                        outputStream.write(b,0,len);
                    }
                    return outputStream.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            MyGson_Log myGson_log = gson.fromJson(s, MyGson_Log.class);
            Toast.makeText(MainActivity.this, ""+myGson_log.getMsg(), Toast.LENGTH_SHORT).show();
            if(myGson_log.getMsg().equals("登录成功")){
                //跳转页面
                Intent intent = new Intent(MainActivity.this,MainActivity_List.class);
                startActivity(intent);
            }
        }

    }
}
