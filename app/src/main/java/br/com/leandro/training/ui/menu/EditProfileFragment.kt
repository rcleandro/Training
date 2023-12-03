package br.com.leandro.training.ui.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentEditProfileBinding
import br.com.leandro.training.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that allows you to change the profile name and e-mail.
 */
@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]

        lifecycle.addObserver(EditProfileLifecycleObserver(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForm()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForm()
            }
        })

        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForm()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForm()
            }
        })

        binding.previous.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            findNavController().popBackStack()
        }

        // Observer UI State for changes.
        viewModel
            .stateOnceAndStream()
            .observe(viewLifecycleOwner) { uiState ->
                binding.editTextName.setText(uiState.profile.name)
                binding.editTextEmail.setText(uiState.profile.email)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateForm(): Boolean {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()

        return when {
            name.isEmpty() -> {
                binding.editTextName.error = getString(R.string.must_be_filled)
                false
            }
            email.isEmpty() -> {
                binding.editTextEmail.error = getString(R.string.must_be_filled)
                false
            }
            !binding.editTextEmail.text.isValidEmail() -> {
                binding.editTextEmail.error = getString(R.string.invalid_email)
                false
            }
            else -> true
        }
    }
}