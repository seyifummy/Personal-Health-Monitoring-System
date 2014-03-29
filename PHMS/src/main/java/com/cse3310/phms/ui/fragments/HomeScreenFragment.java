/*
 * Copyright (c) 2014 Personal-Health-Monitoring-System
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cse3310.phms.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.activeandroid.ActiveAndroid;
import com.cse3310.phms.R;
import com.cse3310.phms.model.User;
import com.cse3310.phms.model.WeightLog;
import com.cse3310.phms.ui.utils.UserSingleton;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.GregorianCalendar;

@EFragment(R.layout.home_screen)
public class HomeScreenFragment extends SherlockFragment{

    @ViewById(R.id.initialize_test_data)
    Button testButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.overflow_menu, menu);
    }

    @AfterViews
    void setClickListener() {
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeTestData();
            }
        });
    }


    private void initializeTestData() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        ActiveAndroid.beginTransaction();
        try {
//            WeightLog weightLog = new WeightLog(150);
//            weightLog.save();
            GregorianCalendar calendar = new GregorianCalendar(2014, Calendar.APRIL, 15);
            user.getDiet().addWeightLog(new WeightLog(400).setDate(calendar.getTime()));
//            calendar.set(2014, Calendar.JULY, 6);
//            user.getDiet().addWeightLog(new WeightLog(120).setDate(calendar.getTime()));
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        Toast.makeText(getActivity(), "DONE", Toast.LENGTH_SHORT).show();
    }


}
