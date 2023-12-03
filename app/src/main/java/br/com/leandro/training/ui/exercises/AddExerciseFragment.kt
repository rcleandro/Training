package br.com.leandro.training.ui.exercises

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentAddExerciseBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to add new exercise.
 */
@AndroidEntryPoint
class AddExerciseFragment : Fragment() {

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddExerciseViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previous.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener { onSave() }

        binding.editTextComments.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForm()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForm()
            }
        })

        viewModel
            .onSaved()
            .observe(viewLifecycleOwner) {
                if (it) findNavController().navigateUp()
            }
    }

    private fun onSave() {
        val comment = binding.editTextComments.text.toString()
        val imageUrl =
            if (binding.editTextImage.text.isNullOrEmpty()) null
            else binding.editTextImage.text.toString()

        viewModel.addExercise(comment, imageUrl)
    }

    private fun validateForm(): Boolean {
        val name = binding.editTextComments.text.toString()

        return if (name.isEmpty()) {
            binding.editTextComments.error = getString(R.string.must_be_filled)
            false
        } else true
    }
}