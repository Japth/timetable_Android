package com.example.regisuseong;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID; //모든 클래스에서 접근 가능

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userID=getIntent().getStringExtra("userID"); //intent 값에서 넘어온 userID를 그대로 받음

        noticeListView=(ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();


        adapter=new NoticeListAdapter(getApplicationContext(), noticeList); //adapter에 해당 리스트 매칭
        noticeListView.setAdapter(adapter); //adapter에 들어간 내용들이 뷰 형태로 ListView에 들어가서 화면에 보여짐 //실행함수 부분으로 이동

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        courseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);  //해당 프레그먼트로 화면이 바뀔 수 있도록 notice부분이 보이지 않게 함
                courseButton.setSelected(true);
                scheduleButton.setSelected(false);
                statisticsButton.setSelected(false);
//              statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//              scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());//fragment 부분을 new CourseFragment()로 대체
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);  //해당 프레그먼트로 화면이 바뀔 수 있도록 notice부분이 보이지 않게 함
                courseButton.setSelected(false);
                scheduleButton.setSelected(true);
                statisticsButton.setSelected(false);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());//fragment 부분을 new ScheduleFragment()로 대체
                fragmentTransaction.commit();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);  //해당 프레그먼트로 화면이 바뀔 수 있도록 notice부분이 보이지 않게 함
                courseButton.setSelected(false);
                scheduleButton.setSelected(false);
                statisticsButton.setSelected(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());//fragment 부분을 new StatisticsFragment()로 대체
                fragmentTransaction.commit();
            }
        });

        new BackGroundTask().execute();

    }

    class BackGroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            target="http://jandpth.dothome.co.kr/NoticeList.php";
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
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    noticeContent = object.getString("noticeContent"); //noticeContent값 가져옴
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");

                    Notice notice = new Notice(noticeContent, noticeName, noticeDate); //하나의 공지사항에 대한 객체 생성
                    noticeList.add(notice);//리스트에 추가
                    //noticeListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌
                    count++;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private long lastTimeBackPressed; //뒤로가기 두 번 누르면 종료

    @Override
    public void onBackPressed()
    {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) //1.5초 이내로 뒤로가기 두 번 누르면
        {
            android.os.Process.killProcess(android.os.Process.myPid()); //앱을 종료하고 프로세스에 할당된 자원 모두 회수
        }

        Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
