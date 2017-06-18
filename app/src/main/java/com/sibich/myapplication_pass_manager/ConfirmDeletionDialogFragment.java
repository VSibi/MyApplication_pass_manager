package com.sibich.myapplication_pass_manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Slavon on 03.04.2017.
 */
public class ConfirmDeletionDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
   * implement this interface in order to receive event callbacks.
   * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDeleteEntryDialogPositiveClick(DialogFragment dialog);
     //   public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
      NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_dialog_confirm, null);



        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.confirm_deletion)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                         mListener.onDeleteEntryDialogPositiveClick(ConfirmDeletionDialogFragment.this);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmDeletionDialogFragment.this.getDialog().cancel();
                    }
                })
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnPositive = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNegative = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
        btnPositive.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorFlatButton));
        btnNegative.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorFlatButton));

    }
}
