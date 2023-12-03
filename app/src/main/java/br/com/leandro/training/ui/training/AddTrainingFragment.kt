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
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentAddTrainingBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to add new training.
 */
@AndroidEntryPoint
class AddTrainingFragment : Fragment() {

    private var _binding: FragmentAddTrainingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddTrainingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddTrainingViewModel::class.java]
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

        viewModel.addTraining(comment, listOf())
    }

    private fun validateForm(): Boolean {
        val name = binding.editTextComments.text.toString()

        return if (name.isEmpty()) {
            binding.editTextComments.error = getString(R.string.must_be_filled)
            false
        } else true
    }
}