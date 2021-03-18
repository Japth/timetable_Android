package com.example.regisuseong;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ListView courseListView;
    private StatisticsCourseListAdapter adapter;
    private List<Course> courseList;
    //int totalCredit=0; //해당 학생이 신청한 총 학점

    public static int totalCredit=0; //학점 자체가 공통적으로 사용됨
    public static TextView credit;

    private ArrayAdapter rankAdapter;
    private Spinner rankSpinner;

    private ListView rankListView;
    private RankListAdapter rankListAdapter;
    private List<Course> rankList;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(Bundle b) //해당 엑티비티를 불러왔을때 실행되는 부분
    {
        super.onActivityCreated(b);
        courseListView = (ListView) getView().findViewById(R.id.courseListView); //변수들 초기화
        courseList = new ArrayList<Course>();
        adapter = new StatisticsCourseListAdapter(getContext().getApplicationContext(), courseList, this);
        courseListView.setAdapter(adapter);
        new BackgroundTask().execute(); //데이터베이스와 소통되는 부분

        totalCredit=0;
        credit = (TextView) getView().findViewById(R.id.totalCredit);

        rankSpinner = (Spinner) getView().findViewById(R.id.rankSpinner);
        rankAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.rank, R.layout.spinner_item);
        rankSpinner.setAdapter(rankAdapter);
        rankSpinner.setPopupBackgroundResource(R.color.colorPrimary); //해당 스피너를 선택했을때 나오는 목록의 배경색 지정
        rankListView = (ListView) getView().findViewById(R.id.rankListView);
        rankList = new ArrayList<Course>();
        rankListAdapter = new RankListAdapter(getContext().getApplicationContext(), rankList, this); //생성자 조회
        rankListView.setAdapter(rankListAdapter);
        //new ByEntire().execute(); //전체에서 강의 순위를 불러오는 부분
        rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(rankSpinner.getSelectedItem().equals("In Total"))
                {
                    rankList.clear();
                    new ByEntire().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("In My Major"))
                {
                    rankList.clear();
                    new ByMyMajor().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("Female Popularity"))
                {
                    rankList.clear();
                    new ByFemale().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("Male Popularity"))
                {
                    rankList.clear();
                    new ByMale().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("Programming Popularity"))
                {
                    rankList.clear();
                    new ByMajor().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("Design Popularity"))
                {
                    rankList.clear();
                    new ByRefinement().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    class ByMyMajor extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByMyMajor.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class ByRefinement extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByRefinement.php";

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class ByMajor extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByMajor.php";

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class ByFemale extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByFemale.php";

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class ByMale extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByMale.php";

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class ByEntire extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ByEntire.php";

            } catch (Exception e) {
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
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;
                String courseTime;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");

                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide,  coursePersonnel, courseTime, courseProfessor));

                    count++;
                }
                rankListAdapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/StatisticsCourseList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");

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
                int courseID;
                String courseGrade;
                String courseTitle;
                int courseDivide;
                int coursePersonnel;
                int courseRival;

                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseRival = object.getInt("COUNT(SCHEDULE.courseID)");
                    int courseCredit =object.getInt("courseCredit");
                    totalCredit += courseCredit;
                    courseList.add(new Course(courseID, courseTitle, courseDivide, courseGrade, coursePersonnel, courseRival, courseCredit));

                    count++;
                }
                adapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌
                credit.setText(totalCredit+"credits");

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
