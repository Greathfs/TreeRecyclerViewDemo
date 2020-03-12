package com.hfs.treerecyclerviewdemo.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hfs.treerecyclerviewdemo.R;
import com.hfs.treerecyclerviewdemo.model.ItemData;

import java.io.File;

/**
 * @author HuangFusheng
 * @date 2020-03-05
 * description ChildViewHolder
 */
public class ChildViewHolder extends BaseViewHolder {

    public TextView text;
    public RelativeLayout relativeLayout;
    private int itemMargin;
    private int offsetMargin;

    public ChildViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.container);
        itemMargin = itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.item_margin);
        offsetMargin = itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.expand_size);
    }

    public void bindView(final ItemData itemData, int position) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text
                .getLayoutParams();
        params.leftMargin = itemMargin * itemData.getTreeDepth() + offsetMargin;
        text.setLayoutParams(params);
        text.setText(itemData.getText());
        relativeLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击了--->" + itemData.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
