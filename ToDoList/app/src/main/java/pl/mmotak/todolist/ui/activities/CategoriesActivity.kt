package pl.mmotak.todolist.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_categories.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import pl.mmotak.todolist.R
import pl.mmotak.todolist.models.toParcelTodoCategory
import pl.mmotak.todolist.ui.adapters.BaseAdapter
import pl.mmotak.todolist.ui.adapters.CategoriesAdapter
import pl.mmotak.todolist.ui.dialogs.AddItemDialog
import pl.mmotak.todolist.ui.viewmodels.CategoriesViewModel
import pl.mmotak.todolist.ui.viewmodels.TodoViewModelFactory
import pl.mmotak.todolist.utils.BackPressed
import pl.mmotak.todolist.utils.toast
import java.util.concurrent.TimeUnit


class CategoriesActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: TodoViewModelFactory by instance()

    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: CategoriesViewModel

//    private lateinit var backPressed: BackPressed
    private val EXIT_TIMEOUT: Long = 800
    private val compositeDisposable = CompositeDisposable()
    private val backButtonClickSource = PublishSubject.create<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setSupportActionBar(toolbar)
        setUpVies()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        viewModel.allCategories().observe(this, Observer { categories -> adapter.setItems(categories) })

        // not working ;?
//        backPressed = BackPressed(this,
//            { toast("Please press back once more to exit") },
//            { finish() })
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.add(backButtonClickSource
            .debounce(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { toast("Please press back once more to exit") }
            .timeInterval(TimeUnit.MILLISECONDS)
            .skip(1)
            .filter { it.time() < EXIT_TIMEOUT }
            .subscribe { super.onBackPressed() })
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.run {
            dispose()
            clear()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        backButtonClickSource.onNext(true)
        //backPressed.event()
    }

    private fun setUpVies() {
        adapter = CategoriesAdapter(this)
        adapter.onItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(
                    Henson.with(this@CategoriesActivity)
                        .gotoItemsActivity()
                        .todoCategory(adapter.getItemAtPosition(position).toParcelTodoCategory())
                        .build()
                )
            }
        })
        rc_categories.adapter = adapter
        rc_categories.layoutManager = GridLayoutManager(this, 2)

        fab.setOnClickListener { view ->
            AddItemDialog.show(this)
        }
    }

}
