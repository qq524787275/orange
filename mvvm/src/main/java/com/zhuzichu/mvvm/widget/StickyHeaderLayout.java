package com.zhuzichu.mvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.zhuzichu.mvvm.R;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StickyHeaderLayout extends FrameLayout {
    private Context mContext;
    private RecyclerView mRecyclerView;
    //吸顶容器，用于承载吸顶布局。
    private FrameLayout mStickyLayout;
    private final SparseArray<PinnedHeaderCreator> mTypePinnedHeaderFactories = new SparseArray<>();
    //是否吸顶。
    private boolean isSticky = true;
    //用于在吸顶布局中保存viewType的key。
    private final int VIEW_TAG_TYPE = -101;
    //用于在吸顶布局中保存ViewHolder的key。
    private final int VIEW_TAG_HOLDER = -102;
    //记录当前吸顶的组。
    private int mHeaderPosition = -1;
    //是否已经注册了adapter刷新监听
    private boolean isRegisterDataObserver = false;
    //保存吸顶布局的缓存池。它以列表组头的viewType为key,ViewHolder为value对吸顶布局进行保存和回收复用。
    private final SparseArray<RecyclerView.ViewHolder> mStickyViews = new SparseArray<>();

    public StickyHeaderLayout(@NonNull Context context) {
        this(context, null);
    }

    public StickyHeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StickyHeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        List<RecyclerView> list = findRecyclerView(child);
        if (list.size() != 1) {
            throw new IllegalArgumentException("StickyHeaderLayout can host only one direct child --> RecyclerView");
        }

        mRecyclerView = list.get(0);
        addOnScrollListener();
        addStickyLayout();
    }

    private List<RecyclerView> findRecyclerView(View child) {
        List<RecyclerView> list = new ArrayList<>();
        if (null == child) {
            return list;
        }
        if (child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            LinkedList<ViewGroup> linkedList = new LinkedList<>();
            linkedList.add(viewGroup);
            while (!linkedList.isEmpty()) {
                ViewGroup current = linkedList.removeFirst();
                //dosomething
                if (current instanceof RecyclerView) {
                    list.add((RecyclerView) current);
                }
                for (int i = 0; i < current.getChildCount(); i++) {
                    if (current.getChildAt(i) instanceof ViewGroup) {
                        linkedList.addLast((ViewGroup) current.getChildAt(i));
                    } else {
                        //dosomething
                        if (current.getChildAt(i) instanceof RecyclerView) {
                            list.add((RecyclerView) current);
                        }
                    }
                }
            }
        }
        return list;
    }

    private void addStickyLayout() {
        mStickyLayout = new FrameLayout(mContext);
        LayoutParams lp = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        mStickyLayout.setLayoutParams(lp);
        super.addView(mStickyLayout, 1, lp);
    }

    private void addOnScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 在滚动的时候，需要不断的更新吸顶布局。
                if (isSticky) {
                    updateStickyView(false);
                }
            }
        });
    }

    private void updateStickyView(boolean imperative) {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof BindingRecyclerViewAdapter) {
            BindingRecyclerViewAdapter bindAdapter = (BindingRecyclerViewAdapter) adapter;
            registerAdapterDataObserver(bindAdapter);

            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager == null || layoutManager.getChildCount() <= 0) {
                return;
            }
            int firstVisiblePosition = ((RecyclerView.LayoutParams) layoutManager.getChildAt(0).getLayoutParams()).getViewAdapterPosition();

            int headerPosition = findPinnedHeaderPosition(firstVisiblePosition);
            if (headerPosition < 0)
                return;
            if (mHeaderPosition != headerPosition || imperative) {

                mHeaderPosition = headerPosition;
                //通过groupPosition获取当前组的组头position。这个组头就是我们需要吸顶的布局。
                Log.i("zzc", "updateStickyView: " + headerPosition);
                int viewType = bindAdapter.getItemViewType(headerPosition);
                //如果当前的吸顶布局的类型和我们需要的一样，就直接获取它的ViewHolder，否则就回收。
                View stickView = LayoutInflater.from(mContext).inflate(viewType, null);
                ViewDataBinding bind = DataBindingUtil.bind(stickView);
                mStickyLayout.removeAllViews();
                mStickyLayout.addView(bind.getRoot());
                bind.setVariable(bindAdapter.getItemBinding().variableId(), bindAdapter.getAdapterItem(headerPosition));
            }
            //这是是处理第一次打开时，吸顶布局已经添加到StickyLayout，但StickyLayout的高依然为0的情况。
            if (mStickyLayout.getChildCount() > 0 && mStickyLayout.getHeight() == 0) {
                mStickyLayout.requestLayout();
            }

            //设置mStickyLayout的Y偏移量。
            mStickyLayout.setTranslationY(calculateOffset(firstVisiblePosition, headerPosition + 1));
        }
    }


    private float calculateOffset(int firstVisiblePosition, int headerPosition) {
        int nextHeaderPosition = findPinnedHeaderPosition(headerPosition);
        if (nextHeaderPosition != -1) {
            int index = nextHeaderPosition - firstVisiblePosition;
            if (mRecyclerView.getChildCount() > index) {
                //获取下一个组的组头的itemView。
                View view = mRecyclerView.getChildAt(index);
                if (view == null)
                    return 0;
                float off = view.getY() - mStickyLayout.getHeight();
                if (off < 0) {
                    return off;
                }
            }
        }
        return 0;
    }


    public void bindStickyHeader(FrameLayout stickyHeader) {
        removeView(mStickyLayout);
        mStickyLayout = stickyHeader;
    }

    /**
     * 从缓存池中获取吸顶布局
     *
     * @param viewType 吸顶布局的viewType
     * @return
     */
    private RecyclerView.ViewHolder getStickyViewByType(int viewType) {
        return mStickyViews.get(viewType);
    }

    /**
     * 是否吸顶
     *
     * @return
     */
    public boolean isSticky() {
        return isSticky;
    }

    /**
     * 设置是否吸顶。
     *
     * @param sticky
     */
    public void setSticky(boolean sticky) {
        if (isSticky != sticky) {
            isSticky = sticky;
            if (mStickyLayout != null) {
                if (isSticky) {
                    mStickyLayout.setVisibility(VISIBLE);
                    updateStickyView(false);
                } else {
                    mStickyLayout.removeAllViews();
                    mStickyLayout.setVisibility(GONE);
                }
            }
        }
    }

    /**
     * 注册adapter刷新监听
     */
    private void registerAdapterDataObserver(BindingRecyclerViewAdapter adapter) {
        if (!isRegisterDataObserver) {
            isRegisterDataObserver = true;
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    updateStickyViewDelayed();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    updateStickyViewDelayed();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    updateStickyViewDelayed();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    updateStickyViewDelayed();
                }

            });
        }
    }

    private void updateStickyViewDelayed() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                updateStickyView(true);
            }
        }, 100);
    }

    private int findPinnedHeaderPosition(int fromPosition) {
        if (fromPosition >= mRecyclerView.getAdapter().getItemCount() || fromPosition < 0) {
            return -1;
        }

        for (int position = fromPosition; position >= 0; position--) {
            final int viewType = mRecyclerView.getAdapter().getItemViewType(position);
            if (isPinnedViewType(mRecyclerView, position, viewType)) {
                return position;
            }
        }
        return -1;
    }

    private boolean isPinnedViewType(RecyclerView parent, int adapterPosition, int viewType) {
        PinnedHeaderCreator pinnedHeaderCreator = mTypePinnedHeaderFactories.get(viewType);

        return pinnedHeaderCreator != null && pinnedHeaderCreator.create(parent, adapterPosition);
    }

    public void registerTypePinnedHeader(int itemType, PinnedHeaderCreator pinnedHeaderCreator) {
        mTypePinnedHeaderFactories.put(itemType, pinnedHeaderCreator);

    }

    public interface PinnedHeaderCreator {
        boolean create(RecyclerView parent, int adapterPosition);
    }


    @Override
    protected int computeVerticalScrollOffset() {
        if (mRecyclerView != null) {
            try {
                Method method = View.class.getDeclaredMethod("computeVerticalScrollOffset");
                method.setAccessible(true);
                return (int) method.invoke(mRecyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.computeVerticalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollRange() {
        if (mRecyclerView != null) {
            try {
                Method method = View.class.getDeclaredMethod("computeVerticalScrollRange");
                method.setAccessible(true);
                return (int) method.invoke(mRecyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.computeVerticalScrollRange();
    }

    @Override
    protected int computeVerticalScrollExtent() {
        if (mRecyclerView != null) {
            try {
                Method method = View.class.getDeclaredMethod("computeVerticalScrollExtent");
                method.setAccessible(true);
                return (int) method.invoke(mRecyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.computeVerticalScrollExtent();
    }

    @Override
    public void scrollBy(int x, int y) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollBy(x, y);
        } else {
            super.scrollBy(x, y);
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollTo(x, y);
        } else {
            super.scrollTo(x, y);
        }
    }
}
