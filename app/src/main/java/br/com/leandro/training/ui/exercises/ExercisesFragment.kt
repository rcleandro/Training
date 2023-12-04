package br.com.leandro.training.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.training.databinding.FragmentExercisesBinding
import br.com.leandro.training.utils.ItemSwipeHandle
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that displays a list of exercises.
 */
@AndroidEntryPoint
class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExerciseListViewModel
    private lateinit var adapter: ExerciseListAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ExerciseListViewModel::class.java]

        lifecycle.addObserver(ExerciseLifecycleObserver(viewModel))
        adapter = ExerciseListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeHandler()

        // Set the adapter
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.exercisesRecyclerView.adapter = adapter
        adapter.onItemClickListener = { exercise ->
            val action = ExercisesFragmentDirections.showEditExerciseFragment(exercise)
            findNavController().navigate(action)
        }

        binding.fabAddExercises.setOnClickListener {
            val action = ExercisesFragmentDirections.showAddExerciseFragment()
            findNavController().navigate(action)
        }

        // Observer UI State for changes.
        viewModel
            .stateOnceAndStream()
            .observe(viewLifecycleOwner) {
                bindUiState(it)
            }
    }

    /**
     * Bind UI State to View.
     *
     * Update list of exercises according to updates.
     */
    private fun bindUiState(uiState: ExerciseListViewModel.UiState) {
        adapter.updateExercises(uiState.exerciseList)
    }

    private fun setSwipeHandler() {
        itemTouchHelper = ItemTouchHelper(
            ItemSwipeHandle(
                requireContext(),
                object : ItemSwipeHandle.OnTouchListener {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteExercise(adapter.getExercise(viewHolder.adapterPosition).name)
                    }
                }
            )
        )

        itemTouchHelper.attachToRecyclerView(binding.exercisesRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}