package com.bytesculptor.guitartones.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ro on 15.09.17
 */

public class DialogGameCompleted extends DialogFragment {

    public interface GameCompletedListener {
        void onSuccessCopied();
    }

    GameCompletedListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (GameCompletedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must impl NoticeDialogListener interface in host activity");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        float success = args.getFloat("success", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\nSuccess: " + Math.round(success) + " %").setTitle("Completed!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onSuccessCopied();
            }
        });

        return builder.create();
    }
}
