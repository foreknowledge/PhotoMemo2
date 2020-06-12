package com.foreknowledge.photomemo2.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.listener.OnItemClickListener
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener

/**
 * Create by Yeji on 22,April,2020.
 */
abstract class BaseRecyclerAdapter<T>(
		@LayoutRes private val layoutResId: Int
) : RecyclerView.Adapter<BaseViewHolder<T>>() {
	protected val items = mutableListOf<T>()

	private var onItemClickListener: OnItemClickListener<T> =
		object : OnItemClickListener<T> {
			override fun onClick(item: T) {
				// default click listener: do nothing
			}
		}

	open fun setOnItemClickListener(listener:(item: T) -> Unit) {
		this.onItemClickListener = object : OnItemSingleClickListener<T>() {
			override fun onSingleClick(item: T) {
				listener(item)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		object : BaseViewHolder<T>(layoutResId, parent) {}

	override fun getItemCount(): Int = items.size

	open fun replaceItems(newItems: List<T>) {
		items.clear()
		items.addAll(newItems)
		notifyDataSetChanged()
	}

	override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
		holder.bind(items[position], onItemClickListener)
}