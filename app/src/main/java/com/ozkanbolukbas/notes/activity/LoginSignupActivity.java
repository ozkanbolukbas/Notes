package com.ozkanbolukbas.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.api.SimpleCallback;
import com.ozkanbolukbas.notes.fragment.LoginFragment;
import com.ozkanbolukbas.notes.fragment.SignupFragment;
import com.ozkanbolukbas.notes.model.LoginResponse;
import com.ozkanbolukbas.notes.model.User;

import retrofit2.Response;

public class LoginSignupActivity extends BaseActivity implements SignupFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {
    LoginFragment loginFragment;
    SignupFragment signupFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        loginFragment = LoginFragment.newInstance();
        signupFragment = SignupFragment.newInstance();

        onNavigateToSignupClicked();
    }

    @Override
    public void onLoginClicked(User user) {
        loginAndNavigate(user);
    }

    private void loginAndNavigate(User user) {
        getApi().login(user).enqueue(new SimpleCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                getStorage()
                        .setSessionId(loginResponse.getSessionId())
                        .save();
                navigateToWelcomeScreen();
            }

            @Override
            public void onClientError(Response<LoginResponse> response) {
                Toast.makeText(LoginSignupActivity.this, "Try a different username and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToWelcomeScreen() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToSignupClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signupFragment)
                .commitNow();
    }

    @Override
    public void onSignupClicked(final User user) {
        getApi().signUp(user).enqueue(new SimpleCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loginAndNavigate(user);
            }

            @Override
            public void onClientError(Response<Void> response) {
                Toast.makeText(LoginSignupActivity.this, "Try a different username", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNavigateToLoginClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitNow();
    }
}
