package pl.mmotak.todolist.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.mmotak.todolist.R
import pl.mmotak.todolist.models.TodoItem

class ItemAdapter(context: Context): BaseAdapter<TodoItem, ItemAdapter.ViewHolder>(context) {
    override fun createViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int,
        onItemClickListener: OnItemClickListener?
    ): ViewHolder {
        val view = inflater.inflate(R.layout.item_item, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    inner class ViewHolder(
        view: View,
        onItemClickListener: OnItemClickListener?
    ) : BaseAdapter.InnerViewHolder<TodoItem>(view, onItemClickListener) {
        override fun updateView(view: View, item: TodoItem) {
            view.findViewById<TextView>(R.id.tv_title).text = item.name
            view.findViewById<TextView>(R.id.tv_description).text = item.description
        }
    }
}