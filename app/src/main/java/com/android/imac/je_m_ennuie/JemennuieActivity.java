package com.android.imac.je_m_ennuie;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class JemennuieActivity extends ActionBarActivity {

    public static final String DB_NAME = "Jemennuie_database.sqlite3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jemennuie);

        System.out.println("Debut du jeu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Debut du jeu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Debut du jeu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(printKeyHash(this));

        /* Récupération des éléments de la vue */
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        button1.setTypeface(font);
        button2.setTypeface(font);
        button3.setTypeface(font);


        /* Changement de couleur au clic */
        button1.setBackgroundResource(R.drawable.selector);
        button2.setBackgroundResource(R.drawable.selector);
        button3.setBackgroundResource(R.drawable.selector);

        /* Evenements au clic */
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(JemennuieActivity.this, GameDisplayActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(JemennuieActivity.this, ListActivitiesActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        DataBaseHelper myDbHelper = DataBaseHelper.getInstance(this);

        // Création de la BDD
        System.out.println("Debut Database");
        myDbHelper.createDataBase();
        System.out.println("Database created ! ");

        myDbHelper.openDataBase();
        myDbHelper.fillQuestionsFromDB();
        myDbHelper.fillActivitiesToDoFromDB();

        // Création du jeu
        Game game = Game.getInstance(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jemennuie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // fonction pour avoir la hash key de l'appli
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {

            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }

        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
