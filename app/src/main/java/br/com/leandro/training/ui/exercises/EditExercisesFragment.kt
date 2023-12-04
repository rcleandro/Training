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
import androidx.navigation.fragment.navArgs
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentEditExercisesBinding
import br.com.leandro.training.utils.validateForm
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to edit exercise.
 */
@AndroidEntryPoint
class EditExercisesFragment : Fragment() {

    private var _binding: FragmentEditExercisesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EditExercisesViewModel
    private val args by navArgs<EditExercisesFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[EditExercisesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgs()

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

        viewModel
            .onSaved()
            .observe(viewLifecycleOwner) {
                if (it) findNavController().navigateUp()
            }
    }

    private fun setArgs() {
        val title = "${getString(R.string.edit_exercise)} ${args.exercise.name}"
        binding.tvEditExercise.text = title
        binding.editTextComments.setText(args.exercise.comments)
        args.exercise.image?.let { binding.editTextImage.setText(it) }
        validateForm()
    }

    private fun validateForm() {
        binding.btnSave.isEnabled = binding.editTextComments.validateForm()
    }

    private fun onSave() {
        val name = args.exercise.name
        val comment = binding.editTextComments.text.toString()
        val imageUrl =
            if (binding.editTextImage.text.isNullOrEmpty()) null
            else binding.editTextImage.text.toString()

        viewModel.editExercise(name = name, comment = comment, image = imageUrl)
    }
}