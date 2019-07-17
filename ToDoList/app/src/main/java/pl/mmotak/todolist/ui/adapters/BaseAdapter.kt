package pl.mmotak.todolist.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.mmotak.todolist.R

abstract class BaseAdapter<ITEM, VH : BaseAdapter.InnerViewHolder<ITEM>>(context: Context) : RecyclerView.Adapter<VH>() {
    internal val inflater: LayoutInflater = LayoutInflater.from(context)
    private var onItemClickListener: OnItemClickListener? = null
    private var list = ArrayList<ITEM>()

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val current = list[position]
        holder.setItem(current)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderWithListener(parent, viewType, onItemClickListener)
    }

    abstract fun createViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int,
        onItemClickListener: OnItemClickListener?
    ): VH

    fun getItemAtPosition(position: Int) : ITEM = list[position]

    fun setItems(items: List<ITEM>) {
        if (list.isEmpty()) {
            list.clear()
            list.addAll(items)
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(InternalDiffCallback(list, items))
            list.clear()
            list.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    fun addItem(item: ITEM) {
        list.add(item)
        notifyItemInserted(this.list.size - 1)
    }

    fun onItemClickListener(listener : OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun onItemClickListener(method: (Int) -> Unit) {
        this.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) = method(position)
        }
    }

    abstract class InnerViewHolder<ITEM>(view: View, onItemClickListener: OnItemClickListener?)
        : RecyclerView.ViewHolder(view) {
        init {
            onItemClickListener?.let { listener ->
                itemView.findViewById<View>(R.id.view_parent).setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
        }

        internal fun setItem(item: ITEM) = updateView(itemView, item)
        abstract fun updateView(view: View, item: ITEM)
    }

    inner class InternalDiffCallback<ITEM>(private val oldList: List<ITEM>, private val newList: List<ITEM>) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}