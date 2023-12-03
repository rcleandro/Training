package br.com.leandro.training.ui.exercises

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ExerciseLifecycleObserver(
    private val viewModel: ExerciseListViewModel
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}