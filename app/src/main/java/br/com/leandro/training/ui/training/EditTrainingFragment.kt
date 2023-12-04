package br.com.leandro.training.ui.training

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentEditTrainingBinding
import br.com.leandro.training.utils.validateForm
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to edit training.
 */
@AndroidEntryPoint
class EditTrainingFragment : Fragment() {

    private var _binding: FragmentEditTrainingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EditTrainingViewModel
    private lateinit var adapter: ChooseExerciseListAdapter
    private val args by navArgs<EditTrainingFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[EditTrainingViewModel::class.java]

        lifecycle.addObserver(ChooseExerciseToEditLifecycleObserver(viewModel))
        adapter = ChooseExerciseListAdapter(args.training.exercises)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgs()

        // Set the adapter
        binding.chooseExercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chooseExercisesRecyclerView.adapter = adapter

        binding.previous.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener { onSave() }

        binding.editTextComments.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { validateForm() }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateForm()
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

    private fun setArgs() {
        val title = "${getString(R.string.edit_training)} ${args.training.name}"
        binding.tvEditTraining.text = title
        binding.editTextComments.setText(args.training.description)
        validateForm()
    }

    private fun validateForm() {
        binding.btnSave.isEnabled = binding.editTextComments.validateForm()
    }

    /**
     * Bind UI State to View.
     *
     * Update list of exercises according to updates.
     */
    private fun bindUiState(uiState: EditTrainingViewModel.UiState) {
        adapter.updateExercises(uiState.exerciseList)
    }

    private fun onSave() {
        val name = args.training.name
        val description = binding.editTextComments.text.toString()
        val exercises = adapter.getSelectedExercises()

        viewModel.editTraining(name = name, description = description, exercise = exercises)
    }
}