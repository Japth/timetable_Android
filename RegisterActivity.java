package com.example.regisuseong;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate=false; //사용할 수 있는 아이디인지 체크하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner=(Spinner)findViewById(R.id.majorSpinner);
        adapter=ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText idText=(EditText)findViewById(R.id.idText);
        final EditText passwordText=(EditText)findViewById(R.id.passwordText);
        final EditText emailText=(EditText)findViewById(R.id.emailText);

        RadioGroup genderGroup=(RadioGroup)findViewById(R.id.genderGroup);
        int genderGroupID=genderGroup.getCheckedRadioButtonId();
        userGender=((RadioButton)findViewById(genderGroupID)).getText().toString(); //현재 선택되어 있는 아이디 값

        //라디오버튼 클릭에 대한 이벤트 처리
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i); //현재 선택되어 있는 라디오 버튼 찾기
                userGender=genderButton.getText().toString();
            }
        });

        final Button validateButton=(Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String userID=idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("You must fill the ID").setPositiveButton("Confirm", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                            {
                            JSONObject jsonResponse=new JSONObject(response); //특정한 응답을 받을 수 있게 해줌
                            boolean success=jsonResponse.getBoolean("success");//success 변수는 해당 과정이 정상적으로 됐는지 리스폰스 값 의미
                            if(success)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("Usable ID").setPositiveButton("Confirm", null).create();
                                dialog.show();
                                idText.setEnabled(false); //id값을 더이상 바꿀 수 없도록 고정
                                validate=true; //중복체크 완료
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                //validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("Same ID already exists").setNegativeButton("Confirm", null).create();
                                dialog.show();
                            }
                        } catch(Exception e) {e.printStackTrace();}
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener); //실질적으로 접속할 수 있도록 객체 생성
                RequestQueue queue= newRequestQueue(RegisterActivity.this);//리퀘스트를 실질적으로 보낼 수 있는 queue 만들기
                queue.add(validateRequest);
            }
        });

        Button registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String userID=idText.getText().toString();
                String userPassword=passwordText.getText().toString();
                String userMajor=spinner.getSelectedItem().toString();
                String userEmail=emailText.getText().toString();

                if(!validate) //현재 중복체크가 되어있지 않다면 중복테크 하라는 메시지 출력
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("Please check validate first").setNegativeButton("Confirm", null).create();
                    dialog.show();
                    return;
                }

                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("Please fill the blank").setNegativeButton("Confirm", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse=new JSONObject(response); //특정한 응답을 받을 수 있게 해줌
                            boolean success=jsonResponse.getBoolean("success");//success 변수는 해당 과정이 정상적으로 됐는지 리스폰스 값 의미
                            if(success)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("Register sucees").setPositiveButton("Confirm", null).create();
                                dialog.show();
                                finish(); //회원가입 성공하면 회원가입 창 닫기
                            }
                            else
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("Register fail").setNegativeButton("Confirm", null).create();
                                dialog.show();
                            }
                        } catch(Exception e) {e.printStackTrace();}
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener); //회원가입 버튼 누르면 registerRequest 실행
                RequestQueue queue= newRequestQueue(RegisterActivity.this);//리퀘스트를 실질적으로 보낼 수 있는 queue 만들기
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected void onStop() //회원등록이 이루어진 이후 회원등록 창이 꺼지게 되면 실행되는 함수
    {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog=null;
        }
    }
}
