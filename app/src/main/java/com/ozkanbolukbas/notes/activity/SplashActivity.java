package com.ozkanbolukbas.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.api.SimpleCallback;
import com.ozkanbolukbas.notes.model.User;

import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(getStorage().getSessionId() == null) {
            navigateToLoginSignupScreen();
        } else {
            checkIfTheSessionIsWorking();
        }
    }

    private void navigateToLoginSignupScreen() {
        Intent intent = new Intent(this, LoginSignupActivity.class);
        navigateDelayed(intent);
    }

    private void navigateToWelcomeScreen() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        navigateDelayed(intent);
    }

    private void navigateDelayed(final Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void checkIfTheSessionIsWorking() {
        getApi().getMe().enqueue(new SimpleCallback<User>() {
            @Override
            public void onSuccess(User user) {
                navigateToWelcomeScreen();
            }

            @Override
            public void onNoContent(Response<User> response) {
                navigateToLoginSignupScreen();
            }
        });
    }
}
