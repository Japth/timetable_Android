package com.example.regisuseong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class RankListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;


    public RankListAdapter(Context context, List<Course> courseList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.rank, null); //rank에 해당하는 디자인 사용할 수 있게 함
        TextView rankTextView = (TextView) v.findViewById(R.id.rankTextView);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        rankTextView.setText((i + 1) + "rank");
        if(i != 0) //1위가 아니라면 colorPrimary 배경색을 가짐
        {
            rankTextView.setBackground(parent.getResources().getDrawable(R.drawable.rect_button_white));
            rankTextView.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
        }

        if(courseList.get(i).getCourseGrade().equals("0") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("All grade");
        }
        else
        {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "grade");
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "credits");
        courseDivide.setText(courseList.get(i).getCourseDivide() + "divide");

        if(courseList.get(i).getCoursePersonnel()==0)
        {
            coursePersonnel.setText("No Limit ");

        }
        else
        {
            coursePersonnel.setText("Limit:" +courseList.get(i).getCoursePersonnel()+" ");
        }

        if(courseList.get(i).getCourseProfessor().equals(""))
        {
            courseProfessor.setText("Personal study");
        }
        else
        {
            courseProfessor.setText(courseList.get(i).getCourseProfessor()+" Professor");
        }

        courseTime.setText(courseList.get(i).getCourseTime()+"");
        v.setTag(courseList.get(i).getCourseID());

        return v;
    }


}
