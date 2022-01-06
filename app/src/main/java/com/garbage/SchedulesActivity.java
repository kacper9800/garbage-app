package com.garbage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.garbage.schedule.Schedule;
import com.garbage.schedule.ScheduleListAdapter;
import com.garbage.service.CallBack;
import com.garbage.service.FirebaseService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

public class SchedulesActivity extends AppCompatActivity {

    private final FirebaseService firebaseService = FirebaseService.getInstance();

    private String districtName;

    private RecyclerView scheduleList;
    private ScheduleListAdapter scheduleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules_main);

        districtName = getIntent().getStringExtra(MainActivity.EXTRA_DISTRICT_NAME);
        TextView textView = findViewById(R.id.header);
        textView.setText(getString(R.string.schedules_header, districtName));

        scheduleList = findViewById(R.id.schedule_list);

        fetchAndPopulateSchedules();
        setupListFilter();
    }

    private void fetchAndPopulateSchedules() {
        firebaseService.fetchSchedules(new CallBack() {
            @Override
            public void onSuccess(Object object) {
                List<Schedule> schedules = ((List<Schedule>) object).stream()
                        .filter(schedule -> schedule.getDistrict().equals(districtName)).collect(Collectors.toList());
                scheduleListAdapter = new ScheduleListAdapter(SchedulesActivity.this, schedules);
                scheduleList.setAdapter(scheduleListAdapter);
                scheduleListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object object) {
                //TODO
            }
        });
    }

    private void setupListFilter() {
        TextInputEditText filter = findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scheduleListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}