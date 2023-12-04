package br.com.leandro.training.ui.menu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.databinding.FragmentDeleteAccountBinding
import br.com.leandro.training.ui.auth.AuthActivity
import br.com.leandro.training.utils.showToast
import br.com.leandro.training.utils.validateEmail
import br.com.leandro.training.utils.validateForm
import br.com.leandro.training.utils.validatePassword
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A [Fragment] that allows the user to delete their account.
 */
class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnDelete.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnDelete.isEnabled = validateForms()
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.btnDelete.isEnabled = validateForms()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnDelete.isEnabled = validateForms()
            }
        })

        binding.previous.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDelete.setOnClickListener { showConfirmationDialog() }
    }

    private fun validateForms(): Boolean {
        return when {
            !binding.editTextEmail.validateForm() -> false
            !binding.editTextEmail.validateEmail() -> false
            !binding.editTextPassword.validatePassword() -> false
            else -> true
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Confirmação")
            .setMessage("Tem certeza de que deseja excluir sua conta?")
            .setPositiveButton("Sim") { _, _ -> deleteAccount() }
            .setNegativeButton("Não") { _, _ ->  }
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteAccount() {
        val auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser

        if (user != null) {
            val credential = EmailAuthProvider.getCredential("sj.leandro@gmail.com", "12345678")

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.delete()
                            .addOnCompleteListener { deleteTask ->
                                if (deleteTask.isSuccessful) {
                                    presentAuthScreen()
                                    requireContext().showToast("Conta excluída")
                                } else requireContext().showToast("Falha ao excluir conta")
                            }
                    } else requireContext().showToast("Verifique seu e-mail e senha atual")
                }
        } else {
            Firebase.auth.signOut()
            presentAuthScreen()
        }
    }

    private fun presentAuthScreen() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}