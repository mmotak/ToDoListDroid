package pl.mmotak.todolist.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_items.*
import pl.mmotak.todolist.R
import pl.mmotak.todolist.data.TodoCategoryFactory
import pl.mmotak.todolist.models.ParcelTodoCategory
import pl.mmotak.todolist.models.TodoCategory
import pl.mmotak.todolist.ui.adapters.ItemAdapter

class ItemsActivity : AppCompatActivity() {

    @InjectExtra
    lateinit var todoCategory : ParcelTodoCategory
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        Dart.inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = todoCategory.type
            subtitle = todoCategory.name
            setDisplayHomeAsUpEnabled(true)
        }

        setUpVies()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpVies() {
        adapter = ItemAdapter(this)
        rc_items.adapter = adapter
        rc_items.layoutManager = LinearLayoutManager(this)

        adapter.setItems(TodoCategoryFactory().generatesItems(12).toList())
    }
}