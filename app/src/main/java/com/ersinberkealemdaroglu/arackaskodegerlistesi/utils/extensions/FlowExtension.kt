package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(coroutineContext) {
        lifecycle.repeatOnLifecycle(state, block)
    }
}

inline fun <T> Flow<T>.collectWhenCreated(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
        collect {
            action(it)
        }
    }
}

// Etkileşime kapalı olması gereken durumlarda kullanılmalıdır.
inline fun <T> Flow<T?>.collectWhenPrimitiveTypeStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T?) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
        collectLatest {
            if (it != null) {
                action(it)
            }
        }
    }
}


inline fun <T> Flow<List<T>?>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: List<T>?) -> Unit
) {
    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            lifecycleOwner.lifecycleScope.launch {
                this@collectWhenStarted.collect { value ->
                    // Burada hem null kontrolü hem de boş liste kontrolü yapılıyor
                    if (value?.isNotEmpty() == true) {
                        action(value)
                    }
                }
            }
        }
    })
}


// Etkileşime açık olması gereken durumlarda kullanılır.
inline fun <T> Flow<T>.collectWhenResumed(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.RESUMED) {
        collect {
            action(it)
        }
    }
}