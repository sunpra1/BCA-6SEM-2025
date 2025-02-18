package com.sunpra.classroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sunpra.classroom.databinding.ActivityLayoutLearnRlBinding;

import java.util.Arrays;

public class LayoutLearnActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "LayoutLearnActivity";

    private ActivityLayoutLearnRlBinding binding;

    Gender[] genders = {Gender.MALE, Gender.FEMALE, Gender.OTHERS};

    Gender selectedGender;

    boolean addExtraCheese = false;

    boolean addExtraToppings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //region Name
        binding = ActivityLayoutLearnRlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.clickMe.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
//                        if (validate()) {
//                            String userName = binding.userName.getText().toString();
//                            Toast.makeText(
//                                    LayoutLearnActivity.this,
//                                    "Your username is :" + userName,
//                                    Toast.LENGTH_LONG
//                            ).show();
//                        }
//                        binding.userName.clearFocus();

                        //region spinner
                        //Using selected index
//                        int selectedGenderPosition = binding.spinner.getSelectedItemPosition();
//                        Gender selectedGender = genders[selectedGenderPosition];

                        // Using selected item
//                        Gender selectedGender = (Gender) binding.spinner.getSelectedItem();
//
//                        Toast.makeText(
//                                LayoutLearnActivity.this,
//                                "You have chosen " + selectedGender,
//                                Toast.LENGTH_LONG
//                        ).show();

                        if (validateGender()) {
                            Log.i(TAG, "onClick: Gender is valid.");
                        }
                        //endregion
                    }
                }
        );

        binding.userName.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            binding.userNameTil.setErrorEnabled(false);
                            binding.userNameTil.setError("");
                        }
                    }
                }
        );

//        String[] genders = getResources().getStringArray(R.array.gender);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Gender selection = (Gender) adapterView.getItemAtPosition(position);
                Toast.makeText(
                        LayoutLearnActivity.this,
                        "Selected: " + selection,
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            } // do nothing
        });
        binding.spinner.setAdapter(
                new ArrayAdapter<>(
                        LayoutLearnActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        genders
                ) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            return false;
                        }
                        return super.isEnabled(position);
                    }
                }


        );
        //endregion

        int index = Arrays.asList(genders).indexOf(Gender.OTHERS);
        binding.spinner.setSelection(index);

        binding.genderWrapper.setOnCheckedChangeListener(LayoutLearnActivity.this);
        binding.addExtraCheeseCb.setOnCheckedChangeListener(LayoutLearnActivity.this);
        binding.addExtraToppingsCb.setOnCheckedChangeListener(LayoutLearnActivity.this);
    }

    private boolean validateUserName() {
        boolean isvalid = true;

        String userName = binding.userName.getText().toString();

        if (userName.length() < 5) {
            isvalid = false;
            String usernameError = "Username should be 5 characters long.";
            binding.userNameTil.setErrorEnabled(true);
            binding.userNameTil.setError(usernameError);
        }

        return isvalid;
    }

    private boolean validateGender() {
        boolean isValid = true;
        if (selectedGender == null) {
            isValid = false;
            String validationMessage = "Gender is required.";
            new AlertDialog.Builder(LayoutLearnActivity.this)
                    .setTitle(R.string.gender_error)
                    .setMessage(validationMessage)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }

        return isValid;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton.getId() == R.id.addExtraCheeseCb){
            addExtraCheese = isChecked;
        }else if(compoundButton.getId() == R.id.addExtraToppingsCb){
            addExtraToppings = isChecked;
        }
        Log.d(TAG, "onCheckedChanged: addExtraCheese: " + addExtraCheese + " - addExtraTopping: " + addExtraToppings );
    }
    //"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        boolean isMaleChecked = i == R.id.radioMale;
        boolean isFemaleChecked = i == R.id.radioFemale;
        boolean isOthersChecked = i == R.id.radioOthers;

        if (isMaleChecked) selectedGender = Gender.MALE;
        else if (isFemaleChecked) selectedGender = Gender.FEMALE;
        else if (isOthersChecked) selectedGender = Gender.OTHERS;

        Log.d(TAG, "onCheckedChanged: chosen gender: " + selectedGender);
    }
}