package br.com.leandro.training.ui.auth

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.databinding.FragmentAuthBinding
import br.com.leandro.training.ui.main.MainActivity
import br.com.leandro.training.utils.showToast
import br.com.leandro.training.utils.validateEmail
import br.com.leandro.training.utils.validateForm
import br.com.leandro.training.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A [Fragment] responsible for providing user authentication access to the application
 */
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener { onLoginButtonClicked() }

        binding.btnSignUp.setOnClickListener {
            val action = AuthFragmentDirections.showSignUpFragment()
            findNavController().navigate(action)
        }

        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnLogin.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = validateForms()
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnLogin.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = validateForms()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        updateUI(user)
    }

    private fun onLoginButtonClicked() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    updateUI(auth.currentUser)
                } else {
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    requireContext().showToast("Autenticação falhou. Verifique seu e-mail e senha.")
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        when {
            user != null && !user.isEmailVerified -> {
                val action = AuthFragmentDirections.showCheckEmail()
                findNavController().navigate(action)
            }
            user != null -> presentMainScreen()
        }
    }

    private fun presentMainScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun validateForms(): Boolean {
        return when {
            !binding.editTextEmail.validateForm() -> false
            !binding.editTextEmail.validateEmail() -> false
            !binding.editTextPassword.validatePassword() -> false
            else -> true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}