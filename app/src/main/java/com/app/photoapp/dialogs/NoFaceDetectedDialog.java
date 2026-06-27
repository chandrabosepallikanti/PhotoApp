package com.app.photoapp.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NoFaceDetectedDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle("No Face Detected")
                .setMessage("No face detected. Please choose another image.")
                .setPositiveButton("OK", (dialog, which) -> dismiss())
                .setCancelable(false)
                .create();
    }
}
