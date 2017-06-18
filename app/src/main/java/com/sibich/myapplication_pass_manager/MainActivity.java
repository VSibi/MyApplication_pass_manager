package com.sibich.myapplication_pass_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.sibich.myapplication_pass_manager.database.MyPassDbSaveLoad;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sibic_000 on 09.05.2017.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener,
        ConfirmDeletionDialogFragment.NoticeDialogListener {

    private static final String KEY_STATE_OF_CURR_SELECTED_ENTRIES = "state_of_curr_selected_entries";

    CurrentSelectedEntries currentSelectedEntries = CurrentSelectedEntries.WEB_SITES;

    private UUID mCurrentEntryId;

    private static final String DIALOG_CONFIRM_DELETE = "DialogConfirmDelete";

    private RecyclerView mEntryRecyclerView;
    private EntryAdapter mAdapter;

    private ImageButton mToolbarSortImageButton;
    private boolean mIsCheckedSortByDate = true;

    private TextView mToolBarSubtitleTextView;
    private long mBack_pressed;

    private MyPassDbSaveLoad dbSaveLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbSaveLoad = new MyPassDbSaveLoad(this);

        mToolBarSubtitleTextView = (TextView) findViewById(R.id.toolbar_subtitle_textView);

        mToolbarSortImageButton = (ImageButton) findViewById(R.id.toolbar_sort_imageButton);

        mEntryRecyclerView = (RecyclerView) findViewById(R.id.entry_recycler_view);
        mEntryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState != null) {
            currentSelectedEntries = (CurrentSelectedEntries) savedInstanceState.getSerializable(KEY_STATE_OF_CURR_SELECTED_ENTRIES);
        }

        if (currentSelectedEntries != null) {
            switch (currentSelectedEntries) {
                case WEB_SITES:
                    updateUI(EntryLab.get(MainActivity.this).getEntriesWebSites());
                    break;
                case CREDIT_CARDS:
                    updateUI(EntryLab.get(MainActivity.this).getEntriesCreditCards());
                    break;
                default:
                    updateUI(EntryLab.get(MainActivity.this).getEntriesWebSites());
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                switch (currentSelectedEntries) {
                    case WEB_SITES:
                        i = new Intent(MainActivity.this, NewEntryWebSiteActivity.class);
                        startActivity(i);
                        break;
                    case CREDIT_CARDS:
                        i = new Intent(MainActivity.this, NewEntryCreditCardActivity.class);
                        startActivity(i);
                        break;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(KEY_STATE_OF_CURR_SELECTED_ENTRIES, currentSelectedEntries);
    }

    private void updateUI( List<Entry> entries) {
        if (mAdapter == null) {
            mAdapter = new EntryAdapter(entries);
            mEntryRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class EntryHolder extends RecyclerView.ViewHolder {
        private TextView mEntryAvatarTextView, mEntryTitleTextView, mEntrySubTitleView;
        private Button mEntryLearnMoreButton, mEntryDeleteButton;
        private Entry mEntry;


        public EntryHolder(View itemView) {
            super(itemView);
            mEntryAvatarTextView = (TextView) itemView.findViewById(R.id.avatar_entryTextView);
            mEntryTitleTextView = (TextView) itemView.findViewById(R.id.list_item_entry_title_textView);
            mEntrySubTitleView = (TextView) itemView.findViewById(R.id.list_item_entry_subtitle_textView);
            mEntryLearnMoreButton = (Button) itemView.findViewById(R.id.list_item_entry_learn_more_Button);
            mEntryDeleteButton = (Button) itemView.findViewById(R.id.list_item_entry_delete_Button);
        }

        public void bindEntry(Entry entry) {
            mEntry = entry;
            String avatarText = mEntry.getTitle().substring(0, 1).toUpperCase();

            mEntryAvatarTextView.setText(avatarText);
            switch (currentSelectedEntries) {
                case WEB_SITES:
                    mEntryAvatarTextView.setBackgroundResource(R.drawable.avatar_entry);
                    break;
                case CREDIT_CARDS:
                    mEntryAvatarTextView.setBackgroundResource(R.drawable.avatar_blue100_entry);
                    break;
                default:
                    mEntryAvatarTextView.setBackgroundResource(R.drawable.avatar_entry);
            }

            mEntryTitleTextView.setText(mEntry.getTitle());
            String subTitle = getResources().getString(R.string.last_updated) + " " +
                    DateFormat.getDateTimeInstance().format(mEntry.getDate());
            mEntrySubTitleView.setText(subTitle);

            mEntryLearnMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i;
                    switch (currentSelectedEntries) {
                        case WEB_SITES:
                            i = EntryWebSiteActivity.newIntent(MainActivity.this, mEntry.getId());
                            startActivity(i);
                            break;
                        case CREDIT_CARDS:
                            i = EntryCreditCardActivity.newIntent(MainActivity.this, mEntry.getId());
                            startActivity(i);
                            break;
                    }
                }
            });

            mEntryDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCurrentEntryId = mEntry.getId();
                    FragmentManager manager = getSupportFragmentManager();
                    ConfirmDeletionDialogFragment dialog = new ConfirmDeletionDialogFragment();
                    dialog.show(manager, DIALOG_CONFIRM_DELETE);
                }
            });
        }
    }

    private class EntryAdapter extends RecyclerView.Adapter<EntryHolder> {
        private List<Entry> mEntries;

        public EntryAdapter(List<Entry> entries) {
            mEntries = entries;
        }

        @Override
        public EntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater
                    .inflate(R.layout.list_item_entry, parent, false);
            return new EntryHolder(view);
        }

        @Override
        public void onBindViewHolder(EntryHolder holder, int position) {
            Entry entry = mEntries.get(position);
            holder.bindEntry(entry);
        }

        @Override
        public int getItemCount() {
            return mEntries.size();
        }
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDeleteEntryDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        switch (currentSelectedEntries) {
            case WEB_SITES:
                EntryLab.get(MainActivity.this).deleteEntryWebSites(mCurrentEntryId);
                mAdapter.notifyDataSetChanged();
                break;
            case CREDIT_CARDS:
                EntryLab.get(MainActivity.this).deleteEntryCreditCards(mCurrentEntryId);
                mAdapter.notifyDataSetChanged();
                break;
        }
        View parentLayout = findViewById(R.id.main_activity_drawer_layout);
        Snackbar.make(parentLayout, getResources().getString(R.string.entry_was_deleted), Snackbar.LENGTH_LONG)
                .show();
    }

    private void dbSaveEntries() {
        if (!dbSaveLoad.saveEntries()) {
            View parentLayout = findViewById(R.id.main_activity_drawer_layout);
            Snackbar.make(parentLayout, getResources().getString(R.string.Entries_not_save), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (mBack_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.Press_again), Toast.LENGTH_SHORT).show();
            }
            mBack_pressed = System.currentTimeMillis();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showPopup(View v) {
        final PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.context_menu_sort_entries, popup.getMenu());
        if (mIsCheckedSortByDate) {
            popup.getMenu().findItem(R.id.menu_sort_alphabetical).setChecked(false);
            popup.getMenu().findItem(R.id.menu_sort_by_date).setChecked(true);
        }
        else {
            popup.getMenu().findItem(R.id.menu_sort_by_date).setChecked(false);
            popup.getMenu().findItem(R.id.menu_sort_alphabetical).setChecked(true);
        }

        popup.show();

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                mIsCheckedSortByDate = popup.getMenu().findItem(R.id.menu_sort_by_date).isChecked();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        View parentLayout = findViewById(R.id.main_activity_drawer_layout);
        switch (item.getItemId()) {
            case R.id.menu_sort_alphabetical:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Collections.sort(currSelectedEntries(), new SortEntriesAlphabetical());
                updateUI(currSelectedEntries());
                Snackbar.make(parentLayout, getResources().getString(R.string.entries_was_sorted_alphabetical), Snackbar.LENGTH_LONG)
                        .show();
                return true;
            case R.id.menu_sort_by_date:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Collections.sort(currSelectedEntries(), new SortEntriesByDate());
                updateUI(currSelectedEntries());
                Snackbar.make(parentLayout, getResources().getString(R.string.entries_was_sorted_by_date), Snackbar.LENGTH_LONG)
                        .show();
                return true;
            default:
                return false;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_websites) {
            // Handle the websites action
            currentSelectedEntries = CurrentSelectedEntries.WEB_SITES;
            mToolBarSubtitleTextView.setText(R.string.WebSites);

            if(mIsCheckedSortByDate) {
                Collections.sort(currSelectedEntries(), new SortEntriesByDate());
            } else {
                Collections.sort(currSelectedEntries(), new SortEntriesAlphabetical());
            }

            mAdapter = null;
            updateUI(currSelectedEntries());

        } else if (id == R.id.nav_credit_cards) {
            currentSelectedEntries = CurrentSelectedEntries.CREDIT_CARDS;
            mToolBarSubtitleTextView.setText(R.string.CreditCards);

            if(mIsCheckedSortByDate) {
                Collections.sort(currSelectedEntries(), new SortEntriesByDate());
            } else {
                Collections.sort(currSelectedEntries(), new SortEntriesAlphabetical());
            }

            mAdapter = null;
            updateUI(currSelectedEntries());

        } else if (id == R.id.nav_change_masterPass) {
            Intent i = new Intent(MainActivity.this, ChangeMasterPassActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_Exit) {
            dbSaveEntries();
            System.exit(0);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Entry> currSelectedEntries() {
        switch (currentSelectedEntries) {
            case WEB_SITES:
                return EntryLab.get(MainActivity.this).getEntriesWebSites();
            case CREDIT_CARDS:
                return EntryLab.get(MainActivity.this).getEntriesCreditCards();
            default:
                return EntryLab.get(MainActivity.this).getEntriesWebSites();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mIsCheckedSortByDate = sharedPreferences.getBoolean("IsCheckedSortByDate", true);
        if (mIsCheckedSortByDate) {
            Collections.sort(currSelectedEntries(), new SortEntriesByDate());
        }
        else {
            Collections.sort(currSelectedEntries(), new SortEntriesAlphabetical());
        }
        updateUI(currSelectedEntries());
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("IsCheckedSortByDate", mIsCheckedSortByDate);
        edit.apply();

    }

    @Override
    public void onStop() {
        super.onStop();
        dbSaveEntries();
    }
}
