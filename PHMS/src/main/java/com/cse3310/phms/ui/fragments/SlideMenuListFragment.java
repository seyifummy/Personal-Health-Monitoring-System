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
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.activeandroid.Cache;
import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.cse3310.phms.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideMenuListFragment extends SherlockListFragment {
    private List<String> topViewTitleList = new ArrayList<String>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 20; i++) {
            topViewTitleList.add("Title " + i);
        }

        BindDictionary<String> dict = new BindDictionary<String>();
        dict.addStringField(R.id.frag_list_top_view_title,
                new StringExtractor<String>() {
                    @Override
                    public String getStringValue(String s, int i) {
                        return s;
                    }
                });

        FunDapter<String> adapter = new FunDapter<String>(Cache.getContext(),
                topViewTitleList, R.layout.frag_list_top_view, dict);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        EventBus.getDefault().postSticky(new EventBusDemoEvent.ButtonClickEvent(position));
        Toast.makeText(getActivity(), ">" + position, Toast.LENGTH_SHORT).show();
        ((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle(); // close sliding menu after clicking an item
    }
}
