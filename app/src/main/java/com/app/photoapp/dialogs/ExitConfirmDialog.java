package com.app.photoapp.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ExitConfirmDialog extends DialogFragment {

    public interface OnExitListener {
        void onExit();
    }

    private OnExitListener listener;

    public void setOnExitListener(OnExitListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (listener != null) listener.onExit();
                })
                .setNegativeButton("No", (dialog, which) -> dismiss())
                .create();
    }
}
