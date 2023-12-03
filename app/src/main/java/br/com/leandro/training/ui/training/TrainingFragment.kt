package br.com.leandro.training.ui.training

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
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentTrainingBinding
import br.com.leandro.training.utils.ItemSwipeHandle
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that displays a list of trainings.
 */
@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrainingListViewModel
    private lateinit var adapter: TrainingListAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[TrainingListViewModel::class.java]

        lifecycle.addObserver(TrainingLifecycleObserver(viewModel))

        adapter = TrainingListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeHandler()

        // Set the adapter
        binding.trainingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.trainingRecyclerView.adapter = adapter

        binding.fabAddTraining.setOnClickListener {
            findNavController().navigate(R.id.navigation_add_training)
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
     * Update list of training according to updates.
     */
    private fun bindUiState(uiState: TrainingListViewModel.UiState) {
        adapter.updateTrainings(uiState.trainingList)
    }

    private fun setSwipeHandler() {
        itemTouchHelper = ItemTouchHelper(
            ItemSwipeHandle(
                requireContext(),
                object : ItemSwipeHandle.OnTouchListener {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteTraining(adapter.getTraining(viewHolder.adapterPosition).name)
                    }
                }
            )
        )

        itemTouchHelper.attachToRecyclerView(binding.trainingRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}