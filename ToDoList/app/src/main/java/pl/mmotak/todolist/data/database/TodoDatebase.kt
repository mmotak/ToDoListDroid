package pl.mmotak.todolist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.mmotak.todolist.data.database.dao.TodoDao
import pl.mmotak.todolist.data.database.entities.DBCategory
import pl.mmotak.todolist.data.database.entities.DBItem

@Database(entities = [DBCategory::class, DBItem::class], version = 1)
abstract class TodoDatebase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object {
        @Volatile
        private var DB_INSTANCE: TodoDatebase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = DB_INSTANCE ?: synchronized(LOCK) {
            DB_INSTANCE ?: createDB(context).also { DB_INSTANCE = it }
        }

        private fun createDB(context: Context): TodoDatebase =
            Room.databaseBuilder(
                context.applicationContext.applicationContext,
                TodoDatebase::class.java,
                "todo.db")
                .build()
    }
}