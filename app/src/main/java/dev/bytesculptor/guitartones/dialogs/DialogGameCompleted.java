/*
 * MIT License
 *
 * Copyright (c) 2021 Byte Sculptor Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.bytesculptor.guitartones.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

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
