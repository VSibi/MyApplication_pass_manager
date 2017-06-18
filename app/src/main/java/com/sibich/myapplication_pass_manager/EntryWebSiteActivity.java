package com.sibich.myapplication_pass_manager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class EntryWebSiteActivity extends AppCompatActivity
        implements ConfirmDeletionDialogFragment.NoticeDialogListener {

    private static final String DIALOG_CONFIRM_DELETE = "DialogConfirmDelete";
    private static final String EXTRA_ENTRY_ID =
            "com.sibich.myapplication_pass_manager_entry_id";
    private static final String KEY_STATE_OF_PASS_SYMBOLS = "state_of_pass_symbols";

    private boolean mIsOpenPassSymbols = false;

    private EntryWebSite mEntry;
    private TextView mCustomerTitleTextView, mUserNameTextView, mCustomerUserNameTextView, mPasswordTextView, mCustomerPasswordTextView,
            mWebSiteTextView, mCustomerWebSiteTextView;
    private ImageButton mTitleCopyImageButton, mUserNameCopyImageButton, mPasswordCopyImageButton, mPassOpenSymbolsImageButton,
    mWebSiteCopyImageButton, mOpenWebSiteImageButton;
    private View mDivider_2, mDivider_3, mDivider_4;

    private EntryLab mEntryLab = EntryLab.get(this);

    public static Intent newIntent(Context packageContext, UUID entryId) {
        Intent intent = new Intent(packageContext, EntryWebSiteActivity.class);
        intent.putExtra(EXTRA_ENTRY_ID, entryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_web_site);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_EntryWebSite);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        UUID entryId = (UUID) getIntent().getSerializableExtra(EXTRA_ENTRY_ID);
        mEntry = mEntryLab.getEntryWebSites(entryId);

        mCustomerTitleTextView = (TextView) findViewById(R.id.entry_web_site_customer_title_textView);

        mTitleCopyImageButton = (ImageButton) findViewById(R.id.entry_web_site_title_copy_imageButton);
        mTitleCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerTitleTextView.getText().toString();
                if(copyToClipboard(EntryWebSiteActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mUserNameTextView = (TextView) findViewById(R.id.entry_web_site_username_textView);
        mCustomerUserNameTextView = (TextView) findViewById(R.id.entry_web_site_customer_username_textView);

        mUserNameCopyImageButton = (ImageButton) findViewById(R.id.entry_web_site_username_copy_imageButton);
        mUserNameCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerUserNameTextView.getText().toString();
                if(copyToClipboard(EntryWebSiteActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
        mDivider_2 = findViewById(R.id.entry_web_site_divider_2);

        mPasswordTextView = (TextView) findViewById(R.id.entry_web_site_password_textView);
        mCustomerPasswordTextView = (TextView) findViewById(R.id.entry_web_site_customer_password_textView);

        mPasswordCopyImageButton = (ImageButton) findViewById(R.id.entry_web_site_password_copy_imageButton);
        mPasswordCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerPasswordTextView.getText().toString();
                if(copyToClipboard(EntryWebSiteActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mPassOpenSymbolsImageButton = (ImageButton) findViewById(R.id.entry_web_site_password_open_symbol_imageButton);

        if (savedInstanceState != null) {
            mIsOpenPassSymbols = savedInstanceState.getBoolean(KEY_STATE_OF_PASS_SYMBOLS, false);
        }

        if (mIsOpenPassSymbols) {
            mCustomerPasswordTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            mCustomerPasswordTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }

        mPassOpenSymbolsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsOpenPassSymbols) {
                    mCustomerPasswordTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_black_24dp);
                    mIsOpenPassSymbols = true;
                }
                else {
                    mCustomerPasswordTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassOpenSymbolsImageButton.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mIsOpenPassSymbols = false;
                }
            }
        });
        mDivider_3 = findViewById(R.id.entry_web_site_divider_3);

        mWebSiteTextView = (TextView) findViewById(R.id.entry_web_site_website_textView);
        mCustomerWebSiteTextView = (TextView) findViewById(R.id.entry_web_site_customer_website_textView);

        mWebSiteCopyImageButton = (ImageButton) findViewById(R.id.entry_web_site_website_copy_imageButton);
        mWebSiteCopyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copiedText = mCustomerWebSiteTextView.getText().toString();
                if(copyToClipboard(EntryWebSiteActivity.this, copiedText)) {
                    Snackbar.make(view, getResources().getString(R.string.text_copied_to_clipboard), Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        mOpenWebSiteImageButton = (ImageButton) findViewById(R.id.entry_web_site_open_website_imageButton);
        mOpenWebSiteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mCustomerWebSiteTextView.getText().toString()));
                startActivity(i);
            }
        });

        mDivider_4 = findViewById(R.id.entry_web_site_divider_4);

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
        savedInstanceState.putBoolean(KEY_STATE_OF_PASS_SYMBOLS, mIsOpenPassSymbols);
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDeleteEntryDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        mEntryLab.deleteEntryWebSites(mEntry.getId());
        Toast.makeText(EntryWebSiteActivity.this,
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
            Intent intent = EditEntryWebsiteActivity.newIntent(EntryWebSiteActivity.this, mEntry.getId());
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
        mEntry = mEntryLab.getEntryWebSites(entryId);

        mCustomerTitleTextView.setText(mEntry.getTitle());

        if (!mEntry.getUserName().equals("")) {
            mUserNameTextView.setVisibility(View.VISIBLE);
            mCustomerUserNameTextView.setVisibility(View.VISIBLE);
            mUserNameCopyImageButton.setVisibility(View.VISIBLE);
            mDivider_2.setVisibility(View.VISIBLE);

            String decryptedUserName = AesCrypt.decrypt(mEntry.getUserName(), pass);
            mCustomerUserNameTextView.setText(decryptedUserName);
        }
        else {
            mUserNameTextView.setVisibility(View.GONE);
            mCustomerUserNameTextView.setVisibility(View.GONE);
            mUserNameCopyImageButton.setVisibility(View.GONE);
            mDivider_2.setVisibility(View.GONE);
        }

        if (!mEntry.getPassword().equals("")) {
            mPasswordTextView.setVisibility(View.VISIBLE);
            mCustomerPasswordTextView.setVisibility(View.VISIBLE);
            mPasswordCopyImageButton.setVisibility(View.VISIBLE);
            mPassOpenSymbolsImageButton.setVisibility(View.VISIBLE);
            mDivider_3.setVisibility(View.VISIBLE);

            String decryptedPassword = AesCrypt.decrypt(mEntry.getPassword(), pass);
            mCustomerPasswordTextView.setText(decryptedPassword);
        }
        else {
            mPasswordTextView.setVisibility(View.GONE);
            mCustomerPasswordTextView.setVisibility(View.GONE);
            mPasswordCopyImageButton.setVisibility(View.GONE);
            mPassOpenSymbolsImageButton.setVisibility(View.GONE);
            mDivider_3.setVisibility(View.GONE);
        }

        if (!mEntry.getWebSite().equals("")) {
            mWebSiteTextView.setVisibility(View.VISIBLE);
            mCustomerWebSiteTextView.setVisibility(View.VISIBLE);
            mWebSiteCopyImageButton.setVisibility(View.VISIBLE);
            mOpenWebSiteImageButton.setVisibility(View.VISIBLE);
            mDivider_4.setVisibility(View.VISIBLE);

            String decryptedWebSite = AesCrypt.decrypt(mEntry.getWebSite(), pass);
            mCustomerWebSiteTextView.setText(decryptedWebSite);
        }
        else {
            mWebSiteTextView.setVisibility(View.GONE);
            mCustomerWebSiteTextView.setVisibility(View.GONE);
            mWebSiteCopyImageButton.setVisibility(View.GONE);
            mOpenWebSiteImageButton.setVisibility(View.GONE);
            mDivider_4.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
