package com.hfs.treerecyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfs.treerecyclerviewdemo.R;
import com.hfs.treerecyclerviewdemo.interfaces.ItemDataClickListener;
import com.hfs.treerecyclerviewdemo.interfaces.OnScrollToListener;
import com.hfs.treerecyclerviewdemo.model.ItemData;
import com.hfs.treerecyclerviewdemo.viewholder.BaseViewHolder;
import com.hfs.treerecyclerviewdemo.viewholder.ChildViewHolder;
import com.hfs.treerecyclerviewdemo.viewholder.ParentViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author HuangFusheng
 * @date 2020-03-05
 * description RecyclerAdapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<ItemData> mDataSet;
    private OnScrollToListener onScrollToListener;

    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public RecyclerAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case ItemData.ITEM_TYPE_PARENT:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_recycler_parent, parent, false);
                return new ParentViewHolder(view);
            case ItemData.ITEM_TYPE_CHILD:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_recycler_child, parent, false);
                return new ChildViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_recycler_parent, parent, false);
                return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ItemData.ITEM_TYPE_PARENT:
                ParentViewHolder imageViewHolder = (ParentViewHolder) holder;
                imageViewHolder.bindView(mDataSet.get(position), position,
                        imageClickListener);
                break;
            case ItemData.ITEM_TYPE_CHILD:
                ChildViewHolder textViewHolder = (ChildViewHolder) holder;
                textViewHolder.bindView(mDataSet.get(position), position);
                break;
            default:
                break;
        }
    }

    private ItemDataClickListener imageClickListener = new ItemDataClickListener() {

        @Override
        public void onExpandChildren(ItemData itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<ItemData> children = getChildrenByPath("1",
                    itemData.getTreeDepth());
            if (children == null) {
                return;
            }
            addAll(children, position + 1);// 插入到点击点的下方
            itemData.setChildren(children);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position + 1);
            }
        }

        @Override
        public void onHideChildren(ItemData itemData) {
            int position = getCurrentPosition(itemData.getUuid());
            List<ItemData> children = itemData.getChildren();
            if (children == null) {
                return;
            }
            removeAll(position + 1, getChildrenCount(itemData) - 1);
            if (onScrollToListener != null) {
                onScrollToListener.scrollTo(position);
            }
            itemData.setChildren(null);
        }
    };

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private int getChildrenCount(ItemData item) {
        List<ItemData> list = new ArrayList<ItemData>();
        printChild(item, list);
        return list.size();
    }

    private void printChild(ItemData item, List<ItemData> list) {
        list.add(item);
        if (item.getChildren() != null) {
            for (int i = 0; i < item.getChildren().size(); i++) {
                printChild(item.getChildren().get(i), list);
            }
        }
    }

    /**
     * 根据路径获取子目录或文件
     *
     * @param path
     * @param treeDepth
     * @return
     */
    public List<ItemData> getChildrenByPath(String path, int treeDepth) {
        treeDepth++;
        try {
            List<ItemData> list = new ArrayList<ItemData>();
            List<ItemData> fileList = new ArrayList<ItemData>();
            if ("0".equals(path)) {
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        list.add(new ItemData(ItemData.ITEM_TYPE_PARENT, "Parent--->" + path + "--->" + i, UUID
                                .randomUUID().toString(), treeDepth, null));
                    }else {
                        list.add(new ItemData(ItemData.ITEM_TYPE_CHILD, "Child--->" + path + "--->" + i, UUID
                                .randomUUID().toString(), treeDepth, null));
                    }
                }
            } else {

                for (int i = 0; i < 6; i++) {
                    if (i % 2 == 0) {
                        fileList.add(new ItemData(ItemData.ITEM_TYPE_PARENT, "Parent--->" + path + "--->" + i, UUID
                                .randomUUID().toString(), treeDepth, null));
                    }else {
                        fileList.add(new ItemData(ItemData.ITEM_TYPE_CHILD, "Child--->" + path + "--->" + i, UUID
                                .randomUUID().toString(), treeDepth, null));
                    }
                }
            }

            Collections.sort(list);
            Collections.sort(fileList);
            list.addAll(fileList);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (uuid.equalsIgnoreCase(mDataSet.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    public void add(ItemData text, int position) {
        mDataSet.add(position, text);
        notifyItemInserted(position);
    }

    public void addAll(List<ItemData> list, int position) {
        mDataSet.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
    }

    /**
     * 从position开始删除，删除
     *
     * @param position
     * @param itemCount 删除的数目
     */
    protected void removeAll(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }


    public void delete(int pos) {
        if (pos >= 0 && pos < mDataSet.size()) {
            if (mDataSet.get(pos).getType() == ItemData.ITEM_TYPE_PARENT
                    && mDataSet.get(pos).isExpand()) {// 父组件并且子节点已经展开
                for (int i = 0; i < mDataSet.get(pos).getChildren().size() + 1; i++) {
                    mDataSet.remove(pos);
                }
                notifyItemRangeRemoved(pos, mDataSet.get(pos).getChildren()
                        .size() + 1);
            } else {// 孩子节点，或没有展开的父节点
                mDataSet.remove(pos);
                notifyItemRemoved(pos);
            }
        }
    }
}
