package com.ozkanbolukbas.notes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ozkanbolukbas.notes.R;
import com.ozkanbolukbas.notes.model.User;

public class SignupFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    EditText userEdit;
    EditText passwordEdit;
    EditText nameEdit;
    Button loginButton;
    Button signupButton;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        passwordEdit = view.findViewById(R.id.password_EditText);
        userEdit = view.findViewById(R.id.username_EditText);
        nameEdit = view.findViewById(R.id.name_EditText);
        loginButton = view.findViewById(R.id.login_button);
        signupButton = view.findViewById(R.id.signup_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNavigateToLoginClicked();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(userEdit.getText().toString());
                user.setPassword(passwordEdit.getText().toString());
                user.setName(nameEdit.getText().toString());

                mListener.onSignupClicked(user);
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
        void onSignupClicked(User user);
        void onNavigateToLoginClicked();
    }
}
