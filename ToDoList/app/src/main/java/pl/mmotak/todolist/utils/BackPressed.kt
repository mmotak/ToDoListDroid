package pl.mmotak.todolist.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class BackPressed(
    private val lifecycleOwner: LifecycleOwner,
    private val onNextAction: () -> Unit,
    private val onSubscribeAction: () -> Unit,
    private val timeOut: Long = EXIT_TIMEOUT
) : LifecycleObserver {
    companion object {
        private const val EXIT_TIMEOUT: Long = 800
    }

    private val compositeDisposable = CompositeDisposable()
    private val backButtonClickSource = PublishSubject.create<Boolean>()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun event() = backButtonClickSource.onNext(true)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.run {
            dispose()
            clear()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        compositeDisposable.add(backButtonClickSource
            .debounce(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { onNextAction }
            .timeInterval(TimeUnit.MILLISECONDS)
            .skip(1)
            .filter { it.time() < timeOut }
            .subscribe { onSubscribeAction })
    }
}