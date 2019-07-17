package pl.mmotak.todolist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel() as T
        }
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            return ItemsViewModel() as T
        }
        throw IllegalArgumentException("ViewModel Not Found in TodoViewModelFactory!!")
    }
}