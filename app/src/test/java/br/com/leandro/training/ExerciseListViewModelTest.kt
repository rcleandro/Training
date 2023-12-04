package br.com.leandro.training

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.repository.ExercisesRepository
import br.com.leandro.training.domain.GetExerciseUseCase
import br.com.leandro.training.ui.exercises.ExerciseListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExerciseListViewModelTest {

    /**
     * InstantTaskExecutorRule swaps the background executor used by the Architecture Components
     * with a different one which executes each task synchronously.
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getExerciseUseCase = mock<GetExerciseUseCase>()
    private val exercisesRepository = mock<ExercisesRepository>()

    private val viewModel =
        ExerciseListViewModel(
            getExerciseUseCase = getExerciseUseCase,
            exercisesRepository = exercisesRepository
        )

    @Test
    fun `Verify uiState is initialized with  Exercise`() {
        testCoroutineRule.runBlockingTest {
            // Prepare
            whenever(getExerciseUseCase.invoke()).thenReturn(
                listOf(
                    Exercise(
                        name = 1,
                        image = "https://github.githubassets.com/favicons/favicon-dark.png",
                        comments = "Exercise 1 commentary Test"
                    )
                )
            )
            viewModel.onResume()

            // Execute
            val uiState = viewModel.stateOnceAndStream().getOrAwaitValue()

            // Verify
            assert(uiState.exerciseList.isNotEmpty()) // verify uiState has items when initialized
        }
    }
}