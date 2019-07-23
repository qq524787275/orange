package com.zhuzichu.mvvm.databinding.recyclerview;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zhuzichu.mvvm.databinding.command.BindingCommand;


/**
 * Created by goldze on 2017/6/16.
 */
public class ViewAdapter {

    @BindingAdapter("lineManager")
    public static void setLineManager(RecyclerView recyclerView, LineManagers.LineManagerFactory lineManagerFactory) {
        recyclerView.addItemDecoration(lineManagerFactory.create(recyclerView));
    }

    @BindingAdapter(value = {"gridLayoutManager", "spanSizeLookup"}, requireAll = false)
    public static void setGridLayoutManager(final RecyclerView recyclerView, final int spanCount, final GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        GridLayoutManager gridLayoutManager;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanCount(spanCount);
        } else {
            gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanCount);
        }
        if (spanSizeLookup != null)
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null)
            adapter.notifyItemRangeChanged(0,adapter.getItemCount());
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
