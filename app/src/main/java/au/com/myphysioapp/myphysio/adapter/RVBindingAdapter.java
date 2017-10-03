package au.com.myphysioapp.myphysio.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wooden on 24.11.2016.
 */

public class RVBindingAdapter<T>
        extends RecyclerView.Adapter<RVBindingAdapter.BindingHolder> {

    private int holderLayout, variableId;
    protected List<T> items = new ArrayList<>();
    private OnItemClickListener<T> onItemClickListener;

    public RVBindingAdapter(int holderLayout, int variableId, List<T> items) {
        this.holderLayout = holderLayout;
        this.variableId = variableId;
        this.items = items;
    }

    @Override
    public RVBindingAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(holderLayout, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(final RVBindingAdapter.BindingHolder holder, int position) {
        final T item = items.get(position);
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getLayoutPosition(), item);
            }
        });
        holder.getBinding().setVariable(variableId, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(List<T> newItems){
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void add(T newItem){
        this.items.add(newItem);
        notifyItemInserted(this.items.size() - 1);
    }

    public void add(int pos, T newItem){
        this.items.add(pos, newItem);
        notifyItemInserted(pos);
    }

    public T getItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
