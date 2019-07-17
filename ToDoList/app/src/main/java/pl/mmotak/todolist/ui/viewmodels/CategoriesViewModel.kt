package pl.mmotak.todolist.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.mmotak.todolist.data.TodoCategoryFactory
import pl.mmotak.todolist.models.TodoCategory

class CategoriesViewModel : ViewModel() {
    private val categories = TodoCategoryFactory().generateCategories(48)
    private val liveCategories : MutableLiveData<List<TodoCategory>> = MutableLiveData()

    fun allCategories() : LiveData<List<TodoCategory>> {
        liveCategories.postValue(categories)
        return liveCategories
    }
}