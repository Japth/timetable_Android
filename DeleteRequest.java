package com.example.regisuseong;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//해당 php파일에 유저 정보를 보내서 회원가입 요청을 보내는 클래스
public class DeleteRequest extends StringRequest {

    final static private String URL = "http://jandpth.dothome.co.kr/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, String courseID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null); //해당 URL에 parameter들을 POST방식으로 숨겨서 보내라
        parameters=new HashMap<>();
        parameters.put("userID", userID); //유저아이디와 코스아이디를 매개변수로 받아서 보내줌
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
