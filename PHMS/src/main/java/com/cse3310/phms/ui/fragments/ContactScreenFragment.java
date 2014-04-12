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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.cse3310.phms.R;
import com.cse3310.phms.model.DoctorInfo;
import com.cse3310.phms.model.Info;
import com.cse3310.phms.model.User;
import com.cse3310.phms.ui.cards.ContactCard;
import com.cse3310.phms.ui.cards.DoctorContactCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.UserSingleton;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.contact_screen)
public class ContactScreenFragment extends SherlockFragment {
    private static final String[] TABS = new String[] { "DoctorInfo", "Emergency" };
    private CardListFragment_ mCardListFragment;
    private List<Card> mDoctorCardList  = new ArrayList<Card>();
    private List<Card> mContactCardList = new ArrayList<Card>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    // add this to be able to add other icon to the action bar menu
        EventBus.getDefault().registerSticky(this);
        populateCardLists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentManager fm = getChildFragmentManager();
        final TabsIndicatorFragment tabsIndicatorFragment = TabsIndicatorFragment.newInstance(new ContactScreenAdapter(fm));
        mCardListFragment = CardListFragment_.newInstance(mDoctorCardList, true);

        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.contact_screen_tabs_container, tabsIndicatorFragment);
        transaction.add(R.id.contact_screen_content_container, mCardListFragment);
        transaction.commit();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);    // add the add icon to the action bar menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void populateCardLists() {
        User user = UserSingleton.INSTANCE.getCurrentUser();
        mDoctorCardList.clear();
        for (DoctorInfo doctorInfo : user.getDoctors()) {
            mDoctorCardList.add(new DoctorContactCard(getActivity(), doctorInfo));
        }

        mContactCardList.clear();
        for (Info contact : user.getContacts()) {
            mContactCardList.add(new ContactCard(getActivity(), contact));
        }
    }

    //===============================================================
    //                      EventBus Listeners
    //===============================================================
    public void onEvent(Events.SwitchTabEvent event) {
        populateCardLists();

        mCardListFragment.clearCards();
        if (event.position == 0) {
            mCardListFragment.addCards(mDoctorCardList);
        } else {
            mCardListFragment.addCards(mContactCardList);
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.detach(mCardListFragment);
        transaction.attach(mCardListFragment);
        transaction.commit();
    }

    //===============================================================
    //                      INNER Adapter Class
    //===============================================================
    private class ContactScreenAdapter extends FragmentPagerAdapter {
        public ContactScreenAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                return BlankFragment.newInstance(R.color.transparent);
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }
    }
}
