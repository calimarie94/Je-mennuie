package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Virginie on 28/12/2014.
 */
public class TestDatabaseActivity extends Activity{

    private static final String DB_NAME = "Jemennuie_database.sqlite3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jemennuie);


        DataBaseHelper myDbHelper = DataBaseHelper.getInstance(this);

        System.out.println("Debut Database");
        myDbHelper.createDataBase();

        System.out.println("Database created ! ");

        try {
            // ouverture de la base de données
            myDbHelper.openDataBase();

            /*
            // test succès ouverture bdd
            String dbname = myDbHelper.getDatabaseName();
            System.out.println("Open Database" + dbname);

            // test question
            Question question = myDbHelper.getQuestionById(8);
            System.out.println("HEYYY YAAAA ");

            System.out.println(question.toString());
            Toast.makeText(this, "HEYYY YAAAA question : "+question.toString(), Toast.LENGTH_LONG).show();

             // test activité
            ActivityToDo activityToDo = myDbHelper.getActivityToDoById(1);
            System.out.println("HEYYY YAAAA ");

            System.out.println(activityToDo.toString());
            //Toast.makeText(this, "HEYYY YAAAA activité : "+activityToDo.toString(), Toast.LENGTH_LONG).show();

            */
            // test LinkedList ActivityToDo

            myDbHelper.fillActivitiesToDoFromDB();
            System.out.println("taille de activities "+myDbHelper.activities.size());

            /*for (int i = 0; i<myDbHelper.activities.size(); ++i){
                Toast.makeText(this, " Activité numéro "+ myDbHelper.activities.get(i).getIdActivity() +
                                " énoncé : "+myDbHelper.activities.get(i).getNameActivity()+
                                " favori : "+myDbHelper.activities.get(i).getFavorite()+
                                " découvert : "+myDbHelper.activities.get(i).getDiscovered(),
                        Toast.LENGTH_LONG).show();
            }*/


            // test ArrayList Question

            myDbHelper.fillQuestionsFromDB();
            System.out.println("taille de questions "+myDbHelper.questions.size());
            /*for (int i = 0; i<myDbHelper.questions.size(); ++i){
                Toast.makeText(this, " Question numéro "+ myDbHelper.questions.get(i).getId() +" énoncé : "+myDbHelper.questions.get(i).toString(), Toast.LENGTH_LONG).show();
            }*/

            // test Impact
            System.out.println("pouet pouet ");
            Answer testAnswer = myDbHelper.getImpactActivity(25,1);
            System.out.println(testAnswer.toString());
            Answer testAnswer2 = myDbHelper.getImpactActivity(17, 0);
            System.out.println(testAnswer2.toString());

            myDbHelper.fillDiscoveredActivitiesFromDB();
            System.out.println("taille de discovered "+myDbHelper.discoveredActivies.size());
            for (int i = 0; i<myDbHelper.discoveredActivies.size(); ++i){
                Toast.makeText(this, " activité découverte numéro "+ myDbHelper.discoveredActivies.get(i).getIdActivity() +" énoncé : "+myDbHelper.discoveredActivies.get(i).toString(), Toast.LENGTH_LONG).show();
            }



        }catch(SQLException sqle){
            System.out.println("Database not opened ! :( ");
            throw sqle;
        }

        // fermer la bdd
        myDbHelper.close();

    }


}
