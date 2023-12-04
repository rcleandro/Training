package br.com.leandro.training.ui.training

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.training.databinding.FragmentAddTrainingBinding
import br.com.leandro.training.utils.validateForm
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to add new training.
 */
@AndroidEntryPoint
class AddTrainingFragment : Fragment() {

    private var _binding: FragmentAddTrainingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddTrainingViewModel
    private lateinit var adapter: ChooseExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddTrainingViewModel::class.java]

        lifecycle.addObserver(ChooseExerciseLifecycleObserver(viewModel))
        adapter = ChooseExerciseListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        binding.chooseExercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chooseExercisesRecyclerView.adapter = adapter

        binding.previous.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener { onSave() }

        binding.editTextComments.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = binding.editTextComments.validateForm()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = binding.editTextComments.validateForm()
            }
        })

        // Observer UI State for changes.
        viewModel
            .stateOnceAndStream()
            .observe(viewLifecycleOwner) {
                bindUiState(it)
            }

        viewModel
            .onSaved()
            .observe(viewLifecycleOwner) {
                if (it) findNavController().navigateUp()
        }
    }

    /**
     * Bind UI State to View.
     *
     * Update list of exercises according to updates.
     */
    private fun bindUiState(uiState: AddTrainingViewModel.UiState) {
        adapter.updateExercises(uiState.exerciseList)
    }

    private fun onSave() {
        val description = binding.editTextComments.text.toString()
        val exercises = adapter.getSelectedExercises()

        viewModel.addTraining(description = description, exercises = exercises)
    }
}