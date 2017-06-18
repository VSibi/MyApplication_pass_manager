package com.sibich.myapplication_pass_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class EditEntryWebsiteActivity extends AppCompatActivity {

    private static final String EXTRA_ENTRY_ID =
            "com.sibich.myapplication_pass_manager_entry_id";
    private static final String KEY_STATE_OF_PASS_SYMBOLS = "state_of_pass_symbols";
    private String mPass = "";

    private boolean mIsOpenPassSymbols = false;

    private EditText mTitleEditTextView, mUserNameEditTextView, mPasswordEditTextView,
            mWebSiteEditTextView;
    private Button mGenPassButton;
    private ImageButton mPassOpenSymbolsImageButton;

    private EntryWebSite mEntry;
    private EntryLab mEntryLab = EntryLab.get(this);

    public static Intent newIntent(Context packageContext, UUID entryId) {
        Intent intent = new Intent(packageContext, EditEntryWebsiteActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, entryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_web_site);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editEntryWebSite);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        UUID entryId = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        mEntry = mEntryLab.getEntryWebSites(entryId);

        mTitleEditTextView = (EditText) findViewById(R.id.edit_entry_web_site_title_editText);
        mTitleEditTextView.setText(mEntry.getTitle());

        mUserNameEditTextView = (EditText) findViewById(R.id.edit_entry_web_site_username_editText);

        mPasswordEditTextView = (EditText) findViewById(R.id.edit_entry_web_site_password_editText);

        mPassOpenSymbolsImageButton =
                (ImageButton) findViewById(R.id.edit_entry_web_site_password_open_symbol_imageButton);

        if (savedInstanceState != null) {
            mIsOpenPassSymbols = savedInstanceState.getBoolean(KEY_STATE_OF_PASS_SYMBOLS, false);
        }
        if (mIsOpenPassSymbols) {
            mPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            mPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }

        mPassOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPassSymbols) {
                    mPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPassSymbols = true;
                }
                else {
                    mPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPassSymbols = false;
                }
            }
        });

        mGenPassButton = (Button) findViewById(R.id.edit_entry_web_site_gen_passButton);
        mGenPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassGenerator passGen = new PassGenerator();
                String pass = passGen.generate(16);
                mPasswordEditTextView.setText(pass);
                Snackbar.make(view, getResources().getString(R.string.pass_success_gen), Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        mWebSiteEditTextView = (EditText) findViewById(R.id.edit_entry_web_site_website_editText);

        mPass = MasterPass.getMasterPass();

        String decryptedUserName = "", decryptedPassword = "", decryptedWebSite = "";
        if (!mEntry.getUserName().isEmpty()) {
            decryptedUserName = AesCrypt.decrypt(mEntry.getUserName(), mPass);
        }
        if (!mEntry.getPassword().isEmpty()) {
            decryptedPassword = AesCrypt.decrypt(mEntry.getPassword(), mPass);
        }
        if (!mEntry.getWebSite().isEmpty()) {
            decryptedWebSite = AesCrypt.decrypt(mEntry.getWebSite(), mPass);
        }

        mUserNameEditTextView.setText(decryptedUserName);
        mPasswordEditTextView.setText(decryptedPassword);
        mWebSiteEditTextView.setText(decryptedWebSite);
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_STATE_OF_PASS_SYMBOLS, mIsOpenPassSymbols);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_save) {

            if (!mTitleEditTextView.getText().toString().equals("")) {
                mEntry.setTitle(mTitleEditTextView.getText().toString());
                mEntry.setDate(new Date());

                String encryptedUserName = "", encryptedPassword = "", encryptedWebSite = "";
                if (!mUserNameEditTextView.getText().toString().isEmpty()) {
                    encryptedUserName = AesCrypt.encrypt(mUserNameEditTextView.getText().toString(), mPass);
                }

                if (!mPasswordEditTextView.getText().toString().isEmpty()) {
                    encryptedPassword = AesCrypt.encrypt(mPasswordEditTextView.getText().toString(), mPass);
                }

                if (!mWebSiteEditTextView.getText().toString().isEmpty()) {
                    encryptedWebSite = AesCrypt.encrypt(mWebSiteEditTextView.getText().toString(), mPass);
                }

                mEntry.setUserName(encryptedUserName);
                mEntry.setPassword(encryptedPassword);
                mEntry.setWebSite(encryptedWebSite);

                onBackPressed();
                Toast.makeText(EditEntryWebsiteActivity.this, getResources().getString(R.string.Entry_saved), Toast.LENGTH_SHORT).show();
            } else {
                View parentLayout = findViewById(R.id.root_view_edit_card);
                Snackbar.make(parentLayout, getResources().getString(R.string.title_error), Snackbar.LENGTH_LONG)
                        .show();
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPass = MasterPass.getMasterPass();
    }

}
