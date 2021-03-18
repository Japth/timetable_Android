package com.example.regisuseong;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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

    private AutoResizeTextView monday[] = new AutoResizeTextView[14];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[14];
    private AutoResizeTextView friday[] = new AutoResizeTextView[14];
    private AutoResizeTextView saturday[] = new AutoResizeTextView[14];
    private Schedule schedule = new Schedule();

    @Override
    public void onActivityCreated(Bundle b) //해당 프레그먼트가 생설될 때 실행
    {
        super.onActivityCreated(b);

        monday[0]=(AutoResizeTextView) getView().findViewById(R.id.monday0);//각각의 텍스트뷰 초기화
        monday[1]=(AutoResizeTextView) getView().findViewById(R.id.monday1);
        monday[2]=(AutoResizeTextView) getView().findViewById(R.id.monday2);
        monday[3]=(AutoResizeTextView) getView().findViewById(R.id.monday3);
        monday[4]=(AutoResizeTextView) getView().findViewById(R.id.monday4);
        monday[5]=(AutoResizeTextView) getView().findViewById(R.id.monday5);
        monday[6]=(AutoResizeTextView) getView().findViewById(R.id.monday6);
        monday[7]=(AutoResizeTextView) getView().findViewById(R.id.monday7);
        monday[8]=(AutoResizeTextView) getView().findViewById(R.id.monday8);
        monday[9]=(AutoResizeTextView) getView().findViewById(R.id.monday9);
        monday[10]=(AutoResizeTextView) getView().findViewById(R.id.monday10);
        monday[11]=(AutoResizeTextView) getView().findViewById(R.id.monday11);
        monday[12]=(AutoResizeTextView) getView().findViewById(R.id.monday12);
        monday[13]=(AutoResizeTextView) getView().findViewById(R.id.monday13);

        tuesday[0]=(AutoResizeTextView) getView().findViewById(R.id.tuesday0);//각각의 텍스트뷰 초기화
        tuesday[1]=(AutoResizeTextView) getView().findViewById(R.id.tuesday1);
        tuesday[2]=(AutoResizeTextView) getView().findViewById(R.id.tuesday2);
        tuesday[3]=(AutoResizeTextView) getView().findViewById(R.id.tuesday3);
        tuesday[4]=(AutoResizeTextView) getView().findViewById(R.id.tuesday4);
        tuesday[5]=(AutoResizeTextView) getView().findViewById(R.id.tuesday5);
        tuesday[6]=(AutoResizeTextView) getView().findViewById(R.id.tuesday6);
        tuesday[7]=(AutoResizeTextView) getView().findViewById(R.id.tuesday7);
        tuesday[8]=(AutoResizeTextView) getView().findViewById(R.id.tuesday8);
        tuesday[9]=(AutoResizeTextView) getView().findViewById(R.id.tuesday9);
        tuesday[10]=(AutoResizeTextView) getView().findViewById(R.id.tuesday10);
        tuesday[11]=(AutoResizeTextView) getView().findViewById(R.id.tuesday11);
        tuesday[12]=(AutoResizeTextView) getView().findViewById(R.id.tuesday12);
        tuesday[13]=(AutoResizeTextView) getView().findViewById(R.id.tuesday13);

        wednesday[0]=(AutoResizeTextView) getView().findViewById(R.id.wednesday0);//각각의 텍스트뷰 초기화
        wednesday[1]=(AutoResizeTextView) getView().findViewById(R.id.wednesday1);
        wednesday[2]=(AutoResizeTextView) getView().findViewById(R.id.wednesday2);
        wednesday[3]=(AutoResizeTextView) getView().findViewById(R.id.wednesday3);
        wednesday[4]=(AutoResizeTextView) getView().findViewById(R.id.wednesday4);
        wednesday[5]=(AutoResizeTextView) getView().findViewById(R.id.wednesday5);
        wednesday[6]=(AutoResizeTextView) getView().findViewById(R.id.wednesday6);
        wednesday[7]=(AutoResizeTextView) getView().findViewById(R.id.wednesday7);
        wednesday[8]=(AutoResizeTextView) getView().findViewById(R.id.wednesday8);
        wednesday[9]=(AutoResizeTextView) getView().findViewById(R.id.wednesday9);
        wednesday[10]=(AutoResizeTextView) getView().findViewById(R.id.wednesday10);
        wednesday[11]=(AutoResizeTextView) getView().findViewById(R.id.wednesday11);
        wednesday[12]=(AutoResizeTextView) getView().findViewById(R.id.wednesday12);
        wednesday[13]=(AutoResizeTextView) getView().findViewById(R.id.wednesday13);

        thursday[0]=(AutoResizeTextView) getView().findViewById(R.id.thursday0);//각각의 텍스트뷰 초기화
        thursday[1]=(AutoResizeTextView) getView().findViewById(R.id.thursday1);
        thursday[2]=(AutoResizeTextView) getView().findViewById(R.id.thursday2);
        thursday[3]=(AutoResizeTextView) getView().findViewById(R.id.thursday3);
        thursday[4]=(AutoResizeTextView) getView().findViewById(R.id.thursday4);
        thursday[5]=(AutoResizeTextView) getView().findViewById(R.id.thursday5);
        thursday[6]=(AutoResizeTextView) getView().findViewById(R.id.thursday6);
        thursday[7]=(AutoResizeTextView) getView().findViewById(R.id.thursday7);
        thursday[8]=(AutoResizeTextView) getView().findViewById(R.id.thursday8);
        thursday[9]=(AutoResizeTextView) getView().findViewById(R.id.thursday9);
        thursday[10]=(AutoResizeTextView) getView().findViewById(R.id.thursday10);
        thursday[11]=(AutoResizeTextView) getView().findViewById(R.id.thursday11);
        thursday[12]=(AutoResizeTextView) getView().findViewById(R.id.thursday12);
        thursday[13]=(AutoResizeTextView) getView().findViewById(R.id.thursday13);

        friday[0]=(AutoResizeTextView) getView().findViewById(R.id.friday0);//각각의 텍스트뷰 초기화
        friday[1]=(AutoResizeTextView) getView().findViewById(R.id.friday1);
        friday[2]=(AutoResizeTextView) getView().findViewById(R.id.friday2);
        friday[3]=(AutoResizeTextView) getView().findViewById(R.id.friday3);
        friday[4]=(AutoResizeTextView) getView().findViewById(R.id.friday4);
        friday[5]=(AutoResizeTextView) getView().findViewById(R.id.friday5);
        friday[6]=(AutoResizeTextView) getView().findViewById(R.id.friday6);
        friday[7]=(AutoResizeTextView) getView().findViewById(R.id.friday7);
        friday[8]=(AutoResizeTextView) getView().findViewById(R.id.friday8);
        friday[9]=(AutoResizeTextView) getView().findViewById(R.id.friday9);
        friday[10]=(AutoResizeTextView) getView().findViewById(R.id.friday10);
        friday[11]=(AutoResizeTextView) getView().findViewById(R.id.friday11);
        friday[12]=(AutoResizeTextView) getView().findViewById(R.id.friday12);
        friday[13]=(AutoResizeTextView) getView().findViewById(R.id.friday13);

        saturday[0]=(AutoResizeTextView) getView().findViewById(R.id.saturday0);//각각의 텍스트뷰 초기화
        saturday[1]=(AutoResizeTextView) getView().findViewById(R.id.saturday1);
        saturday[2]=(AutoResizeTextView) getView().findViewById(R.id.saturday2);
        saturday[3]=(AutoResizeTextView) getView().findViewById(R.id.saturday3);
        saturday[4]=(AutoResizeTextView) getView().findViewById(R.id.saturday4);
        saturday[5]=(AutoResizeTextView) getView().findViewById(R.id.saturday5);
        saturday[6]=(AutoResizeTextView) getView().findViewById(R.id.saturday6);
        saturday[7]=(AutoResizeTextView) getView().findViewById(R.id.saturday7);
        saturday[8]=(AutoResizeTextView) getView().findViewById(R.id.saturday8);
        saturday[9]=(AutoResizeTextView) getView().findViewById(R.id.saturday9);
        saturday[10]=(AutoResizeTextView) getView().findViewById(R.id.saturday10);
        saturday[11]=(AutoResizeTextView) getView().findViewById(R.id.saturday11);
        saturday[12]=(AutoResizeTextView) getView().findViewById(R.id.saturday12);
        saturday[13]=(AutoResizeTextView) getView().findViewById(R.id.saturday13);

//        for(int i=0; i<14; i++)
//        {
//            friday[i]=(AutoResizeTextView) getView().findViewById(R.id.friday0+i); //뷰 아이디와 다르게 매칭되는 오류 발생
//        }

        new BackGroundTask().execute();
    }

    class BackGroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            try {
                target="http://jandpth.dothome.co.kr/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");

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
                String courseTitle;
                int courseID;
                while(count < jsonArray.length()) //count가 jsonArray의 전체 크기보다 작을때까지
                {
                    JSONObject object = jsonArray.getJSONObject(count);//배열의 원소 값 저장
                    courseID = object.getInt("courseID"); //noticeContent값 가져옴
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");
                    schedule.addSchedule(courseTime, courseTitle, courseProfessor);

                    //adapter.notifyDataSetChanged(); //어뎁터에 데이터 변화가 있다고 알려줌
                    count++;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            schedule.setting(monday, tuesday, wednesday, thursday, friday, saturday, getContext()); //텍스트뷰에 출력
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
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
