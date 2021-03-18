package com.example.regisuseong;

import android.content.Context;

public class Schedule {

    private String monday[] = new String[14];
    private String tuesday[] = new String[14];
    private String wednesday[] = new String[14];
    private String thursday[] = new String[14];
    private String friday[] = new String[14];
    private String saturday[] = new String[14];

    //생성자
    public Schedule()
    {
        for(int i=0;i<14;i++)
        {
            monday[i]=""; //강의 정보로 공백이 들어가게 함
            tuesday[i]="";
            wednesday[i]="";
            thursday[i]="";
            friday[i]="";
            saturday[i]="";
        }
    }

    public void addSchedule(String scheduleText) //스케줄 정보를 담는 특정 텍스트가 있을때 데이터를 파싱해서 강의 정보가 들어가는 배열에 넣음
    {
        int temp;
        //temp안에 Mon이라는 단어가 포함되어 있을때 스케줄텍스트에 Mon이라는 단어가 포함되어 있는 위치를 반환하고 temp에 값이 들어감
        //Mon:[3][4][5] //Mon이라는 데이터를 기준으로 파싱해서 [3][4][5]라는 교시 데이터에 강의 정보가 들어가게 함
        //Mon:[3][4][5]Tue[1][2] //Mon기준으로 [3][4][5]를 파싱하고 Tue 기준으로 [1][2] 파싱
        if((temp=scheduleText.indexOf("Mon")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                    //현재 데이터의 교시에 해당하는 숫자 데이터를 배열에 넣음
                    //괄호 사이에 있는 숫자를 파싱해서 monday의 해당 교시에 Course라는 데이터를 넣음
                }
            }
        }

