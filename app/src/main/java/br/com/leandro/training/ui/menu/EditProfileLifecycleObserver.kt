package br.com.leandro.training.ui.menu

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class EditProfileLifecycleObserver(
    private val viewModel: EditProfileViewModel
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}