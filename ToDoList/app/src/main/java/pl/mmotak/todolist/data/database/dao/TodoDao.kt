package pl.mmotak.todolist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import pl.mmotak.todolist.data.database.COLUMN_ID_PARENT
import pl.mmotak.todolist.data.database.TABLE_CATEGORIES
import pl.mmotak.todolist.data.database.TABLE_ITEMS
import pl.mmotak.todolist.data.database.entities.DBCategory
import pl.mmotak.todolist.data.database.entities.DBItem
import pl.mmotak.todolist.data.database.entities.FullDBCategory

@Dao
interface TodoDao {
    @Query("SELECT * FROM $TABLE_CATEGORIES")
    fun getAllDBCategories(): Flowable<List<DBCategory>>

    @Query("SELECT * FROM $TABLE_ITEMS WHERE $COLUMN_ID_PARENT = :categoryId")
    fun getAllDBItems(categoryId: Long): Flowable<List<DBItem>>

    //@Query("SELECT * FROM $TABLE_CATEGORIES, $TABLE_ITEMS WHERE $TABLE_CATEGORIES.id = $TABLE_ITEMS.id_parent")
    @Query("SELECT * FROM $TABLE_CATEGORIES")
    fun getAll() : Flowable<List<FullDBCategory>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCategory(dbCategory: DBCategory): Completable

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertItem(dbItem: DBItem): Completable
}