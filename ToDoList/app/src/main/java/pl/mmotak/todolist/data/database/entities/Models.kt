package pl.mmotak.todolist.data.database.entities

import androidx.room.*
import pl.mmotak.todolist.data.database.*

@Entity(
    tableName = TABLE_CATEGORIES
//    indices = [
//        Index(value = [COLUMN_ID, COLUMN_CATEGORY_NAME], unique = true)]
)
data class DBCategory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_CATEGORY_NAME)
    val type: String,
    @ColumnInfo(name = COLUMN_CATEGORY_DESCRIPTION)
    val name: String
)

@Entity(
    tableName = TABLE_ITEMS,
    foreignKeys = [androidx.room.ForeignKey(
        entity = DBCategory::class,
        parentColumns = kotlin.arrayOf(COLUMN_ID),
        childColumns = kotlin.arrayOf(COLUMN_ID_PARENT),
        onDelete = androidx.room.ForeignKey.CASCADE
    )]
)
data class DBItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_ID_PARENT, index = true)
    val parentId: Long,
    @ColumnInfo(name = COLUMN_ITEM_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_ITEM_DESCRIPTION)
    val description: String
)

data class FullDBCategory(
    @Embedded
    val category: DBCategory,
    @Relation(parentColumn = COLUMN_ID, entityColumn = COLUMN_ID_PARENT, entity = DBItem::class)
    val items: List<DBItem>
)