package com.codebeginner.sovary.fingerprinttest;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.codebeginner.sovary.fingerprinttest.reotrfit.RetrofitService;
import com.codebeginner.sovary.fingerprinttest.reotrfit.User;
import com.codebeginner.sovary.fingerprinttest.reotrfit.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.concurrent.Executor;


public class MainActivity extends AppCompatActivity {

    Button btn_fppin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* btn_fp = findViewById(R.id.btn_fp);*/
        btn_fppin = findViewById(R.id.btn_fppin);
        try {
            checkBioMetricSupported();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                sendPostRequest();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //attempt not regconized fingerprint
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        /*btn_fp.setOnClickListener(view -> {
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setNegativeButtonText("Cancel");
            biometricPrompt.authenticate(promptInfo.build());
        });*/
        btn_fppin.setOnClickListener(view -> {
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setDeviceCredentialAllowed(true);
            biometricPrompt.authenticate(promptInfo.build());
        });
    }

    BiometricPrompt.PromptInfo.Builder dialogMetric() {
        //Show prompt dialog
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric credential");
    }

    //    public static void sendPingRequest(String ipAddress)
//            throws UnknownHostException, IOException
//    {
//        InetAddress geek = InetAddress.getByName(ipAddress);
//        System.out.println("Sending Ping Request to " + ipAddress);
//        if (geek.isReachable(5000))
//            System.out.println("Host is reachable");
//        else
//            System.out.println("Sorry ! We can't reach to this");
//}
    //must running android 6
    public void sendPostRequest() {
        // Specify the API endpoint URL
        RetrofitService retrofitService = new RetrofitService();
        UserApi usrApi = retrofitService.getRetrofit().create(UserApi.class);
        // Create a new User object with the necessary data
//        Call<User> call = userService.addUser(user);
        User usr = new User();
        usr.setLocationLat("8");
        usr.setLocationLat("12");
        usr.setCardNumber("123456789");
        usr.setStaticCvv(123);
        Call<ResponseEntity<String>> call = usrApi.addUser(usr);
        call.enqueue(new Callback<ResponseEntity<String>>() {
            @Override
            public void onResponse(Call<ResponseEntity<String>> call, Response<ResponseEntity<String>> response) {
                if (!response.isSuccessful()) {
//                    .isSuccessful()

                    // Handle unsuccessful response
                    Toast.makeText(getApplicationContext(), "Save failed: " + response.message(), Toast.LENGTH_SHORT).show();
                } else {//.isSuccessful()
                    // Handle successful response
                    Toast.makeText(getApplicationContext(), "Save successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEntity<String>> call, Throwable t) {
                // Handle failure
                Toast.makeText(getApplicationContext(), "Save failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


        void checkBioMetricSupported() throws IOException {
        BiometricManager manager = BiometricManager.from(this);
        String info;
        switch (manager.canAuthenticate(BIOMETRIC_WEAK | BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                String url="heyy";
////                String ipAddress = "192.168.0.244:8082";
//                sendPingRequest(ipAddress);
//                info = "App can authenticate using biometrics.";
                Log.e("Called2",url);
                enableButton(true);
//                sendPostRequest();
                Log.e("Called3",url);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                info = "No biometric features available on this device.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                info = "Biometric features are currently unavailable.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                info = "Need register at least one finger print";
                enableButton(false, true);
                break;
            default:
                info = "Unknown cause";
                enableButton(false);
        }
        /*TextView txinfo =  findViewById(R.id.tx_info);
        txinfo.setText(info);*/
    }

    void enableButton(boolean enable) {
        //btn_fp.setEnabled(enable);
        btn_fppin.setEnabled(true);
    }

    void enableButton(boolean enable, boolean enroll) {
        enableButton(enable);

        if (!enroll) return;
        // Prompts the user to create credentials that your app accepts.
        //Open settings to set credential
        final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
        enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
        startActivity(enrollIntent);
    }
}