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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class EditEntryCreditCardActivity extends AppCompatActivity {

    private static final String EXTRA_ENTRY_ID =
            "com.sibich.myapplication_pass_manager_entry_id";
    private static final String KEY_STATE_OF_PIN_SYMBOLS = "state_of_pin_symbols";
    private String mPass = "";

    private boolean mIsOpenPinSymbols = false;

    private EditText mTitleEditTextView, mNameEditTextView, mNumberEditTextView,
            mDateCardEditTextView, mCVVEditTextView, mPinCodeEditTextView;
    private ImageButton mPinOpenSymbolsImageButton;

    private EntryCreditCard mEntry;
    private EntryLab mEntryLab = EntryLab.get(this);

    public static Intent newIntent(Context packageContext, UUID entryId) {
        Intent intent = new Intent(packageContext, EditEntryCreditCardActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, entryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editEntryCreditCard);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        UUID entryId = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        mEntry = mEntryLab.getEntryCreditCards(entryId);

        mTitleEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_title_editText);
        mTitleEditTextView.setText(mEntry.getTitle());

        mNameEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_name_editText);

        mNumberEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_number_editText);

        mDateCardEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_dateCard_editText);

        mCVVEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_cvv_editText);

        mPinCodeEditTextView = (EditText) findViewById(R.id.edit_entry_credit_card_pin_code_editText);

        mPinOpenSymbolsImageButton = (ImageButton) findViewById(R.id.edit_entry_credit_card_pin_code_open_symbol_imageButton);

        if (savedInstanceState != null) {
            mIsOpenPinSymbols = savedInstanceState.getBoolean(KEY_STATE_OF_PIN_SYMBOLS, false);
        }
        if (mIsOpenPinSymbols) {
            mPinCodeEditTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
            mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            mPinCodeEditTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }

        mPinOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPinSymbols) {
                    mPinCodeEditTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPinSymbols = true;
                }
                else {
                    mPinCodeEditTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPinSymbols = false;
                }
            }
        });

        mPass = MasterPass.getMasterPass();

        String decryptedName = "", decryptedNumber = "",
                decryptedDateCard = "", decryptedCVV = "", decryptedPinCode = "";
        if (!mEntry.getName().isEmpty()) {
            decryptedName = AesCrypt.decrypt(mEntry.getName(), mPass);
        }
        if (!mEntry.getNumber().isEmpty()) {
            decryptedNumber = AesCrypt.decrypt(mEntry.getNumber(), mPass);
        }
        if (!mEntry.getDate_card().isEmpty()) {
            decryptedDateCard = AesCrypt.decrypt(mEntry.getDate_card(), mPass);
        }
        if (!mEntry.getCVV().isEmpty()) {
            decryptedCVV = AesCrypt.decrypt(mEntry.getCVV(), mPass);
        }
        if (!mEntry.getPin_code().isEmpty()) {
            decryptedPinCode = AesCrypt.decrypt(mEntry.getPin_code(), mPass);
        }

        mNameEditTextView.setText(decryptedName);
        mNumberEditTextView.setText(decryptedNumber);
        mDateCardEditTextView.setText(decryptedDateCard);
        mCVVEditTextView.setText(decryptedCVV);
        mPinCodeEditTextView.setText(decryptedPinCode);


    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_STATE_OF_PIN_SYMBOLS, mIsOpenPinSymbols);
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

                String encryptedName = "", encryptedNumber = "",
                        encryptedDateCard = "", encryptedCVV = "", encryptedPinCode = "";
                if (!mNameEditTextView.getText().toString().isEmpty()) {
                    encryptedName = AesCrypt.encrypt(mNameEditTextView.getText().toString(), mPass);
                }
                if (!mNumberEditTextView.getText().toString().isEmpty()) {
                    encryptedNumber = AesCrypt.encrypt(mNumberEditTextView.getText().toString(), mPass);
                }
                if (!mDateCardEditTextView.getText().toString().isEmpty()) {
                    encryptedDateCard = AesCrypt.encrypt(mDateCardEditTextView.getText().toString(), mPass);
                }
                if (!mCVVEditTextView.getText().toString().isEmpty()) {
                    encryptedCVV = AesCrypt.encrypt(mCVVEditTextView.getText().toString(), mPass);
                }
                if (!mPinCodeEditTextView.getText().toString().isEmpty()) {
                    encryptedPinCode = AesCrypt.encrypt(mPinCodeEditTextView.getText().toString(), mPass);
                }

                mEntry.setName(encryptedName);
                mEntry.setNumber(encryptedNumber);
                mEntry.setDate_card(encryptedDateCard);
                mEntry.setCVV(encryptedCVV);
                mEntry.setPin_code(encryptedPinCode);

                onBackPressed();
                Toast.makeText(EditEntryCreditCardActivity.this, getResources().getString(R.string.Entry_saved), Toast.LENGTH_SHORT).show();
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
