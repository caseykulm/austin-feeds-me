package com.example.utfeedsme.addeditevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.utfeedsme.AustinFeedsMeApplication;
import com.example.utfeedsme.R;
import com.example.utfeedsme.addeditevent.AddEditEventContract.View;
import com.example.utfeedsme.data.Event;
import com.example.utfeedsme.data.EventsDataSource;
import com.example.utfeedsme.data.EventsRepository;
import com.firebase.client.Firebase;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by darrankelinske on 4/13/16.
 */
public class AddEditEventActivity extends AppCompatActivity implements View {
    @Inject Firebase firebase;
    @Inject EventsRepository repository;

    @Bind(R.id.title_addeditevent_edittext)
    EditText title;
    @Bind(R.id.description_addeditevent_edittext)
    EditText description;
    @Bind(R.id.save_addeditevent_button)
    Button saveButton;
    @Bind(R.id.add_edit_event_linear_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AustinFeedsMeApplication) getApplication()).component().inject(this);
        setContentView(R.layout.activity_addeditevent);
        ButterKnife.bind(this);

        saveButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                if (null != firebase.getAuth()) {
                    repository.saveEvent(new Event(firebase.getAuth().getUid(),
                            title.getText().toString(),
                            description.getText().toString()), new EventsDataSource.SaveEventCallback() {
                        @Override
                        public void onEventSaved(boolean success) {
                            Log.i("Woo", "Yay the event was saved: " + success);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                } else {
                    Snackbar.make(linearLayout,
                            "You must be logged in to save an event", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void showEmptyTaskError() {

    }

    @Override
    public void showEventsList() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }
}
