package pl.mmotak.todolist.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.mmotak.todolist.R
import pl.mmotak.todolist.models.TodoCategory

class CategoriesAdapter(context: Context) : BaseAdapter<TodoCategory, CategoriesAdapter.ViewHolder>(context) {
    override fun createViewHolderWithListener(
        parent: ViewGroup,
        viewType: Int,
        onItemClickListener: OnItemClickListener?
    ): ViewHolder {
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    inner class ViewHolder(
        view: View,
        onItemClickListener: OnItemClickListener?
    ) : BaseAdapter.InnerViewHolder<TodoCategory>(view, onItemClickListener) {
        override fun updateView(view: View, item: TodoCategory) {
            view.findViewById<TextView>(R.id.tv_title).text = item.type
            view.findViewById<TextView>(R.id.tv_description).text = item.name
        }
    }
}