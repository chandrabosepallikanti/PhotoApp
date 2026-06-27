package com.app.photoapp.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PrivacyPolicyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_privacy_policy, null);
        TextView tvPolicy = view.findViewById(R.id.tv_policy_text);
        tvPolicy.setText(AppConstants.PRIVACY_POLICY_TEXT);

        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Privacy Policy")
                .setView(view)
                .setPositiveButton("Close", (dialog, which) -> dismiss())
                .create();
    }
}
