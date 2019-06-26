package com.zhuzichu.mvvm.databinding.recyclerview;


import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zhuzichu.mvvm.BR;
import com.zhuzichu.mvvm.R;
import com.zhuzichu.mvvm.databinding.command.BindingCommand;
import com.zhuzichu.mvvm.viewmodel.ItemNineImageViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goldze on 2017/6/16.
 */
public class ViewAdapter {

    @BindingAdapter("bindNineImages")
    public static void bindNineImages(RecyclerView recyclerView, List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ItemBinding<ItemNineImageViewModel> itemBind = ItemBinding.of(BR.item, R.layout.item_nine_image);
        DiffObservableList<ItemNineImageViewModel> items = new DiffObservableList<>(new DiffUtil.ItemCallback<ItemNineImageViewModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ItemNineImageViewModel oldItem, @NonNull ItemNineImageViewModel newItem) {
                return oldItem.getUrl().equals(newItem.getUrl());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull ItemNineImageViewModel oldItem, @NonNull ItemNineImageViewModel newItem) {
                return oldItem == newItem;
            }
        });
        ArrayList<ItemNineImageViewModel> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            data.add(new ItemNineImageViewModel(recyclerView.getContext(), new ArrayList<>(list), list.get(i)));
        }
        items.update(data);
        BindingRecyclerViewAdapters.setAdapter(recyclerView, itemBind, items, null, null, null, null);
    }

    @BindingAdapter("lineManager")
    public static void setLineManager(RecyclerView recyclerView, LineManagers.LineManagerFactory lineManagerFactory) {
        recyclerView.addItemDecoration(lineManagerFactory.create(recyclerView));
    }

    @BindingAdapter(value = {"gridLayoutManager", "spanSizeLookup"}, requireAll = false)
    public static void setGridLayoutManager(final RecyclerView recyclerView, final int spanCount, final GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), spanCount);
        if (spanSizeLookup != null)
            layoutManager.setSpanSizeLookup(spanSizeLookup);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final RecyclerView recyclerView,
                                             final BindingCommand<ScrollDataWrapper> onScrollChangeCommand,
                                             final BindingCommand<Integer> onScrollStateChangedCommand) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int state;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ScrollDataWrapper(dx, dy, state));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                if (onScrollStateChangedCommand != null) {
                    onScrollStateChangedCommand.execute(newState);
                }
            }
        });

    }

    public static class ScrollDataWrapper {
        public float scrollX;
        public float scrollY;
        public int state;

        public ScrollDataWrapper(float scrollX, float scrollY, int state) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.state = state;
        }
    }
}
