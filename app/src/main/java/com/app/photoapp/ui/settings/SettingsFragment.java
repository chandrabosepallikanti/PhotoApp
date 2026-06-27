package com.app.photoapp.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.app.photoapp.R;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.dialogs.PrivacyPolicyDialog;
import com.google.android.material.card.MaterialCardView;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialCardView cardRate = view.findViewById(R.id.card_rate_app);
        MaterialCardView cardTerms = view.findViewById(R.id.card_terms);
        MaterialCardView cardPrivacy = view.findViewById(R.id.card_privacy);

        cardRate.setOnClickListener(v -> openPlayStore());
        cardTerms.setOnClickListener(v -> openTerms());
        cardPrivacy.setOnClickListener(v -> showPrivacyPolicy());
    }

    private void openPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.PLAY_STORE_URL)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.PLAY_STORE_WEB_URL)));
        }
    }

    private void openTerms() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.TERMS_URL));
        startActivity(intent);
    }

    private void showPrivacyPolicy() {
        PrivacyPolicyDialog dialog = new PrivacyPolicyDialog();
        dialog.show(getParentFragmentManager(), "privacy_policy");
    }
}
