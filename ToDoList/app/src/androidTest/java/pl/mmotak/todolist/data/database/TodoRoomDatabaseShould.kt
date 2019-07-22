package pl.mmotak.todolist.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import pl.mmotak.todolist.data.database.dao.TodoDao
import pl.mmotak.todolist.data.database.entities.DBCategory
import pl.mmotak.todolist.data.database.entities.DBItem
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule



class TodoRoomDatabaseShould {
    private lateinit var database: TodoDatebase
    private lateinit var todoDao: TodoDao

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TodoDatebase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        todoDao = database.todoDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun getEmptyCategories() {
        todoDao.getAllDBCategories().test().assertEmpty().assertNoErrors()
    }

    @Test
    fun getEmptyItems() {
        todoDao.getAllDBItems(0).test().assertEmpty().assertNoErrors()
    }

    @Test
    fun addCategory() {
        todoDao.insertCategory(DBCategory(0, "c1 type", "c1 name")).test().assertComplete().assertNoErrors()
    }
//
//    @Test
//    fun addCategory2() {
//        todoDao.insertCategory(DBCategory(0, "c1 type", "c1 name")).test().assertComplete().assertNoErrors()
//        todoDao.insertCategory(DBCategory(0, "c1 type", "c1 name")).test().assertComplete().assertNoErrors()
//
//        todoDao.getAllDBCategories().test().assertNoErrors().assertValue { categories ->
//            categories.forEach{ println(it) }
//            categories.size == 1
//        }
//    }

    @Test
    fun addItem() {
        todoDao.insertCategory(DBCategory(0, "c1 type", "c1 name")).test().assertComplete().assertNoErrors()
        todoDao.insertItem(DBItem(0, 1, "c1 type", "c1 name"))
            .test()
            .assertErrorMessage("FOREIGN KEY constraint failed (code 787)")
    }

    @Test
    fun notAddItemWhenWrongParentId() {
        todoDao.insertItem(DBItem(0, 0, "c1 type", "c1 name"))
            .test()
            .assertErrorMessage("FOREIGN KEY constraint failed (code 787)")
    }

    @Test
    fun readCategoriesAndItems() {
        addDataToDatabase()

        todoDao.getAllDBCategories().test().assertNoErrors().assertValue { categories ->
            categories.forEach{ println(it) }
            categories.size == 10
        }

        (1..10).forEach {id ->
            todoDao.getAllDBItems(id.toLong()).test().assertNoErrors().assertValue { items ->
                items.forEach{ println(it) }
                items.size == 10
            }
        }

    }

    @Test
    fun getALL() {
        addDataToDatabase()

        todoDao.getAll().test().assertNoErrors().assertValue {full ->
            full.forEach{println(it)}
            full.size == 10
        }
    }

    private fun addDataToDatabase() {
        (1..10).addCategory(todoDao)
        (1..10).addItems(todoDao)
    }

    private fun IntRange.addItems(todoDao: TodoDao) {
        this.toList()
            .map { it.toLong() }
            .forEach {parent ->
                (1..10).toList().forEach {
                    todoDao.insertItem(
                        DBItem(0, parent, "i$parent-$it", "Parent is $parent, item is $it")
                    ).blockingAwait()
                }
            }
    }

    private fun IntRange.addCategory(todoDao: TodoDao) {
        this.toList().map { it.toLong() }.forEach {
            todoDao.insertCategory(
                DBCategory(0, "c$it type", "c$it name")
            ).blockingAwait()
        }
    }
}
