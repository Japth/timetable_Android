package com.example.regisuseong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class); //현재 로그인엑티비티에서 레지스터엑티비티로 넘어감
                LoginActivity.this.startActivity(registerIntent); //인텐트 실행
            }
        });

        TextView information = (TextView) findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response); //리스폰스 받아옴
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("Login success").setPositiveButton("Confirm", null).create();
                                dialog.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class); //로그인 엑티비티에서 메인엑티비티로 넘어감
                                intent.putExtra("userID", userID);
                                LoginActivity.this.startActivity(intent); //intent 실행
                                finish(); //메인엑티비티로 이동하고 해당 엑티비티 닫음
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("Please check your account").setNegativeButton("Try again", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = newRequestQueue(LoginActivity.this);
                queue.add(loginRequest); //보내진 리퀘스트의 결과로 나온 리스폰스가 jsonResponse를 통해서 다루어짐
            }
        });


    }

    @Override
    protected void onStop() //현재 엑티비티가 종료되었다면
    {
        super.onStop();
        if(dialog != null) //현재 다이얼로그가 켜져 있을때는 함부로 종료되지 않게 함
        {
            dialog.dismiss();
            dialog=null;
        }
    }
}

