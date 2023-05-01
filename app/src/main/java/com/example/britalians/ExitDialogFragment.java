package com.example.britalians;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ExitDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_exit);

        Button yesButton = dialog.findViewById(R.id.yes_button);
        Button noButton = dialog.findViewById(R.id.no_button);

        yesButton.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && event.getAction() == KeyEvent.ACTION_DOWN) {
                noButton.requestFocus();
                return true;
            }
            return false;
        });

        noButton.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && event.getAction() == KeyEvent.ACTION_DOWN) {
                yesButton.requestFocus();
                return true;
            }
            return false;
        });

        yesButton.setOnClickListener(v -> {
            requireActivity().finishAffinity();
            Toast.makeText(getContext(), "Closed!", Toast.LENGTH_SHORT).show();
            System.exit(0);
            dismiss();
        });

        noButton.setOnClickListener(v -> dismiss());

        return dialog;
    }
}
