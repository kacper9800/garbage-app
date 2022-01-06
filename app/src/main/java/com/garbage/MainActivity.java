package com.garbage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.garbage.district.DistrictListAdapter;
import com.garbage.service.CallBack;
import com.garbage.service.FirebaseService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DISTRICT_NAME = "com.garbage.DISTRICT_NAME";

    private final FirebaseService firebaseService = FirebaseService.getInstance();

    private RecyclerView districtList;
    private DistrictListAdapter districtListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        districtList = findViewById(R.id.district_list);

        fetchAndPopulateDistricts();
        setupListFilter();
        setupListSort();
    }

    private void fetchAndPopulateDistricts() {
        firebaseService.fetchDistricts(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                List<String> districts = (List<String>) object;
                districtListAdapter = new DistrictListAdapter(MainActivity.this, districts);
                districtListAdapter.setClickListener(districtName -> navigateToSchedules(districtName));
                districtList.setAdapter(districtListAdapter);
                districtListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object object) {
                //TODO
            }
        });
    }

    private void navigateToSchedules(String districtName) {
        Intent intent = new Intent(this, SchedulesActivity.class);
        intent.putExtra(EXTRA_DISTRICT_NAME, districtName);
        startActivity(intent);
    }

    private void setupListFilter() {
        TextInputEditText filter = findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                districtListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupListSort() {
        ImageButton sort = findViewById(R.id.sort);
        sort.setOnClickListener(v -> districtListAdapter.sortList());
    }
}