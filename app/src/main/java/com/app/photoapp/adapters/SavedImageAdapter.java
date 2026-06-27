package com.app.photoapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.photoapp.R;
import com.app.photoapp.data.local.database.SavedImageEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedImageAdapter extends RecyclerView.Adapter<SavedImageAdapter.SavedImageViewHolder> {
    private final Context context;
    private List<SavedImageEntity> images;
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(SavedImageEntity entity);
    }

    public SavedImageAdapter(Context context) {
        this.context = context;
        this.images = new ArrayList<>();
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<SavedImageEntity> images) {
        this.images = images != null ? images : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_image, parent, false);
        return new SavedImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedImageViewHolder holder, int position) {
        holder.bind(images.get(position));
    }

    @Override
    public int getItemCount() { return images.size(); }

    class SavedImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgSaved;

        SavedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSaved = itemView.findViewById(R.id.img_saved);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_ID && listener != null) {
                    listener.onImageClick(images.get(pos));
                }
            });
        }

        void bind(SavedImageEntity entity) {
            String path = entity.getImagePath();
            Object loadTarget = path.startsWith("content://") ? Uri.parse(path) : new File(path);
            Glide.with(context)
                    .load(loadTarget)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgSaved);
        }
    }
}
