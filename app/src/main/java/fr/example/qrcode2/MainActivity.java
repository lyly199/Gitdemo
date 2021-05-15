package fr.example.qrcode2;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    // Var initialisation
    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Affectation des variables pour scan
        btScan = findViewById(R.id.bt_scan);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        MainActivity.this
                );
                // Display Message Scan
                intentIntegrator.setPrompt("For flash use volume up key");

                // Son pour la scan
                intentIntegrator.setBarcodeImageEnabled(true);

                // orientation fixe
                intentIntegrator.setOrientationLocked(true);

                // Récupération de l'activité
                intentIntegrator.setCaptureActivity(Capture.class);

                //Initialisation du scan
                intentIntegrator.initiateScan();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //initialisation intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        //Check condition
        if (intentResult.getContents() != null) {
            // Msg => Null
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );

            // Titre
            builder.setTitle("Result");

            //Message
            builder.setMessage(intentResult.getContents());

            //Bouton Ok
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            // Show alerte
            builder.show();
        } else  {
            Toast.makeText(getApplicationContext(), "OOps rien scané", Toast.LENGTH_SHORT).show();
        }
    }
}