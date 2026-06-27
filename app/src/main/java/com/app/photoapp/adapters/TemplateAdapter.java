package com.app.photoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.photoapp.R;
import com.app.photoapp.data.model.TemplateModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import java.util.ArrayList;
import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> {
    private final Context context;
    private List<TemplateModel> templates;
    private OnTemplateClickListener listener;

    public interface OnTemplateClickListener {
        void onTemplateClick(TemplateModel template);
    }

    public TemplateAdapter(Context context) {
        this.context = context;
        this.templates = new ArrayList<>();
    }

    public void setOnTemplateClickListener(OnTemplateClickListener listener) {
        this.listener = listener;
    }

    public void setTemplates(List<TemplateModel> templates) {
        this.templates = templates != null ? templates : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_template, parent, false);
        return new TemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        holder.bind(templates.get(position));
    }

    @Override
    public int getItemCount() { return templates.size(); }

    class TemplateViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgTemplate;
        private final TextView tvNewBadge;
        private final TextView tvFreeBadge;

        TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTemplate = itemView.findViewById(R.id.img_template);
            tvNewBadge = itemView.findViewById(R.id.tv_new_badge);
            tvFreeBadge = itemView.findViewById(R.id.tv_free_badge);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_ID && listener != null) {
                    listener.onTemplateClick(templates.get(pos));
                }
            });
        }

        void bind(TemplateModel template) {
            Glide.with(context)
                    .load(template.getImageUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgTemplate);
            tvNewBadge.setVisibility(template.isNewTemplate() ? View.VISIBLE : View.GONE);
            tvFreeBadge.setVisibility(template.isFree() ? View.VISIBLE : View.GONE);
        }
    }
}
