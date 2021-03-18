package com.example.regisuseong;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//해당 php파일에 유저 정보를 보내서 회원가입 요청을 보내는 클래스
public class RegisterRequest extends StringRequest {

    final static private String URL = "http://jandpth.dothome.co.kr/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null); //해당 URL에 parameter들을 POST방식으로 숨겨서 보내라
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
