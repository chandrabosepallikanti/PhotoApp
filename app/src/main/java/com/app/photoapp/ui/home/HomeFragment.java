package com.app.photoapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.photoapp.R;
import com.app.photoapp.adapters.TemplateAdapter;
import com.app.photoapp.constants.AppConstants;
import com.app.photoapp.ui.crop.CropActivity;
import com.app.photoapp.utils.GridSpanHelper;
import com.app.photoapp.utils.PreferenceManager;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private TemplateAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_templates);
        progressBar = view.findViewById(R.id.progress_bar);

        int spanCount = GridSpanHelper.getSpanCount(requireContext());
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TemplateAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnTemplateClickListener(template -> {
            PreferenceManager prefs = new PreferenceManager(requireContext());
            prefs.setSelectedTemplateUrl(template.getImageUrl());

            Intent intent = new Intent(requireActivity(), CropActivity.class);
            intent.putExtra(AppConstants.EXTRA_TEMPLATE_URL, template.getImageUrl());
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getTemplates().observe(getViewLifecycleOwner(), templates -> {
            adapter.setTemplates(templates);
        });
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (recyclerView != null) {
            int spanCount = GridSpanHelper.getSpanCount(requireContext());
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(spanCount);
            adapter.notifyDataSetChanged();
        }
    }
}
