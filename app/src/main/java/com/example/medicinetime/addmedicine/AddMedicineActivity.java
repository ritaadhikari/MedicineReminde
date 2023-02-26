package com.example.medicinetime.addmedicine;

import android.content.Intent;
import android.os.PersistableBundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.medicinetime.Injection;
import com.example.medicinetime.LoginActivity;
import com.example.medicinetime.R;
import com.example.medicinetime.medicine.MedicineActivity;
import com.example.medicinetime.utils.ActivityUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddMedicineActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    public static final String EXTRA_TASK_ID = "task_extra_id";
    public static final String EXTRA_TASK_NAME = "task_extra_name";

    private AddMedicinePresenter mAddMedicinePresenter;

    private ActionBar mActionBar;

    FloatingActionButton Fab1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        //Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddMedicineFragment addMedicineFragment = (AddMedicineFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        int medId = getIntent().getIntExtra(AddMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID,0);
        String medName = getIntent().getStringExtra(AddMedicineFragment.ARGUMENT_EDIT_MEDICINE_NAME);

        setToolbarTitle(medName);


        if (addMedicineFragment == null) {
            addMedicineFragment = AddMedicineFragment.newInstance();

            if (getIntent().hasExtra(AddMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID)) {
                Bundle bundle = new Bundle();
                bundle.putInt(AddMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID, medId);
                addMedicineFragment.setArguments(bundle);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addMedicineFragment, R.id.contentFrame);
        }

        boolean shouldLoadDataFromRepo = true;
        // Prevent the presenter from loading data from the repository if this is a config change.
        if (savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

//        // Create the presenter
        mAddMedicinePresenter = new AddMedicinePresenter(
                medId,
                Injection.provideMedicineRepository(getApplicationContext()),
                addMedicineFragment,
                shouldLoadDataFromRepo);

        Fab1 = (FloatingActionButton) findViewById(R.id.fab_logout);

        Fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMedicineActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void setToolbarTitle(String medicineName) {
        if (medicineName == null) {
            mActionBar.setTitle(getString(R.string.new_medicine));
        } else {
            mActionBar.setTitle(medicineName);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mAddMedicinePresenter.isDataMissing());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
