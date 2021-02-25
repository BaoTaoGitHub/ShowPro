/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ThirdViewUtil;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * ================================================
 * 基类 {@link RecyclerView.ViewHolder}
 * <p>
 * Created by JessYan on 2015/11/24.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    protected OnViewClickListener mOnViewClickListener = null;
    protected OnViewLongClickListener mOnViewLongClickListener = null;

    public BaseHolder(View itemView) {
        super(itemView);
        //点击事件
        itemView.setOnClickListener(this);
        //长按事件
        itemView.setOnLongClickListener(this);
        //屏幕适配
        if (ThirdViewUtil.isUseAutolayout()) {
            AutoUtils.autoSize(itemView);
        }
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, itemView);
    }

    /**
     * 设置数据
     *
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    public abstract void setData(@NonNull T data, int position);

    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    protected void onRelease() {

    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            //使用getAdapterPosition时，尽可能的使用notifyItemInserted(0)来更新数据
            int position = this.getAdapterPosition();
            if(position!=-1){
                mOnViewClickListener.onViewClick(view, position);
            }
        }
    }

    /**
     * 长按事件监听
     * @param view
     * @return true 只执行长按事件
     *          false 执行完长按事件之后还执行单击事件
     */
    @Override
    public boolean onLongClick(View view) {
        if (mOnViewLongClickListener != null) {
            //使用getAdapterPosition时，尽可能的使用notifyItemInserted(0)来更新数据
            int position = this.getAdapterPosition();
            if(position!=-1){
                mOnViewLongClickListener.onViewLongClick(view, position);
            }
        }
        return true;
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }

    public void setOnItemLongClickListener(OnViewLongClickListener listener){
        this.mOnViewLongClickListener = listener;
    }
    /**
     * item 点击事件
     */
    public interface OnViewClickListener {

        /**
         * item 被点击
         *
         * @param view     被点击的 {@link View}
         * @param position 在 RecyclerView 中的位置
         */
        void onViewClick(View view, int position);
    }

    public interface OnViewLongClickListener{
        void onViewLongClick(View view,int position);
    }
}