package pl.mmotak.todolist.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.add_dialog.*
import pl.mmotak.todolist.R


class AddItemDialog : DialogFragment() {

    companion object {
        private val TAG : String = AddItemDialog::class.java.simpleName
        fun show(fragmentActivity : FragmentActivity) : AddItemDialog {
            val dialog = AddItemDialog()
            dialog.show(fragmentActivity.supportFragmentManager, TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pick a style based on the num.
        val style = DialogFragment.STYLE_NO_FRAME
        val theme = R.style.Dialog
        setStyle(DialogFragment.STYLE_NORMAL, theme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        //dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.add_dialog, container, false)
        isCancelable = false
        return rootView
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setWindowAnimations(R.style.DialogAnimation)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        b_cancel.setOnClickListener { dismiss() }
    }
}