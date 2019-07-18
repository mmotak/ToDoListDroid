package pl.mmotak.todolist.ui.activities

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
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


class CategoriesActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: TodoViewModelFactory by instance()

    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setSupportActionBar(toolbar)
        setUpVies()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        viewModel.allCategories().observe(this, Observer { categories -> adapter.setItems(categories) })
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

    private fun animSlideDown(viewToAnim: ViewGroup) {
        val set = AnimationSet(true)
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
        )

        animation.duration = 2000
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
        })
        set.addAnimation(animation)

        val controller = LayoutAnimationController(
            set, 0.25f
        )
        viewToAnim.layoutAnimation = controller
    }
}
