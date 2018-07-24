package com.ozkanbolukbas.notes.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.activity.PublicnotesActivity;
import com.ozkanbolukbas.notes.model.User;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    EditText userEdit;
    EditText passwordEdit;
    Button loginButton;
    Button signupButton;
    Button publicNotes;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        passwordEdit = view.findViewById(R.id.password_EditText);
        userEdit = view.findViewById(R.id.username_EditText);
        loginButton = view.findViewById(R.id.login_button);
        signupButton = view.findViewById(R.id.signup_button);
        publicNotes = view.findViewById(R.id.public_notes);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(userEdit.getText().toString());
                user.setPassword(passwordEdit.getText().toString());
                mListener.onLoginClicked(user);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNavigateToSignupClicked();
            }
        });

        publicNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublicnotesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onLoginClicked(User user);
        void onNavigateToSignupClicked();
    }
}
