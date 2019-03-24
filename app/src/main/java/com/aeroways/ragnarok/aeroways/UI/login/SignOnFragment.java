package com.aeroways.ragnarok.aeroways.UI.login;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ablanco.imageprovider.ImageProvider;
import com.ablanco.imageprovider.ImageSource;
import com.aeroways.ragnarok.aeroways.Entities.User;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.KeyboardHider;
import com.aeroways.ragnarok.aeroways.utils.RandomStringGenerator;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static android.content.ContentValues.TAG;
import static com.aeroways.ragnarok.aeroways.utils.BitmapUtils.bitmapToFile;
import static com.aeroways.ragnarok.aeroways.utils.BitmapUtils.cropToSquare;

public class SignOnFragment extends Fragment implements Validator.ValidationListener {

    @NotEmpty(message = "Ce champs est obligatoire")
    EditText mName;

    @NotEmpty(message = "Ce champs est obligatoire")
    EditText mLname;

    Spinner mSex;

    @NotEmpty(message = "Ce champs est obligatoire")
    @Pattern(regex = "^[0-3][0-9]\\/[0-1][0-9]\\/[0-9]{4}$",message = "Veuillez entrer une date valide")
    EditText mBirthDate;

    Spinner mNat;

    @NotEmpty(message = "Ce champs est obligatoire")
    @Email(message = "Veuillez saisir une adresse e-mail valide")
    EditText mEmail;

    @NotEmpty(message = "Ce champs est obligatoire")
    EditText mAddress;

    @NotEmpty(message = "Ce champs est obligatoire")
    EditText mPhone;

    @NotEmpty(message = "Ce champs est obligatoire")
    EditText mId;

    @NotEmpty(message = "Ce champs est obligatoire")
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,message = "Votre mot de passe doit comporter au moins 6 caractères dont des lettres minisules et majusules, des chiffres, et des symboles")
    EditText mPassword;

    @NotEmpty(message = "Ce champs est obligatoire")
    @ConfirmPassword(message = "Les deux mots de passe sont différents")
    EditText mPasswordConf;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    Button signOn;
    Button imageSelector;
    ImageView imageView;

    Validator validator;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private User user;

    private Uri profilePicUri;

    private LinearLayout linearLayout;

    private final int MY_PERMISSIONS_REQUEST_GALLERY = 1997;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();

        validator = new Validator(this);
        validator.setValidationListener(this);

        return inflater.inflate(R.layout.fragment_sign_on, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Elements Definition
        mName = getActivity().findViewById(R.id.edittextInput_name);
        mLname = getActivity().findViewById(R.id.edittextInput_lname);
        mSex = getActivity().findViewById(R.id.editText5);
        mBirthDate = getActivity().findViewById(R.id.editText7);
        mNat = getActivity().findViewById(R.id.editText6);
        mEmail = getActivity().findViewById(R.id.edittextInput_email);
        mAddress = getActivity().findViewById(R.id.edittextInput_address);
        mPhone = getActivity().findViewById(R.id.edittextInput_tel);
        mId = getActivity().findViewById(R.id.edittextInput_id);
        mPassword = getActivity().findViewById(R.id.edittextInput_pass);
        mPasswordConf = getActivity().findViewById(R.id.edittextInput_passc);
        signOn = getActivity().findViewById(R.id.button2);
        imageSelector = getActivity().findViewById(R.id.button3);
        imageView = getActivity().findViewById(R.id.imageView);

        // Date Picker
        mBirthDate.setFocusable(false);
        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Calendar c = Calendar.getInstance();
                  int year = c.get(Calendar.YEAR);
                  int month = c.get(Calendar.MONTH);
                  int day = c.get(Calendar.DAY_OF_MONTH);

                  DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,day);
                  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                  dialog.show();
                }
            }
        );

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String sYear = String.valueOf(year);
                String sMonth;
                String sDay;
                if (month<10) sMonth="0"+String.valueOf(month); else sMonth=String.valueOf(month);
                if (dayOfMonth<10) sDay="0"+String.valueOf(dayOfMonth); else sDay=String.valueOf(dayOfMonth);
                mBirthDate.setText(sDay+"/"+sMonth+"/"+sYear);
            }
        };

        //Bouton de selection d'image
        imageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))||(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_GALLERY);
                    }

                }
                ImageProvider imageProvider = new ImageProvider(getActivity());
                imageProvider.getImage(ImageSource.GALLERY, new Function1<Bitmap, Unit>() {
                    @Override
                    public Unit invoke(Bitmap bitmap) {
                        if (bitmap!=null) imageView.setImageBitmap(cropToSquare(bitmap));
                        return null;
                    }
                });

            }
        });

        //Bouton s'enregistrer
        signOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    }

    @Override
    public void onValidationSucceeded() {

        Toast.makeText(getActivity(), "Validation Success", Toast.LENGTH_SHORT).show();

        user = new User();
        user.setFirstName(mName.getText().toString());
        user.setLastName(mLname.getText().toString());
        if (mSex.getSelectedItem().toString().equals("Homme")) user.setGender(true); else user.setGender(false);
        user.setDateOfBirth(mBirthDate.getText().toString());
        user.setCountry(mNat.getSelectedItem().toString());
        user.setEmail(mEmail.getText().toString());
        user.setAddress(mAddress.getText().toString());
        user.setMobile(mPhone.getText().toString());

        Uri file = null;
        try {
            file = Uri.fromFile(bitmapToFile(getActivity(),((BitmapDrawable)imageView.getDrawable()).getBitmap()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mStorageRef = FirebaseStorage.getInstance().getReference();
        final String picName = RandomStringGenerator.generate(32);
        final StorageReference profilePicRef = mStorageRef.child("images/" + picName + ".jpg");
        UploadTask uploadTask =  profilePicRef.putFile(file);


        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               Log.e(TAG,"Uploading picture failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e(TAG,"Uploading picture succeeded");
            }
        });

        mAuth.createUserWithEmailAndPassword(user.getEmail(), mPassword.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mAuth.signInWithEmailAndPassword(user.getEmail(),mPassword.getText().toString())
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                String userId = firebaseUser.getUid();
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(user.getFirstName()+" "+user.getLastName())
                                                        .build();

                                                firebaseUser.updateProfile(profileUpdates);

                                                user.setId(userId);
                                                user.setProfilePicLink("images/" + picName + ".jpg");
                                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                                mDatabase.child("users").child(userId).setValue(user);

                                            }
                                        }
                                    });



                            Log.d(TAG, "Utilisateur " + user.getFirstName() + " créé avec succès");
                            KeyboardHider.closeKeyboard(getActivity());
                            SharedPreferencesUtils.saveUser(getActivity(),user);
                            FragmentUtils.loadMain(getActivity(),new LoginFragment());

                        } else {
                            Log.e(TAG, "Erreur de création", task.getException());
                            Toast.makeText(getActivity(), "Erreur lors de la création du compte",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
