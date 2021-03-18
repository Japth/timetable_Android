package com.example.regisuseong;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList; //courseID가 중복되는지 검사하기 위해서 courseID가 들어가는 리스트
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent; //자신을 불러낸 부모 Fragment를 담을 수 있게 함
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        new BackGroundTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null); //course에 해당하는 디자인 사용할 수 있게 함
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        //TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);

        if(courseList.get(i).getCourseGrade().equals("0") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("All grade");
        }
        else
        {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "grade");
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "credit");
        courseDivide.setText(courseList.get(i).getCourseDivide() + "divide");

        if(courseList.get(i).getCoursePersonnel()==0)
        {
            coursePersonnel.setText("No Limit");
        }
        else
        {
            coursePersonnel.setText("Limit : " +courseList.get(i).getCoursePersonnel());
        }

        if(courseList.get(i).getCourseProfessor().equals(""))
        {
            courseProfessor.setText("Personal Study");
        }
        else
        {
            courseProfessor.setText("Professor "+courseList.get(i).getCourseProfessor());
        }

        courseTime.setText(courseList.get(i).getCourseTime()+"");
        //courseRoom.setText(courseList.get(i).getCourseRoom() + "room");

        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validate = false; //현재 강의를 추가할 수 있는지 검사
                validate=schedule.validate(courseList.get(i).getCourseTime());

                if(!alreadyIn(courseIDList, courseList.get(i).getCourseID())) //신청했던 강의 아이디 중에 신청하려는 강의 아이디가 포함되어 있다면
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("This course is already added")
                            .setPositiveButton("Try again", null).create();
                    dialog.show();
                }

                else if(totalCredit + courseList.get(i).getCourseCredit() > 24) //현재 총 학점과 추가하려는 강의의 학점 합이 24 초과이면 추가할 수 없음
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("Total credits cannot exceed 24 credits")
                            .setPositiveButton("Try again", null).create();
                    dialog.show();
                }

                else if(validate==false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("Time table is duplicate")
                            .setPositiveButton("Try again", null).create();
                    dialog.show();
                }
                else
                {
                    Response.Listener<String> responseListener= new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse=new JSONObject(response); //특정한 응답을 받을 수 있게 해줌
                                boolean success=jsonResponse.getBoolean("success");//success 변수는 해당 과정이 정상적으로 됐는지 리스폰스 값 의미
                                if(success)
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(parent.getActivity()); //자신을 불러낸 CourseFragment에서 알림창을 띄움
                                    AlertDialog dialog=builder.setMessage("Course is added").setPositiveButton("Confirm", null).create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(i).getCourseID()); //강의가 추가 될 때마다 해당 강의를 직접 추가시켜줌
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                    totalCredit += courseList.get(i).getCourseCredit(); //현재 추가한 강의의 학점만큼 총 학점에 추가함
                                }
                                else
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog=builder.setMessage("Adding course is failed").setNegativeButton("Confirm", null).create();
                                    dialog.show();
                                }
                            } catch(Exception e) {e.printStackTrace();}
                        }
                    };

                    //스케줄 데이터베이스 안에 유저아이디 넣음 //공백을 넣어서 문자열 형태로 만듬
                    AddRequest addRequest = new AddRequest(userID, courseList.get(i).getCourseID() + "", responseListener);
                    RequestQueue queue= newRequestQueue(parent.getActivity());//리퀘스트를 실질적으로 보낼 수 있는 queue 만들기
                    queue.add(addRequest);
                }

            }
        });

        return v;
    }

    class BackGroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url=new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 서버에 접속할 수 있도록 URL 연결
                InputStream inputStream = httpURLConnection.getInputStream(); //넘어오는 값들을 저장함
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //해당 인풋스트림의 내용을 버퍼에 담아서 읽을 수 있게 함
                String temp; //temp에 하나씩 읽어와서 문자영 형태로 저장
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine()) != null)// null이 아닐때까지 버퍼에서 받아온 값을 한 줄씩 읽으면서 temp에 넣음
                {
                    stringBuilder.append(temp + "\n"); //temp에 한 줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim(); //들어간 문자열 반환

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) //해당 결과 처리 함수 //결과값을 result로 받아옴
        {
            try
            {
                JSONObject jsonObject = new JSONObject(result);//해당 결과의 응답 부분 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //response에 각각의 공지사항 리스트가 담기게 됨
                int count = 0;
                String courseProfessor;
                String courseTime;
                int courseID;
                totalCredit = 0;
                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //courseID값 가져옴
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    totalCredit += object.getInt("courseCredit");

                    courseIDList.add(courseID); //해당 사용자가 가지고 있는 모든 강의 아이디가 courseIDList에 담김
                    schedule.addSchedule(courseTime);

                    //adapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌
                    count++;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean alreadyIn(List<Integer> courseIDList, int item) //특정 courseID에 해당하는 강의가 이미 들어가 있는 상태라면 체크함
    {
        for(int i=0; i < courseIDList.size(); i++) //모든 강의리스트의 아이디를 돌면서
        {
            if(courseIDList.get(i)==item) //현재 추가하려는 강의 아이디 값과 일치하는 원소가 하나라도 있으면
            {
                return false;
            }
        }
        return true;
    }
}
