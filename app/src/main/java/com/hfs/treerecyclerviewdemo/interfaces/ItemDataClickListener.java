package com.hfs.treerecyclerviewdemo.interfaces;

import com.hfs.treerecyclerviewdemo.model.ItemData;

/**
 *
 * @author HuangFusheng
 * @date 2020-03-05
 * description ItemDataClickListener
 */

public interface ItemDataClickListener {

	public void onExpandChildren(ItemData itemData);

	public void onHideChildren(ItemData itemData);

}
