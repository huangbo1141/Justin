package au.com.myphysioapp.myphysio.adapter;

import android.view.View;

import java.util.List;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.ui.notifications.NewNotificationVM;

/**
 * Created by Wooden on 13.02.2017.
 */

public class NewNotificationRVAdapter extends RVBindingAdapter<NewNotificationVM> {
    private OnItemButtonClickListener<NewNotificationVM> acceptRejectClick;

    public NewNotificationRVAdapter(List<NewNotificationVM> items,
                                    OnItemButtonClickListener<NewNotificationVM> acceptRejectClick) {
        super(R.layout.item_notification_new, BR.vm, items);
        this.acceptRejectClick = acceptRejectClick;
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        View acceptButton = holder.getBinding().getRoot().findViewById(R.id.notification_accept);
        View rejectButton = holder.getBinding().getRoot().findViewById(R.id.notification_reject);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRejectClick.onButtonClick(holder.getLayoutPosition(),
                        items.get(holder.getLayoutPosition()), true);
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRejectClick.onButtonClick(holder.getLayoutPosition(),
                        items.get(holder.getLayoutPosition()), false);
            }
        });


    }

    public interface OnItemButtonClickListener<T> {
        void onButtonClick(int position, T item, boolean isAccepted);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener<NewNotificationVM> onItemClickListener) {
        throw new UnsupportedOperationException();
    }

    public NewNotificationVM removeItem(int position){
        NewNotificationVM result = items.remove(position);
        notifyItemRemoved(position);
        return result;
    }
}