        if((temp=scheduleText.indexOf("Tue")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                }
            }
        }

        if((temp=scheduleText.indexOf("Wed")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                }
            }
        }

        if((temp=scheduleText.indexOf("Thu")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                }
            }
        }

        if((temp=scheduleText.indexOf("Fri")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                }
            }
        }

        if((temp=scheduleText.indexOf("Sat")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    saturday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "class"; //(수업)
                }
            }
        }
    }

    //새로 추가하려는 수강 신청 날짜 데이터가 현재 자리잡고 있는 스케줄 데이터와 중복되지 않는지
    public boolean validate(String scheduleText)
    {
        if(scheduleText.equals(""))
        {
            return true;
        }

        int temp;

        if((temp=scheduleText.indexOf("Mon")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    if(!(monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))) //공백이 아니라면
                    {
                        return false;
                    }
                }
            }
        }

        if((temp=scheduleText.indexOf("Tue")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    if(!tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) //공백이 아니라면
                    {
                        return false;
                    }
                }
            }
        }

        if((temp=scheduleText.indexOf("Wed")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    if(!wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) //공백이 아니라면
                    {
                        return false;
                    }
                }
            }
        }

        if((temp=scheduleText.indexOf("Thu")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    if(!thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) //공백이 아니라면
                    {
                        return false;
                    }
                }
            }
        }

        if((temp=scheduleText.indexOf("Fri")) > -1)
    {
        temp += 4;
        int startPoint=temp;
        int endPoint=temp;
        for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
        {
            if(scheduleText.charAt(i)=='[')
            {
                startPoint=i;
            }
            if(scheduleText.charAt(i)==']')
            {
                endPoint=i;
                if(!friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) //공백이 아니라면
                {
                    return false;
                }
            }
        }
    }

        if((temp=scheduleText.indexOf("Sat")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    if(!saturday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) //공백이 아니라면
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }



    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor) //스케줄 정보를 담는 특정 텍스트가 있을때 데이터를 파싱해서 강의 정보가 들어가는 배열에 넣음
    {
        String professor;
        if(courseProfessor.equals(""))
        {
            professor="";
        }
        else
        {
            //professor="("+courseProfessor+")";
            professor=""; //교수님 이름은 공백으로 처리
        }

        int temp;
        //temp안에 Mon이라는 단어가 포함되어 있을때 스케줄텍스트에 Mon이라는 단어가 포함되어 있는 위치를 반환하고 temp에 값이 들어감
        //Mon:[3][4][5] //Mon이라는 데이터를 기준으로 파싱해서 [3][4][5]라는 교시 데이터에 강의 정보가 들어가게 함
        //Mon:[3][4][5]Tue[1][2] //Mon기준으로 [3][4][5]를 파싱하고 Tue 기준으로 [1][2] 파싱
        if((temp=scheduleText.indexOf("Mon")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                    //현재 데이터의 교시에 해당하는 숫자 데이터를 배열에 넣음
                    //해당 배열 안에 강의 제목과 교수 이름이 들어감
                }
            }
        }

        if((temp=scheduleText.indexOf("Tue")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                }
            }
        }

        if((temp=scheduleText.indexOf("Wed")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                }
            }
        }

        if((temp=scheduleText.indexOf("Thu")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                }
            }
        }

        if((temp=scheduleText.indexOf("Fri")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                }
            }
        }

        if((temp=scheduleText.indexOf("Sat")) > -1)
        {
            temp += 4;
            int startPoint=temp;
            int endPoint=temp;
            for(int i=temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i)=='[')
                {
                    startPoint=i;
                }
                if(scheduleText.charAt(i)==']')
                {
                    endPoint=i;
                    saturday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle+professor;
                }
            }
        }
    }

    //TextView에 해당 강의 목록들을 보여줌
    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, AutoResizeTextView[] saturday, Context context)
    {
        //모든 텍스트뷰에 들어가는 내용 중 가장 긴 텍스트를 골라서 넣어주기
        int maxLength=0;
        String maxString="";
        for(int i=0;i<14;i++)
        {
            if(this.monday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.monday[i].length();
                maxString=this.monday[i]; //현재 가장 긴 문자열 대입
            }

            if(this.tuesday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.tuesday[i].length();
                maxString=this.tuesday[i]; //현재 가장 긴 문자열 대입
            }

            if(this.wednesday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.wednesday[i].length();
                maxString=this.wednesday[i]; //현재 가장 긴 문자열 대입
            }

            if(this.thursday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.thursday[i].length();
                maxString=this.thursday[i]; //현재 가장 긴 문자열 대입
            }

            if(this.friday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.friday[i].length();
                maxString=this.friday[i]; //현재 가장 긴 문자열 대입
            }

            if(this.saturday[i].length() > maxLength) //현재 텍스트 길이가 maxLength보다 길다면
            {
                maxLength=this.saturday[i].length();
                maxString=this.saturday[i]; //현재 가장 긴 문자열 대입
            }
        }

        for(int i=0; i<14; i++)
        {
            if(!this.monday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                monday[i].setText(this.monday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                monday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                monday[i].setText(maxString); //모든 문자열 중에서 가장 긴 것을 선택
            }

            if(!this.tuesday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                tuesday[i].setText(this.tuesday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                tuesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                tuesday[i].setText(maxString);
            }

            if(!this.wednesday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                wednesday[i].setText(this.wednesday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //wednesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                wednesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                wednesday[i].setText(maxString);
            }

            if(!this.thursday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                thursday[i].setText(this.thursday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                thursday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                thursday[i].setText(maxString);
            }

            if(!this.friday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                friday[i].setText(this.friday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                friday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                friday[i].setText(maxString);
            }

            if(!this.saturday[i].equals("")) //특정 강의가 해당 시간에 이미 들어가 있다면
            {
                saturday[i].setText(this.saturday[i]); //해당 배열의 내용이 그 TextView 값으로 들어감 //현재 강의를 뷰에 출력
                //saturday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                saturday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else
            {
                saturday[i].setText(maxString);
            }

//            monday[i].resizeText();
//            tuesday[i].resizeText();
//            wednesday[i].resizeText();
//            thursday[i].resizeText();
//            friday[i].resizeText();
        }
    }

}
