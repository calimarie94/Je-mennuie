package com.android.imac.je_m_ennuie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;



/**
 * Created by Marie on 26/12/2014.
 */
public class DetailedActivityActivity extends Activity implements View.OnClickListener {
    final String EXTRA_FAVORITE = "is_favorite";
    Button btn_facebook;
    Button btn_twitter;
    Button btn_gmail;
    Button btn_favorite;
    Intent intent;
    boolean is_favorite;
    private UiLifecycleHelper uiHelper;
    private char coucou;
    TextView text_activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);

        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        /* Récupération des éléments de la vue */
        TextView title_activity = (TextView) findViewById(R.id.title_activity_detailed);
        text_activity = (TextView) findViewById(R.id.text_activity_detailed);
        btn_facebook = (Button) findViewById(R.id.btn_facebook);
        btn_twitter = (Button) findViewById(R.id.btn_twitter);
        btn_gmail = (Button) findViewById(R.id.btn_gmail);
        btn_favorite = (Button) findViewById(R.id.btn_favorite);
        intent = getIntent();
        is_favorite=intent.getBooleanExtra(EXTRA_FAVORITE,false);

        /* On charge la bonne police */
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        title_activity.setTypeface(font);
        btn_favorite.setTypeface(font);

        if (intent.getStringExtra("text_result")!=null)
            text_activity.setText(intent.getStringExtra("text_result"));

        /* Changement de couleur au clic */
        btn_favorite.setBackgroundResource(R.drawable.selector);

        /* Changement du bouton favori en fonction de l'activité */
        if (is_favorite){
            btn_favorite.setText("Retirer des favoris");
        // Sinon
        }else{
            btn_favorite.setText("Ajouter aux favoris");
        }

        /* Evenements au clic */
        btn_facebook.setOnClickListener(this);
        btn_twitter.setOnClickListener(this);
        btn_gmail.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_facebook) {
            if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                    FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
                // Publish the post using the Share Dialog
                FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                        .setName("Application je m'ennuie")
                        .setDescription("L'application je m'ennuie m'a proposé cette activité : "+text_activity.getText())
                        .setLink("https://www.facebook.com/appli.jemennuie")
                        .build();
                uiHelper.trackPendingDialogCall(shareDialog.present());

            } else {
                Toast.makeText(getApplicationContext(),"Application Facebook non installée",Toast.LENGTH_LONG).show();
            }
        }

        if(v==btn_twitter)
            Toast.makeText(getApplicationContext(), "Partage Twitter", Toast.LENGTH_SHORT).show();

        if(v==btn_gmail)
            Toast.makeText(getApplicationContext(), "Partage Google", Toast.LENGTH_SHORT).show();

        if(v==btn_favorite)
            if(is_favorite){
                btn_favorite.setText("Ajouter aux favoris");
                is_favorite=false;
                //fonction pour retirer des favoris
            }else{
                btn_favorite.setText("Retirer des favoris");
                is_favorite=true;
                //fonction pour ajouter des favoris
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }


}
