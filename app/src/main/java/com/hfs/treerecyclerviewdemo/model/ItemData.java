package com.hfs.treerecyclerviewdemo.model;

import java.util.List;

/**
 * @author HuangFusheng
 * @date 2020-03-05
 * description ItemData
 */
public class ItemData implements Comparable<ItemData> {

    public static final int ITEM_TYPE_PARENT = 0;
    public static final int ITEM_TYPE_CHILD = 1;

    private String uuid;
    /**
     * 显示类型
     */
    private int type;
    private String text;
    /**
     * 路径的深度
     */
    private int treeDepth = 0;
    /**
     * 子路径
     */
    private List<ItemData> children;
    /**
     * 是否展开
     */
    private boolean expand;

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ItemData> getChildren() {
        return children;
    }

    public void setChildren(List<ItemData> children) {
        this.children = children;
    }

    public ItemData(int type, String text, String uuid,
                    int treeDepth, List<ItemData> children) {
        super();
        this.type = type;
        this.text = text;
        this.uuid = uuid;
        this.treeDepth = treeDepth;
        this.children = children;
    }

    public ItemData() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    @Override
    public int compareTo(ItemData another) {
        return this.getText().compareTo(another.getText());
    }

}