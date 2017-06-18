package com.sibich.myapplication_pass_manager;

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

public class NewEntryCreditCardActivity extends AppCompatActivity {

    private static final String KEY_STATE_OF_PIN_SYMBOLS = "state_of_pin_symbols";
    private String mPass = "";

    private boolean mIsOpenPinSymbols = false;

    private EditText mTitleEditTextView, mNameEditTextView,
            mNumberEditTextView, mDateCardEditTextView, mCVVEditTextView,
            mPinCodeEditTextView;

    private ImageButton mPinOpenSymbolsImageButton;

    private EntryLab mEntryLab = EntryLab.get(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_newEntryCreditCard);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mTitleEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_title_editText);

        mNameEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_name_editText);

        mNumberEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_number_editText);

        mDateCardEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_dateCard_editText);

        mDateCardEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_dateCard_editText);

        mCVVEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_cvv_editText);

        mPinCodeEditTextView = (EditText) findViewById(R.id.new_entry_credit_card_pin_code_editText);

        mPinOpenSymbolsImageButton = (ImageButton) findViewById(R.id.new_entry_credit_card_pin_code_open_symbol_imageButton);

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

                mEntryLab.addEntryCreditCards(mTitleEditTextView.getText().toString(),
                        encryptedName,
                        encryptedNumber,
                        encryptedDateCard,
                        encryptedCVV,
                        encryptedPinCode
                        );

                onBackPressed();
                Toast.makeText(NewEntryCreditCardActivity.this, getResources().getString(R.string.Created_new_entry),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                View parentLayout = findViewById(R.id.root_view_new_entry_web_site);
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
