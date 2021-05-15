package fr.example.qrcode2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class Login_screen extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

            //manager biometrique
            TextView msg_txt = findViewById(R.id.txt_msg);
            Button login_btn = findViewById(R.id.login_magic);

            androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);

            switch (biometricManager.canAuthenticate()){
                case androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS:              //accès OK
                    msg_txt.setText("Connexion Biométrique activée");

                    setContentView(R.layout.activity_login_screen);
                    button = (Button) findViewById(R.id.login_magic);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openNewActivity();
                        }
                    });

                    break;

                case androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:     // de matériel
                    msg_txt.setText("Acces Ko");
                    login_btn.setVisibility(View.GONE);
                    break;

                case androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:  //
                    msg_txt.setText("Acces Panne");
                    login_btn.setVisibility(View.GONE);
                    break;

                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    msg_txt.setText("Pas de connexion possible avec empreinte");
                    login_btn.setVisibility(View.GONE);
                    break;

                case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                    msg_txt.setText("Mise à jour de la sécurité est nécessaire");
                    login_btn.setVisibility(View.GONE);
                    break;

                case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                    msg_txt.setText("Utilisation de la reconnaissance biométrique impossible");
                    login_btn.setVisibility(View.GONE);
                    break;

                case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                    msg_txt.setText("Erreur Inconnue");
                    login_btn.setVisibility(View.GONE);
                    break;
            }

            Executor executor = ContextCompat.getMainExecutor(this);

            //retour du résultat
            androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(Login_screen.this ,executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {


                //@Override
                //     public void onAuthenticationError(int errorCode, @NonNull @NotNull CharSequence errString) {
                //     super.onAuthenticationError(errorCode, errString);
                //        }

                //        @Override
                //        public void onAuthenticationSucceeded(@NonNull @NotNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                //            super.onAuthenticationSucceeded(result);
                //            Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).hashCode();
                //        }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });

            androidx.biometric.BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Login")
                    .setDescription("User your fingerprint to call  login to your app")
                    .setNegativeButtonText("cancel")
                    .build();

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biometricPrompt.authenticate(promptInfo);
                }
            });

        //super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_screen);
//        button = (Button) findViewById(R.id.login_magic);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNewActivity();
//            }
//        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}