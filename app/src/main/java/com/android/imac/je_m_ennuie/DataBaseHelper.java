package com.android.imac.je_m_ennuie;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Virginie on 28/12/2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.android.imac.je_m_ennuie/databases/";

    private static String DB_NAME = "Jemennuie_database";

    public ArrayList<Question> questions;
    public LinkedList<ActivityToDo> activities;

    // private static String ASSETS_DB_FOLDER = "db";

    public SQLiteDatabase myDataBase;

    private final Context myContext;

        public SQLiteDatabase getDb() {
            return myDataBase;
        }

        public DataBaseHelper(Context context, String databaseName) {
            super(context, databaseName, null, 1);
            myContext = context;
            //Write a full path to the databases of your application
            String packageName = context.getPackageName();
            DB_PATH = String.format("//data//data//%s//databases//", packageName);
            DB_NAME = databaseName;
            questions = new ArrayList<Question>();
            activities = new LinkedList<ActivityToDo>();
            openDataBase();
        }

        //This piece of code will create a database if it’s not yet created
        public void createDataBase() {
            boolean dbExist = checkDataBase();
            if (!dbExist) {
                this.getReadableDatabase();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    Log.e(this.getClass().toString(), "Copying error");
                    throw new Error("Error copying database!");
                }
            } else {
                Log.i(this.getClass().toString(), "Database already exists");
            }
        }

        //Performing a database existence check
        private boolean checkDataBase() {
            SQLiteDatabase checkDb = null;
            try {
                String path = DB_PATH + DB_NAME;
                checkDb = SQLiteDatabase.openDatabase(path, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLException e) {
                Log.e(this.getClass().toString(), "Error while checking db");
            }
            //Android doesn’t like resource leaks, everything should
            // be closed
            if (checkDb != null) {
                checkDb.close();
            }
            return checkDb != null;
        }

        //Method for copying the database
        private void copyDataBase() throws IOException {
            //Open a stream for reading from our ready-made database
            //The stream source is located in the assets
            InputStream externalDbStream = myContext.getAssets().open(DB_NAME);

            //Path to the created empty database on your Android device
            String outFileName = DB_PATH + DB_NAME;

            //Now create a stream for writing the database byte by byte
            OutputStream localDbStream = new FileOutputStream(outFileName);

            //Copying the database
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = externalDbStream.read(buffer)) > 0) {
                localDbStream.write(buffer, 0, bytesRead);
            }
            //Don’t forget to close the streams
            localDbStream.close();
            externalDbStream.close();
        }

        public SQLiteDatabase openDataBase() throws SQLException {
            String path = DB_PATH + DB_NAME;
            if (myDataBase == null) {
                createDataBase();
                myDataBase = SQLiteDatabase.openDatabase(path, null,
                        SQLiteDatabase.OPEN_READWRITE);
            }
            return myDataBase;
        }


        /****************************** Questions *************************************/
        public Question getQuestionById(int id){
            Cursor c = this.myDataBase.rawQuery("SELECT * FROM Question WHERE _id = "+ id, null);
            c.moveToFirst();
            return cursorToQuestion(c);
        }

        public Question cursorToQuestion(Cursor cursor){
            //si aucun élément n'a été retourné dans la requête, on renvoie null
            if (cursor.getCount() == 0) {
                return null;
            }
            //Sinon on se place sur le premier élément
            //cursor.moveToFirst();
            //On créé une question
            Question question = new Question(cursor.getString(1));
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            question.setId(cursor.getInt(0));

            //On ferme le cursor
            //cursor.close();

            return question;
        }

        // récupérer les questions de la BDD et remplir l' ArrayList<Question> questions avec
        public void fillQuestionsFromDB(){

            Cursor cur = this.myDataBase.rawQuery("SELECT * FROM Question", null);

            cur.moveToFirst();

            while (cur.isAfterLast() == false) {
                System.out.println("BOOOOWWAAA " + cur.getString(1));
                questions.add(cursorToQuestion(cur));
                cur.moveToNext();
            }

            // on ferme le cursor
            cur.close();
        }


        /****************************** Activité *************************************/
        public ActivityToDo getActivityToDoById(int id){
            Cursor c = this.myDataBase.rawQuery("SELECT * FROM Activity WHERE _id = "+ id, null);
            c.moveToFirst();
            return cursorToActivityToDo(c);
        }


        private ActivityToDo cursorToActivityToDo(Cursor c){
            //si aucun élément n'a été retourné dans la requête, on renvoie null
            if (c.getCount() == 0) {
                return null;
            }
            //Sinon on se place sur le premier élément
            //c.moveToFirst();
            //On créé une activité
            ActivityToDo activityToDo = new ActivityToDo(c.getString(1), c.getInt(0));
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            activityToDo.setIdActivity(c.getInt(0));
            activityToDo.setNameActivity(c.getString(1));

            // caster favorite et discover : int en bdd -> boolean en java
            boolean favorite = c.getInt(2) > 0 ? true : false ;
            boolean discover = c.getInt(3) > 0 ? true : false ;

            activityToDo.setFavorite(favorite);
            activityToDo.setDiscovered(discover);

            System.out.println("BOOOOWWAAA cursorToActivityToDo" + c.getString(1));
            System.out.println(activityToDo.toString());

            //On ferme le cursor
            //c.close();

            //On retourne le livre
            return activityToDo;
        }

        // récupérer les activités de la BDD et remplir l' ArrayList<ActivityToDo> activities  avec
        public void fillActivitiesToDoFromDB(){

            Cursor cur = this.myDataBase.rawQuery("SELECT * FROM Activity", null);

            cur.moveToFirst();

            while (cur.isAfterLast() == false) {
                System.out.println("BOOOOWWAAA fillActivitiesToDoFromDB" + cur.getString(1));
                activities.add(cursorToActivityToDo(cur));
                System.out.println("BOOOOWWAAA fillActivitiesToDoFromDB" + cur.getString(1));
                cur.moveToNext();
            }

            // on ferme le cursor
            cur.close();
        }


        //Impact d'une activité selon une question
        Answer getImpactActivity(int idActivity, int  idQuestion)
        {
            System.out.println("getImpactActivity");
            //Lecture en BD
            //Cursor cur = this.myDataBase.rawQuery("SELECT impact FROM ActivityQuestion WHERE id_activity = "+idActivity+ " AND id_question ="+ idQuestion, null);

            System.out.println("idActivity " + idActivity);
            System.out.println("idQuestion " + idQuestion);
            int zero = 0;
            Cursor cur = this.myDataBase.rawQuery("SELECT impact FROM ActivityQuestion WHERE id_activity = "+idActivity+ " AND id_question ="+ idQuestion,null);
            System.out.println("getImpactActivity");
            System.out.println("taille colonne cursor "+ cur.getColumnCount());
            System.out.println("taille cursor "+ cur.getCount());


           // System.out.println(cur.getString(0));


            if (cur.getCount() == 0){
                return Answer.NoMatter;
            }
            else {

                cur.moveToFirst();

                System.out.println("getImpactActivity cur != null");
                int impact = cur.getInt(0);
                System.out.println("getImpactActivity impact = "+ impact);
                switch (impact) {
                    case 0:
                        return Answer.No;
                    case 1:
                        return Answer.Yes;
                    case 2:
                        return Answer.NoMatter;
                    default:
                        return Answer.NoMatter;
                }
            }

        }

    @Override
        public synchronized void close() {
            if (myDataBase != null) {
                myDataBase.close();
            }
            super.close();
        }
        @Override
        public void onCreate(SQLiteDatabase db) {}
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }