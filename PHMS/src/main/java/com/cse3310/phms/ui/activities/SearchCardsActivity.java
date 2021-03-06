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

package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.cse3310.phms.R;
import com.cse3310.phms.ui.fragments.SearchScreenFragment_;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.utils.Keyboard;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;

import java.util.*;

public class SearchCardsActivity extends BaseActivity{
    private List<Card> mCardMatchList = new ArrayList<Card>();
    private Collection<Card> mCardsToSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        setTitle("Search");

        // get a set of unique card title
        Set<String> suggestionsSet = new HashSet<String>(mCardsToSearch.size());
        for (Card card : mCardsToSearch) {
            String title = card.getTitle();
            suggestionsSet.add(title);

            if (title.toLowerCase().contains(mSearchWord.toLowerCase())) { // match cards with search word
                mCardMatchList.add(card);
            }
        }
        setSuggestions(suggestionsSet); // set search suggestions

        SearchScreenFragment_ searchScreenFragment = new SearchScreenFragment_();
        searchScreenFragment.setMatchCardList(mCardMatchList);  // set the cards that match the search word to be display
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_front_container, searchScreenFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mSearchMenuItem.expandActionView();
        mAutoCompTextView.setText(mSearchWord);
        mAutoCompTextView.clearFocus();
        mAutoCompTextView.dismissDropDown();
        Keyboard.hide(mInputManager, mAutoCompTextView);
        return true;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //===========================================
    //              EventBus Listener
    //===========================================
    // set the card list that will be searched
    public void onEvent(Events.initCardsToSearchEvent event) {
        this.mCardsToSearch = event.cardsToSearch;
    }
    // set the search word that will be used to look for match cards
    public void onEvent(Events.initSearchWordEvent event) {
        super.mSearchWord = event.searchWord;
    }
}
