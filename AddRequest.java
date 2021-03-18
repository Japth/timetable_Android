package com.example.regisuseong;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//해당 php파일에 유저 정보를 보내서 회원가입 요청을 보내는 클래스
public class AddRequest extends StringRequest {

    final static private String URL = "http://jandpth.dothome.co.kr/CourseAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String userID, String courseID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null); //해당 URL에 parameter들을 POST방식으로 숨겨서 보내라
        parameters=new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
