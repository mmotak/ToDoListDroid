package pl.mmotak.todolist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelTodoCategory(val type: String, val name: String) : Parcelable

data class TodoCategory(val type: String, val name: String, val items: MutableCollection<TodoItem>)

data class TodoItem(val name: String, val description: String)

fun ParcelTodoCategory.toTodoCategory() = TodoCategory(type, name, mutableListOf())
fun TodoCategory.toParcelTodoCategory() = ParcelTodoCategory(type, name)