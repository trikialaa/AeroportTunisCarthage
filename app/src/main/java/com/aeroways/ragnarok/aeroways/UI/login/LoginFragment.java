package com.aeroways.ragnarok.aeroways.UI.login;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.AppActivity;
import com.aeroways.ragnarok.aeroways.Entities.User;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.KeyboardHider;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment implements Validator.ValidationListener {

    @NotEmpty(message = "Ce champs est obligatoire")
    @Email(message = "Veuillez saisir une adresse e-mail valide")
    EditText mEmail;

    @NotEmpty(message = "Ce champs est obligatoire")
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,message = "Votre mot de passe doit comporter au moins 6 caractères dont des lettres minisules et majusules, des chiffres, et des symboles")
    EditText mPassword;

    Button mLoginButton;

    Validator validator;

    LinearLayout linearLayout;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        validator = new Validator(this);
        validator.setValidationListener(this);

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mEmail = getActivity().findViewById(R.id.edittextInput_email);
        mPassword = getActivity().findViewById(R.id.edittextInput_pass);

        //Bouton s'enregistrer
        mLoginButton = getActivity().findViewById(R.id.button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHider.closeKeyboard(getActivity());
                validator.validate();
            }
        });

        super.onViewCreated(view, savedInstanceState);

        //Keyboard hider when clicked outside

        linearLayout = getActivity().findViewById(R.id.linearLayout);
        linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) KeyboardHider.closeKeyboard(getActivity());
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onValidationSucceeded() {
        mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            mDatabaseRef = mDatabase.getReference("users");
                            Log.e(TAG, "Getting data from aeroporttuniscarthage/users/"+firebaseUser.getUid());
                            Query query = mDatabaseRef.orderByChild("id").equalTo(firebaseUser.getUid());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                        user = childSnapshot.getValue(User.class);
                                    }
                                    Log.d(TAG, "Value is: " + user.toString());
                                    SharedPreferencesUtils.saveUser(getActivity(),user);
                                    startActivity(new Intent(getActivity(), AppActivity.class));
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.e(TAG, "Erreur de l'importation des données de l'utilisateur", error.toException());
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Erreur lors de l'authentification",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(getActivity(), "Erreur lors du login",
                Toast.LENGTH_SHORT).show();
    }
}
