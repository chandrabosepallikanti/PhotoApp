package com.app.photoapp.ui.creations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.photoapp.R;
import com.app.photoapp.adapters.SavedImageAdapter;
import com.app.photoapp.utils.GridSpanHelper;

public class CreationsFragment extends Fragment {

    private CreationsViewModel viewModel;
    private SavedImageAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_creations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_creations);
        tvEmpty = view.findViewById(R.id.tv_empty);

        int spanCount = GridSpanHelper.getSpanCount(requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), spanCount));

        adapter = new SavedImageAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CreationsViewModel.class);
        viewModel.getSavedImages().observe(getViewLifecycleOwner(), images -> {
            adapter.setImages(images);
            boolean isEmpty = images == null || images.isEmpty();
            tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        });
    }
}
