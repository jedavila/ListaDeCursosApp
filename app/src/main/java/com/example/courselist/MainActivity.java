package com.example.courselist;
/*Juan Esteban Davila 206604
* Martin Farfan 207250
* */


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private adapterCourse adapterCourse;
    private Modelo mapaCursos = Modelo.getInstance();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private long backTime;
    private int limitador = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.idRcView);

        HttpGetTask task = new HttpGetTask();
        task.execute();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCourse = new adapterCourse(this, recyclerView);
        recyclerView.setAdapter(adapterCourse);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterCourse = new adapterCourse(this, recyclerView);
        recyclerView.setAdapter(adapterCourse);
    }

    @Override
    public void onBackPressed() {
        if(backTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
        }else{
            onResume();
            Toast.makeText(getBaseContext(), "Presionar otra vez para Salir", Toast.LENGTH_SHORT).show();
        }
        backTime = System.currentTimeMillis();
    }

    private class HttpGetTask extends AsyncTask<Void, Void, Void> {

        private List<section> section = new ArrayList<>();
        private int aux = 0;


        private static final String URL = "http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/uwm.xml";
        private static final String TAG = "Error de HTTP Connection";
        public static final String COURSE_TAG = "course_listing";
        public static final String SECTION_TAG = "section_listing";

        //COURSE TAGS
        public static final String COURS_TAG = "course";
        public static final String TITLE_TAG = "title";
        public static final String CREDITS_TAG = "credits";
        public static final String LEVEL_TAG = "level";
        public static final String RESTRICTIONS_TAG = "restrictions";

        //SECTIONS TAG
        public static final String SEC_TAG = "section";
        public static final String DAYS_TAG = "days";
        public static final String START_TAG = "start";
        public static final String BLDG_TAG = "bldg";
        public static final String RM_TAG = "rm";
        public static final String INSTRUCTOR_TAG = "instructor";

        //booleans y strings
        private String sCourse,sTitle,sCredits,sLevel,sRestrictions,sSec,sDays,sStart,sBldg,sRm,sInstructor;
        private boolean bCourse,bTitle,bCredits,bLevel,bRestrictions,bSec,bDays,bStart,bBldg,bRm,bInstructor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }


        @Override
        protected Void doInBackground(Void... voids) {
            String data = "";
            HttpURLConnection httpUrlConnection = null;

            try {
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                InputStream in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());

                data=readStream(in);

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection) {
                    // 5. Disconnect
                    httpUrlConnection.disconnect();
                }
            }

            parseXmlString(data);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            adapterCourse.notifyDataSetChanged();

        }


        //Method's Del Parse

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException");
                    }
                }
            }
            return data.toString();
        }

        private void parseXmlString(String data) {
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(data));

                int eventType = xpp.getEventType();

                while (mapaCursos.getCursosMap().size()!=20) {
                        if (eventType == XmlPullParser.START_TAG) {
                            startTag(xpp.getName());
                        } else if (eventType == XmlPullParser.END_TAG) {
                            endTag(xpp.getName());
                        } else if (eventType == XmlPullParser.TEXT) {
                            text(xpp.getText());
                        }
                    eventType = xpp.next();
                }
                Log.d(TAG,  mapaCursos.getCursosMap().toString());

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }


        }

        private void startTag(String localName) {
            switch (localName) {
                case COURS_TAG:
                    bCourse = true;
                    break;
                case TITLE_TAG:
                    bTitle = true;
                    break;
                case CREDITS_TAG:
                    bCredits = true;
                    break;
                case LEVEL_TAG:
                    bLevel= true;
                    break;
                case RESTRICTIONS_TAG:
                    bRestrictions = true;
                    break;
                case SEC_TAG:
                    bSec = true;
                    break;
                case DAYS_TAG:
                    bDays = true;
                    break;
                case START_TAG:
                    bStart = true;
                    break;
                case BLDG_TAG:
                    bBldg = true;
                    break;
                case RM_TAG:
                    bRm = true;
                    break;
                case INSTRUCTOR_TAG:
                    bInstructor = true;
                    break;
            }
        }

        private void text(String text) {
            if (bCourse) {
                sCourse = text.trim();
            } else if (bTitle) {
                sTitle = text.trim();
            } else if (bCredits) {
                sCredits = text.trim();
            }else if (bLevel) {
                sLevel = text.trim();
            }else if (bRestrictions) {
                sRestrictions = text.trim();
            }else if (bSec) {
                sSec = text.trim();
            }else if (bDays) {
                sDays = text.trim();
            }else if (bStart) {
                sStart = text.trim();
            }else if (bBldg) {
                sBldg = text.trim();
            }else if (bRm) {
                sRm = text.trim();
            }else if (bInstructor) {
                sInstructor = text.trim();
            }
        }

        private void endTag(String localName) {
            switch (localName) {
                case COURS_TAG:
                    bCourse = false;
                    break;
                case TITLE_TAG:
                    bTitle = false;
                    break;
                case CREDITS_TAG:
                    bCredits = false;
                    break;
                case LEVEL_TAG:
                    bLevel= false;
                    break;
                case RESTRICTIONS_TAG:
                    bRestrictions = false;
                    break;
                case SEC_TAG:
                    bSec = false;
                    break;
                case DAYS_TAG:
                    bDays = false;
                    break;
                case START_TAG:
                    bStart = false;
                    break;
                case BLDG_TAG:
                    bBldg = false;
                    break;
                case RM_TAG:
                    bRm = false;
                    break;
                case INSTRUCTOR_TAG:
                    bInstructor = false;
                    break;
                case SECTION_TAG:
                    if(aux>=limitador){
                        section.add(new section(sSec,sDays,sStart,sBldg,sRm,sInstructor));
                    }
                    sSec=null;
                    sDays=null;
                    sStart=null;
                    sBldg=null;
                    sRm=null;
                    sInstructor=null;
                    break;

                case COURSE_TAG:
                    if(aux>=limitador){
                        mapaCursos.addCursosMap(new Course(sCourse,sTitle,sCredits,sLevel,sRestrictions),new ArrayList<>(section));
                    }
                    aux++;
                    sCourse=null;
                    sTitle=null;
                    sCredits=null;
                    sLevel=null;
                    sRestrictions=null;

                    section.clear();

                    break;

            }
        }


    }
}