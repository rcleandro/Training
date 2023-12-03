package br.com.leandro.training.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leandro.training.core.database.entity.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
//    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    /**
     * Mutable Live Data that initialize with the current list of saved Profile.
     */
    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(profile = Profile("", "")))
    }

    /**
     * Refresh UI State whenever View Resumes.
     */
    fun onResume() {
        viewModelScope.launch {
            refreshProfile()
        }
    }

    /**
     * Expose the uiState as LiveData to UI.
     */
    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    private suspend fun refreshProfile() {
//        uiState.postValue(UiState(getProfileUseCase()))
    }

    /**
     * UI State containing every data needed to show Profile.
     */
    data class UiState(val profile: Profile)
}