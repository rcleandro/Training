package br.com.leandro.training

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.core.repository.TrainingRepository
import br.com.leandro.training.domain.GetTrainingUseCase
import br.com.leandro.training.ui.training.TrainingListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TrainingListViewModelTest {

    /**
     * InstantTaskExecutorRule swaps the background executor used by the Architecture Components
     * with a different one which executes each task synchronously.
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getTrainingUseCase = mock<GetTrainingUseCase>()
    private val trainingRepository = mock<TrainingRepository>()

    private val viewModel =
        TrainingListViewModel(
            getTrainingUseCase = getTrainingUseCase,
            trainingRepository = trainingRepository
        )

    @Test
    fun `Verify uiState is initialized with  Training`() {
        testCoroutineRule.runBlockingTest {
            // Prepare
            whenever(getTrainingUseCase.invoke()).thenReturn(
                listOf(
                    Training(
                        name = 1,
                        description = "Training 1 description Test",
                        date = Date().time,
                        exercises = listOf(
                            Exercise(
                                name = 1,
                                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                                comments = "Exercise 1 commentary Test"
                            )
                        )
                    )
                )
            )
            viewModel.onResume()

            // Execute
            val uiState = viewModel.stateOnceAndStream().getOrAwaitValue()

            // Verify
            assert(uiState.trainingList.isNotEmpty()) // verify uiState has items when initialized
        }
    }
}