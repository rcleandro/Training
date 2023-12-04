package br.com.leandro.training.ui.training

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ChooseExerciseLifecycleObserver(
    private val viewModel: AddTrainingViewModel
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}