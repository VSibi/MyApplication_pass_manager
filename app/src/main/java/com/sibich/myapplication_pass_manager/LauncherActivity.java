package com.sibich.myapplication_pass_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sibich.myapplication_pass_manager.database.MyPassDbSaveLoad;

/**
 * Created by Sibic_000 on 09.05.2017.
 */
public class LauncherActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Boolean> {
    public static final int LOADER_RANDOM_ID = 1;
    private String mMasterPass = "";
    private MyPassDbSaveLoad dbSaveLoad;
    private EditText mPasswordEditTextView, mConfirmPassEditTextView;
    private boolean mIsOpenPassSymbols = false;
    private boolean mIsFirstStartApp;
    private Button mLogInButton, mRegisterButton;
    private ImageButton mPassOpenSymbolsImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        getSupportLoaderManager().initLoader(LOADER_RANDOM_ID, null, this);

        dbSaveLoad = new MyPassDbSaveLoad(LauncherActivity.this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mIsFirstStartApp = sharedPreferences.getBoolean("IsFirstStartApp", true);

        mPasswordEditTextView = (EditText) findViewById(R.id.launcher_password_editText);
        mConfirmPassEditTextView = (EditText) findViewById(R.id.launcher_confirm_pass_editText);
        mPassOpenSymbolsImageButton = (ImageButton) findViewById(R.id.launcher_password_open_symbol_imageButton);


        if (mIsOpenPassSymbols) {
            mPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mConfirmPassEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            mPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mConfirmPassEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);

        }

        mPassOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPassSymbols) {
                    mPasswordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mConfirmPassEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPassSymbols = true;
                } else {
                    mPasswordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mConfirmPassEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPassSymbols = false;
                }
            }
        });

        mLogInButton = (Button) findViewById(R.id.launcher_logIn_Button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMasterPass = mPasswordEditTextView.getText().toString();
                String loadDBPass = dbSaveLoad.loadPass();
                try {
                    String decryptedPass = AesCrypt.decrypt(loadDBPass, mMasterPass);
                    if (mMasterPass.equals(decryptedPass)) {
                        MasterPass.setMasterPass(mMasterPass);
                        Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (IllegalArgumentException e) {
                    Snackbar.make(view, getResources().getString(R.string.Empty_password), Snackbar.LENGTH_LONG)
                            .show();
                } catch (RuntimeException e) {
                    Snackbar.make(view, getResources().getString(R.string.Incorrect_password), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mRegisterButton = (Button) findViewById(R.id.launcher_register_Button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mConfirmPassEditTextView.getVisibility() == View.VISIBLE) {
                    mMasterPass = mPasswordEditTextView.getText().toString();
                    if (mMasterPass.length() > 6) {
                        if (mMasterPass.equals(mConfirmPassEditTextView.getText().toString())) {
                          //  MasterPass.setMasterPass(mMasterPass);
                            try {
                                String encryptedPass = AesCrypt.encrypt(mMasterPass, mMasterPass);
                                if (dbSaveLoad.savePass(encryptedPass)) {

                                    MasterPass.setMasterPass(mMasterPass);
                                    mIsFirstStartApp = false;
                                    SharedPreferences sharedPreferences = PreferenceManager
                                            .getDefaultSharedPreferences(LauncherActivity.this);
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putBoolean("IsFirstStartApp", mIsFirstStartApp);
                                    edit.apply();

                                    Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                                    startActivity(i);
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
                        Snackbar.make(view, getResources().getString(R.string.Pass_short), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });

        if (mIsFirstStartApp) {
            mLogInButton.setVisibility(View.GONE);
            mConfirmPassEditTextView.setVisibility(View.VISIBLE);
            mRegisterButton.setVisibility(View.VISIBLE);
        } else {
            mLogInButton.setVisibility(View.VISIBLE);
            mConfirmPassEditTextView.setVisibility(View.GONE);
            mRegisterButton.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new DataDBLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

}
