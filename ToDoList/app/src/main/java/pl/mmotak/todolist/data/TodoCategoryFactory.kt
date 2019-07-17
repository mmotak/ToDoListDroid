package pl.mmotak.todolist.data

import pl.mmotak.todolist.models.TodoCategory
import pl.mmotak.todolist.models.TodoItem

class TodoCategoryFactory {
    fun generateCategories(size : Int) : List<TodoCategory> {
        return (1..size).map { TodoCategory("type $it", "category $it", generatesItems(it)) }.toList()
    }

    private fun getType(index: Int) : String {
        val types = listOf("Dom", "Praca", "Hobby", "Zakupy", "WAŻNE", "Pomysły", "Inne")
        return types[index % types.size]
    }

    fun generatesItems(index: Int): MutableCollection<TodoItem> {
        return (1..index).map { TodoItem("name_${index}_$it", "descprition_${index}_$it") }.toMutableList()
    }
}