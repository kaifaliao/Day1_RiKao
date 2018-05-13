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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity_ZhuCe extends AppCompatActivity {

    private EditText etxt_hao;
    private EditText etxt_pwd;
    private Button zbtn_fan;
    private Button zbtn_zc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__zhu_ce);
        //获取资源id
        etxt_hao = findViewById(R.id.zetxt_hao);
        etxt_pwd = findViewById(R.id.zetxt_pwd);
        zbtn_fan = findViewById(R.id.zbtn_fan);
        zbtn_zc = findViewById(R.id.zbtn_zc);
        //添加点击事件
        zbtn_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //请求数据
                requestDataZhuCe();
            }
        });

        zbtn_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转页面
                Intent intent = new Intent(MainActivity_ZhuCe.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //请求数据
    private void requestDataZhuCe() {
        /*mobile手机号  必传参数
        password密码  必传参数*/
        //获取输入框中的内容
        String hao = etxt_hao.getText().toString();
        String pwd = etxt_pwd.getText().toString();
        new MyAsyn().execute("http://120.27.23.105/user/reg?mobile="+hao+"&password="+pwd+"");
    }
    class MyAsyn extends AsyncTask<String,Void,String>{

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
            MyGson_ZC myGson_zc = gson.fromJson(s, MyGson_ZC.class);
            Toast.makeText(MainActivity_ZhuCe.this, ""+myGson_zc.getMsg(), Toast.LENGTH_SHORT).show();
            if(myGson_zc.getMsg().equals("注册成功")){
                //跳转页面
                Intent intent = new Intent(MainActivity_ZhuCe.this,MainActivity.class);
                startActivity(intent);
            }
        }

    }
}
