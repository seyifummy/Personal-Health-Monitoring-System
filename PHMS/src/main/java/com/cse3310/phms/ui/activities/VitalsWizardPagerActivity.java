package com.cse3310.phms.ui.activities;

import android.os.Bundle;
import com.cse3310.phms.model.Vitals;
import com.cse3310.phms.ui.cards.VitalsCard;
import com.cse3310.phms.ui.utils.Events;
import com.cse3310.phms.ui.views.VitalsWizardModel;
import de.greenrobot.event.EventBus;

import static co.juliansuarez.libwizardpager.wizard.model.Page.SIMPLE_DATA_KEY;
import static com.cse3310.phms.ui.views.VitalsWizardModel.DOSAGE_KEY;
import static com.cse3310.phms.ui.views.VitalsWizardModel.NAME_KEY;

/**
 * Created by E&N on 4/20/2014.
 */
public class VitalsWizardPagerActivity extends BaseWizardPagerActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String actionBarTile = super.editMode ? "Edit Vitals" : "Add New Vitals";
        getActionBar().setTitle(actionBarTile);
    }

    @Override
    public void onSetupWizardModel() {
        super.mWizardModel = new VitalsWizardModel(this);
    }

    @Override
    public void onSubmit() {
        // extract all the information from the process and
        // use it to create a new vitals object.
        String name = onGetPage(NAME_KEY).getData().getString(SIMPLE_DATA_KEY);
        double information  = Double.parseDouble(onGetPage(DOSAGE_KEY).getData().getString(SIMPLE_DATA_KEY));

        // create the vitals object
        Vitals vitals = new Vitals();
        //vitals.setVitalsName(information);

        // if editing, remove the old card and add the new edited card
        if (super.editMode) {
            VitalsCard oldVitals = ((VitalsWizardModel) mWizardModel).getVitalsCard();
            // post an event to DietScreenFragment to remove the old card
            EventBus.getDefault().post(new Events.RemoveVitalsCardEvent(oldVitals));
        }

        // post an event to DietScreenFragment to add a new card
        EventBus.getDefault().post(new Events.AddVitalsCardEvent(new VitalsCard(this, vitals)));
        finish();
    }
}