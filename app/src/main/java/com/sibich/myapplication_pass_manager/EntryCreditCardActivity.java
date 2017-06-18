package com.sibich.myapplication_pass_manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class EntryCreditCardActivity extends AppCompatActivity
        implements ConfirmDeletionDialogFragment.NoticeDialogListener {

    private static final String DIALOG_CONFIRM_DELETE = "DialogConfirmDelete";
    private static final String EXTRA_ENTRY_ID =
            "com.sibich.myapplication_pass_manager_entry_id";
    private static final String KEY_STATE_OF_PIN_SYMBOLS = "state_of_pin_symbols";

    private boolean mIsOpenPinSymbols = false;

    private EntryCreditCard mEntry;
    private TextView mCustomerTitleTextView,
            mNameTextView, mCustomerNameTextView,
            mNumberTextView, mCustomerNumberTextView,
            mDateCardTextView, mCustomerDateCardTextView,
            mCVVTextView, mCustomerCVVTextView,
            mPinCodeTextView, mCustomerPinCodeTextView;
    private ImageButton mTitleCopyImageButton, mNameCopyImageButton, mNumberCopyImageButton,
            mPinCodeCopyImageButton, mPinOpenSymbolsImageButton,
            mDateCardCopyImageButton, mCVVCopyImageButton;
    private View mDivider_2, mDivider_3, mDivider_4, mDivider_5, mDivider_6;

    private EntryLab mEntryLab = EntryLab.get(this);

    public static Intent newIntent(Context packageContext, UUID entryId) {
        Intent intent = new Intent(packageContext, EntryCreditCardActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, entryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_EntryCreditCard);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        UUID entryId = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        mEntry = mEntryLab.getEntryCreditCards(entryId);

        mCustomerTitleTextView = (TextView) findViewById(R.id.entry_credit_card_customer_title_textView);

        mTitleCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_title_copy_imageButton);
        mTitleCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerTitleTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mNameTextView = (TextView) findViewById(R.id.entry_credit_card_name_textView);
        mCustomerNameTextView = (TextView) findViewById(R.id.entry_credit_card_customer_name_textView);

        mNameCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_name_copy_imageButton);
        mNameCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerNameTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mDivider_2 = findViewById(R.id.entry_credit_card_divider_2);

        mNumberTextView = (TextView) findViewById(R.id.entry_credit_card_number_textView);
        mCustomerNumberTextView = (TextView) findViewById(R.id.entry_credit_card_customer_number_textView);

        mNumberCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_number_copy_imageButton);
        mNumberCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerNumberTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mDivider_3 = findViewById(R.id.entry_credit_card_divider_3);

        mDateCardTextView = (TextView) findViewById(R.id.entry_credit_card_dateCard_textView);
        mCustomerDateCardTextView = (TextView) findViewById(R.id.entry_credit_card_customer_dateCard_textView);

        mDateCardCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_dateCard_copy_imageButton);
        mDateCardCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerDateCardTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mDivider_4 = findViewById(R.id.entry_credit_card_divider_4);

        mCVVTextView = (TextView) findViewById(R.id.entry_credit_card_cvv_textView);
        mCustomerCVVTextView = (TextView) findViewById(R.id.entry_credit_card_customer_cvv_textView);

        mCVVCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_cvv_copy_imageButton);
        mCVVCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerCVVTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mDivider_5 = findViewById(R.id.entry_credit_card_divider_5);

        mPinCodeTextView = (TextView) findViewById(R.id.entry_credit_card_pinCode_textView);
        mCustomerPinCodeTextView = (TextView) findViewById(R.id.entry_credit_card_customer_pinCode_textView);

        mPinCodeCopyImageButton = (ImageButton) findViewById(R.id.entry_credit_card_pinCode_copy_imageButton);
        mPinCodeCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerPinCodeTextView.getText().toString();
                if(copyToClipboard(EntryCreditCardActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mPinOpenSymbolsImageButton = (ImageButton) findViewById(R.id.entry_credit_card_pinCode_open_symbol_imageButton);

        if (savedInstanceState != null) {
            mIsOpenPinSymbols = savedInstanceState.getBoolean(KEY_STATE_OF_PIN_SYMBOLS, false);
        }

        if (mIsOpenPinSymbols) {
            mCustomerPinCodeTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        }
        else {
            mCustomerPinCodeTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }

        mPinOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPinSymbols) {
                    mCustomerPinCodeTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPinSymbols = true;
                }
                else {
                    mCustomerPinCodeTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPinOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPinSymbols = false;
                }
            }
        });

        mDivider_6 = findViewById(R.id.entry_credit_card_divider_6);

    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText("simple text", text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            View parentLayout = findViewById(R.id.root_view_entry_web_site);
            Snackbar.make(parentLayout, getResources().getString(R.string.Text_not_copied), Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_STATE_OF_PIN_SYMBOLS, mIsOpenPinSymbols);
    }




    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDeleteEntryDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        mEntryLab.deleteEntryCreditCards(mEntry.getId());
        Toast.makeText(EntryCreditCardActivity.this,
                getResources().getString(R.string.entry_was_deleted), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_entry_edit) {
            Intent intent = EditEntryCreditCardActivity.newIntent(EntryCreditCardActivity.this, mEntry.getId());
            startActivity(intent);
            return true;
        }else if( id == R.id.menu_entry_delete) {
            FragmentManager manager = getSupportFragmentManager();
            ConfirmDeletionDialogFragment dialog = new ConfirmDeletionDialogFragment();
            dialog.show(manager, DIALOG_CONFIRM_DELETE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        String pass = MasterPass.getMasterPass();

        UUID entryId = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        mEntry = mEntryLab.getEntryCreditCards(entryId);

        mCustomerTitleTextView.setText(mEntry.getTitle());

        if (!mEntry.getName().equals("")) {
            mNameTextView.setVisibility(View.VISIBLE);
            mCustomerNameTextView.setVisibility(View.VISIBLE);
            mNameCopyImageButton.setVisibility(View.VISIBLE);
            mDivider_2.setVisibility(View.VISIBLE);

            String decryptedName = AesCrypt.decrypt(mEntry.getName(), pass);
            mCustomerNameTextView.setText(decryptedName);
        }
        else {
            mNameTextView.setVisibility(View.GONE);
            mCustomerNameTextView.setVisibility(View.GONE);
            mNameCopyImageButton.setVisibility(View.GONE);
            mDivider_2.setVisibility(View.GONE);
        }

        if (!mEntry.getNumber().equals("")) {
            mNumberTextView.setVisibility(View.VISIBLE);
            mCustomerNumberTextView.setVisibility(View.VISIBLE);
            mNumberCopyImageButton.setVisibility(View.VISIBLE);
            mDivider_3.setVisibility(View.VISIBLE);

            String decryptedNumber = AesCrypt.decrypt(mEntry.getNumber(), pass);
            mCustomerNumberTextView.setText(decryptedNumber);
        }
        else {
            mNumberTextView.setVisibility(View.GONE);
            mCustomerNumberTextView.setVisibility(View.GONE);
            mNumberCopyImageButton.setVisibility(View.GONE);
            mDivider_3.setVisibility(View.GONE);
        }

        if (!mEntry.getDate_card().equals("")) {
            mDateCardTextView.setVisibility(View.VISIBLE);
            mCustomerDateCardTextView.setVisibility(View.VISIBLE);
            mDateCardCopyImageButton.setVisibility(View.VISIBLE);
            mDivider_4.setVisibility(View.VISIBLE);

            String decryptedDateCard = AesCrypt.decrypt(mEntry.getDate_card(), pass);
            mCustomerDateCardTextView.setText(decryptedDateCard);
        }
        else {
            mDateCardTextView.setVisibility(View.GONE);
            mCustomerDateCardTextView.setVisibility(View.GONE);
            mDateCardCopyImageButton.setVisibility(View.GONE);
            mDivider_4.setVisibility(View.GONE);
        }

        if (!mEntry.getCVV().equals("")) {
            mCVVTextView.setVisibility(View.VISIBLE);
            mCustomerCVVTextView.setVisibility(View.VISIBLE);
            mCVVCopyImageButton.setVisibility(View.VISIBLE);
            mDivider_5.setVisibility(View.VISIBLE);

            String decryptedCVV = AesCrypt.decrypt(mEntry.getCVV(), pass);
            mCustomerCVVTextView.setText(decryptedCVV);
        }
        else {
            mCVVTextView.setVisibility(View.GONE);
            mCustomerCVVTextView.setVisibility(View.GONE);
            mCVVCopyImageButton.setVisibility(View.GONE);
            mDivider_5.setVisibility(View.GONE);
        }

        if (!mEntry.getPin_code().equals("")) {
            mPinCodeTextView.setVisibility(View.VISIBLE);
            mCustomerPinCodeTextView.setVisibility(View.VISIBLE);
            mPinCodeCopyImageButton.setVisibility(View.VISIBLE);
            mPinOpenSymbolsImageButton.setVisibility(View.VISIBLE);
            mDivider_6.setVisibility(View.VISIBLE);

            String decryptedPinCode = AesCrypt.decrypt(mEntry.getPin_code(), pass);
            mCustomerPinCodeTextView.setText(decryptedPinCode);
        }
        else {
            mPinCodeTextView.setVisibility(View.GONE);
            mCustomerPinCodeTextView.setVisibility(View.GONE);
            mPinCodeCopyImageButton.setVisibility(View.GONE);
            mPinOpenSymbolsImageButton.setVisibility(View.GONE);
            mDivider_6.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
