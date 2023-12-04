package br.com.leandro.training.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.databinding.FragmentSignUpBinding
import br.com.leandro.training.utils.showToast
import br.com.leandro.training.utils.validateEmail
import br.com.leandro.training.utils.validateForm
import br.com.leandro.training.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A [Fragment] responsible for registering users
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previous.setOnClickListener { findNavController().navigateUp() }

        binding.btnSave.setOnClickListener { signUp() }

        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForms()
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForms()
            }
        })

        binding.editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSave.isEnabled = validateForms()
            }
        })
    }

    private fun signUp() {
        loading(true)

        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener { envioEmail ->
                        if (envioEmail.isSuccessful) {
                            findNavController().navigateUp()
                        } else {
                            auth.currentUser!!.delete().addOnCompleteListener {
                                requireContext().showToast(envioEmail.exception?.message!!)
                                loading(false)
                            }
                        }
                    }
                } else {
                    requireContext().showToast("Falha ao realizar operação. Motivo: ${task.exception?.message}")
                    loading(false)
                }
            }

    }

    private fun loading(flag: Boolean) {
        binding.editTextEmail.isEnabled = !flag
        binding.editTextPassword.isEnabled = !flag
        binding.editTextConfirmPassword.isEnabled = !flag
        binding.btnSave.isEnabled = !flag
        binding.btnSave.text = if (flag) "Carregando..." else "Cadastrar"
    }

    private fun validateForms(): Boolean {
        return when {
            !binding.editTextEmail.validateForm() -> false
            !binding.editTextEmail.validateEmail() -> false
            !binding.editTextPassword.validatePassword(binding.editTextConfirmPassword) -> false
            !binding.editTextConfirmPassword.validatePassword(binding.editTextPassword) -> false
            else -> true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}