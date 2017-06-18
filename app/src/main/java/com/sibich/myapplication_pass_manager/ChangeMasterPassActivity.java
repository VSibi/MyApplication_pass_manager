package com.sibich.myapplication_pass_manager;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sibich.myapplication_pass_manager.database.MyPassDbSaveLoad;

import java.util.List;

/**
 * Created by Sibic_000 on 25.05.2017.
 */
public class ChangeMasterPassActivity extends AppCompatActivity {
    private String mOldMasterPass = "", mNewMasterPass = "";
    private MyPassDbSaveLoad dbSaveLoad;
    private EditText mOldPasswordEditTextView, mNewPasswordEditTextView, mConfirmNewPassEditTextView;
    private boolean mIsOpenPassSymbols = false;
    private Button mChangeButton;
    private ImageButton mPassOpenSymbolsImageButton;

    private EntryLab mEntryLab = EntryLab.get(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_master_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        dbSaveLoad = new MyPassDbSaveLoad(ChangeMasterPassActivity.this);

        mOldPasswordEditTextView = (EditText) findViewById(R.id.change_master_pass_old_password_editText);
        mNewPasswordEditTextView = (EditText) findViewById(R.id.change_master_pass_new_pass_editText);
        mConfirmNewPassEditTextView = (EditText) findViewById(R.id.change_master_pass_confirm_new_pass_editText);
        mPassOpenSymbolsImageButton = (ImageButton) findViewById(R.id.change_master_pass_password_open_symbol_imageButton);


        if (mIsOpenPassSymbols) {
            mOldPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mNewPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mConfirmNewPassEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            mOldPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mNewPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mConfirmNewPassEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);

        }

        mPassOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPassSymbols) {
                    mOldPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mNewPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mConfirmNewPassEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPassSymbols = true;
                } else {
                    mOldPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mNewPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mConfirmNewPassEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPassSymbols = false;
                }
            }
        });

        mChangeButton = (Button) findViewById(R.id.change_master_pass_change_Button);
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOldMasterPass = mOldPasswordEditTextView.getText().toString();
                    String loadDBPass = dbSaveLoad.loadPass();
                    try {
                        String decryptedPass = AesCrypt.decrypt(loadDBPass, mOldMasterPass);
                        if (mOldMasterPass.equals(decryptedPass)) {
                            mNewMasterPass = mNewPasswordEditTextView.getText().toString();
                            if(mNewMasterPass.length() > 6) {
                                if (mNewMasterPass.equals(mConfirmNewPassEditTextView.getText().toString())) {
                                    try {
                                        String encryptedPass = AesCrypt.encrypt(mNewMasterPass, mNewMasterPass);
                                        if(reEncodingAllEntries()) {
                                            if (dbSaveLoad.savePass(encryptedPass)) {
                                                MasterPass.setMasterPass(mNewMasterPass);
                                                onBackPressed();
                                                Toast.makeText(ChangeMasterPassActivity.this, getResources().getString(R.string.Pass_updated),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (IllegalArgumentException e) {
                                        Snackbar.make(view, getResources().getString(R.string.Empty_password), Snackbar.LENGTH_LONG)
                                                .show();
                                    } catch (RuntimeException e) {
                                        Snackbar.make(view, getResources().getString(R.string.Error_save_pass), Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                } else {
                                    Snackbar.make(view, getResources().getString(R.string.Confirm_not_pass), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            } else {
                                Snackbar.make(view, getResources().getString(R.string.New_pass_short), Snackbar.LENGTH_LONG)
                                        .show();
                            }

                        }
                    } catch (IllegalArgumentException e) {
                        Snackbar.make(view, getResources().getString(R.string.Empty_password), Snackbar.LENGTH_LONG)
                                .show();
                    } catch (RuntimeException e) {
                        Snackbar.make(view, getResources().getString(R.string.Incorrect_old_password), Snackbar.LENGTH_LONG)
                                .show();
                    }
            }
        });

    }

    private boolean reEncodingAllEntries() {

        List<Entry> entriesWebSites = mEntryLab.getEntriesWebSites();
        List<Entry> entriesCreditCards = mEntryLab.getEntriesCreditCards();

        View parentLayout = findViewById(R.id.root_view_change_master_pass);
        try {

            for (int i = 0; i < entriesWebSites.size(); i++) {
                EntryWebSite entry = (EntryWebSite) entriesWebSites.get(i);

                if(!entry.getUserName().equals("")) {
                    String decryptedUserName = AesCrypt.decrypt(entry.getUserName(), mOldMasterPass);
                    String encryptedUserName = AesCrypt.encrypt(decryptedUserName, mNewMasterPass);
                    entry.setUserName(encryptedUserName);
                }

                if (!entry.getPassword().equals("")) {
                    String decryptedPassword = AesCrypt.decrypt(entry.getPassword(), mOldMasterPass);
                    String encryptedPassword = AesCrypt.encrypt(decryptedPassword, mNewMasterPass);
                    entry.setPassword(encryptedPassword);
                }

                if (!entry.getWebSite().equals("")) {
                    String decryptedWebSite = AesCrypt.decrypt(entry.getWebSite(), mOldMasterPass);
                    String encryptedWebSite = AesCrypt.encrypt(decryptedWebSite, mNewMasterPass);
                    entry.setWebSite(encryptedWebSite);
                }
            }

            for (int i = 0; i < entriesCreditCards.size(); i++) {
                EntryCreditCard entry = (EntryCreditCard) entriesCreditCards.get(i);

                if (!entry.getName().equals("")) {
                    String decryptedName = AesCrypt.decrypt(entry.getName(), mOldMasterPass);
                    String encryptedName = AesCrypt.encrypt(decryptedName, mNewMasterPass);
                    entry.setName(encryptedName);
                }

                if (!entry.getNumber().equals("")) {
                    String decryptedNumber = AesCrypt.decrypt(entry.getNumber(), mOldMasterPass);
                    String encryptedNumber = AesCrypt.encrypt(decryptedNumber, mNewMasterPass);
                    entry.setNumber(encryptedNumber);
                }

                if (!entry.getDate_card().equals("")) {
                    String decryptedDateCard = AesCrypt.decrypt(entry.getDate_card(), mOldMasterPass);
                    String encryptedDateCard = AesCrypt.encrypt(decryptedDateCard, mNewMasterPass);
                    entry.setDate_card(encryptedDateCard);
                }

                if (!entry.getCVV().equals("")) {
                    String decryptedCVV = AesCrypt.decrypt(entry.getCVV(), mOldMasterPass);
                    String encryptedCVV = AesCrypt.encrypt(decryptedCVV, mNewMasterPass);
                    entry.setCVV(encryptedCVV);
                }

                if (!entry.getPin_code().equals("")) {
                    String decryptedPinCode = AesCrypt.decrypt(entry.getPin_code(), mOldMasterPass);
                    String encryptedPinCode = AesCrypt.encrypt(decryptedPinCode, mNewMasterPass);
                    entry.setPin_code(encryptedPinCode);
                }
            }

            dbSaveLoad.saveEntries();

            return true;
        } catch (IllegalArgumentException e) {
            Snackbar.make(parentLayout, getResources().getString(R.string.Error_decrypt), Snackbar.LENGTH_LONG)
                    .show();
            return false;
        } catch (RuntimeException e) {
            Snackbar.make(parentLayout, getResources().getString(R.string.Error_Run_Time), Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

