package com.example.regisuseong;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://jandpth.dothome.co.kr/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null); //해당 URL에 parameter들을 POST방식으로 숨겨서 보내라
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);

    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
