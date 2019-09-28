package com.example.uiapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;


public class TodayLuck extends AppCompatActivity {

    String _url = "https://www.geniecontents.com/";

    //get 요청알 parameter들
    LocalDate date;
    int targetyear ;
    int targetmonth;
    int targetday;
    int animal;


     //json 받아오는거 저장하는 전역변수욥
    String summary;
    String imgPath;
    String startDate;
    String pickedAnimal;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_today_luck);

            Intent intent = getIntent();

            animal = intent.getIntExtra("animal", 0);

            getToday();

            new JSONTask().execute("www.geniecontents.com");

    }

    //오늘날짜를 불러옵니당
    void getToday() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
            targetyear = date.getYear();
            targetmonth = date.getMonthValue();
            targetday = date.getDayOfMonth();
        }
    }


    //연결 요청하는 클래스
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String...urls) {
            try {
                JSONObject jsonObject = new JSONObject();

                HttpURLConnection con = null;
                BufferedReader reader = null;

                //url 뒤에 붙힐 파라미터를 준비합니당..
                StringBuffer stbParams = new StringBuffer();
                stbParams.append("fortune/internal/v1/daily?");
                stbParams.append("targetYear").append("=").append(targetyear).append("&");
                stbParams.append("targetMonth").append("=").append(targetmonth).append("&");
                stbParams.append("targetDay").append("=").append(targetday).append("&");
                stbParams.append("animal").append("=").append(animal);


                //연결시작
                try {

                    //연결합니다..
                    String strParams = stbParams.toString();
                    URL url = new URL(_url + strParams);

                    con = (HttpURLConnection) url.openConnection();



                    con.setRequestMethod("GET");
                    //con.setRequestProperty("Accept-Charset", "UTF-8");
                    con.setRequestProperty("Context_Type", "application/json");

                    Log.d("LOG : ", "응답메세지 :" + con.getResponseMessage());

                    //res
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null) {
                        con.disconnect();
                    }
                    try {
                        if(reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        //데이터 전송 및 데이터를 다 받고난뒤에 실행되는 함수
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //json 파싱한 결과를 저장
            String[][] luckLists = jsonParserList(result);

            //textview에 뛰웁니다.
            setTextView(luckLists);

        }

    }


    //받아온 데이터를 파싱합니다
    public String[][] jsonParserList(String pRecvServerPage) {
       // Log.i("server send :", pRecvServerPage);


        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            summary = json.getString("summary");
            imgPath = json.getString("animalImgUrl");
            startDate = json.getString("startTimePeriod");
            pickedAnimal = json.getString("animal");
            String list = json.getString("list");

            Log.i("server send :", summary);
            Log.i("server send :", imgPath);


            JSONArray jsonArr = new JSONArray(list);
            String[] jsonName = {"year", "description"};

            String[][] parseredData = new String[jsonArr.length()][jsonName.length];

            for (int i = 0; i < jsonArr.length(); i++) {

                json = jsonArr.getJSONObject(i);

                if(json != null) {
                    for(int j = 0; j < jsonName.length; j++) {
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
            }
            for(int i=0; i<parseredData.length; i++){

                Log.i("JSON을 분석한 데이터 "+i+" : ", parseredData[i][0]);
                Log.i("JSON을 분석한 데이터 "+i+" : ", parseredData[i][1]);
            }


            return parseredData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //데이터들을 textview로 설정
    void setTextView(String[][] result) {
        TextView todayLuck = (TextView) findViewById(R.id.todayLuck);

        //날짜 &&띠 오늘의 운세
        String animalVal = findAnimal(pickedAnimal);
        StringBuffer today = new StringBuffer();
        today.append(startDate).append(" ").append(animalVal).append("띠 오늘의 운세");
        todayLuck.append(today.toString());

        //Summary
        TextView summaryText = (TextView) findViewById(R.id.summary);
        summaryText.append(summary);

        TextView year0 = (TextView) findViewById(R.id.year0);
        TextView year0text = (TextView) findViewById(R.id.year0text);
        year0.append(result[0][0] + " : ");
        year0text.append(result[0][1]);

        TextView year1 = (TextView) findViewById(R.id.year1);
        TextView year1text = (TextView) findViewById(R.id.year1text);
        year1.append(result[1][0] + " : ");
        year1text.append(result[1][1]);

        TextView year2 = (TextView) findViewById(R.id.year2);
        TextView year2text = (TextView) findViewById(R.id.year2text);
        year2.append(result[2][0] + " : ");
        year2text.append(result[2][1]);

        TextView year3 = (TextView) findViewById(R.id.year3);
        TextView year3text = (TextView) findViewById(R.id.year3text);
        year3.append(result[3][0] + " : ");
        year3text.append(result[3][1]);

        TextView year4 = (TextView) findViewById(R.id.year4);
        TextView year4text = (TextView) findViewById(R.id.year4text);
        year4.append(result[4][0] + " : ");
        year4text.append(result[4][1]);

    }

    //동물..이름..리..턴..
    String findAnimal(String animal) {
        String value;

        switch(animal) {
            case "1" : value =  "쥐"; break;
            case "2" : value =  "소"; break;
            case "3" : value =  "호랑이"; break;
            case "4" : value =  "토끼"; break;
            case "5" : value =  "용"; break;
            case "6" : value =  "뱀"; break;
            case "7" : value =  "말"; break;
            case "8" : value =  "양"; break;
            case "9" : value =  "원숭이"; break;
            case "10" : value =  "닭"; break;
            case "11" : value =  "개"; break;
            case "12" : value =  "돼지"; break;

            default: value =  null;
        }
        return value;
    }


}
