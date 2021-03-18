package com.example.regisuseong;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.List;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class StatisticsCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    public StatisticsCourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent; //자신을 불러낸 부모 Fragment를 담을 수 있게 함

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
        View v = View.inflate(context, R.layout.statistics, null); //statistics에 해당하는 디자인 사용할 수 있게 함
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseRate = (TextView) v.findViewById(R.id.courseRate);

        if(courseList.get(i).getCourseGrade().equals("0") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("All grade");
        }
        else
        {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "grade");
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());

        courseDivide.setText(courseList.get(i).getCourseDivide() + "divide");

        if(courseList.get(i).getCoursePersonnel()==0)
        {
            coursePersonnel.setText("No Limit");
            courseRate.setText("");
        }
        else
        {
            coursePersonnel.setText("Personnel: " +courseList.get(i).getCourseRival()+ "/" +courseList.get(i).getCoursePersonnel()+" "); //(현재 강의를 등록한 사람 수)/(강의 전체 인원)
            int rate = ((int) (((double) courseList.get(i).getCourseRival() * 100 / courseList.get(i).getCoursePersonnel()) +0.5)); //0.5 더해서 반올림
            courseRate.setText("Rate: "+rate+"%");

            if(rate < 20)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorSafe));
            }
            else if(rate <= 50)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
            }
            else if(rate <= 100)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorDanger));
            }
            else if(rate <= 150)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorWarning));
            }
            else
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorRed));
            }
        }

        v.setTag(courseList.get(i).getCourseID());

        //버튼 이벤트 추가
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StatisticsCourseListAdapter.this.parent.getActivity());
                builder.setMessage("Are you sure?");
                builder.setTitle("Delete")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {

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
                                                AlertDialog dialog=builder.setMessage("Course is deleted").setPositiveButton("Confirm", null).create();
                                                dialog.show();
                                                StatisticsFragment.totalCredit -= courseList.get(i).getCourseCredit(); //촐 학점에서 삭제하려는 강의 학점값을 빼고
                                                StatisticsFragment.credit.setText(StatisticsFragment.totalCredit + "credits"); //남은 총 학점을 보여줌
                                                courseList.remove(i); //강의 삭제
                                                notifyDataSetChanged(); //데이터 변화 알림
                                            }
                                            else
                                            {
                                                AlertDialog.Builder builder=new AlertDialog.Builder(parent.getActivity());
                                                AlertDialog dialog=builder.setMessage("Deleting course is failed").setNegativeButton("Try again", null).create();
                                                dialog.show();
                                            }
                                        } catch(Exception e) {e.printStackTrace();}
                                    }
                                };

                                //스케줄 데이터베이스 안에 유저아이디 넣음 //공백을 넣어서 문자열 형태로 만듬
                                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(i).getCourseID() + "", responseListener);
                                RequestQueue queue= newRequestQueue(parent.getActivity());//리퀘스트를 실질적으로 보낼 수 있는 queue 만들기
                                queue.add(deleteRequest);


                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });

        return v;
    }


}
