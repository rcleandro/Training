package br.com.leandro.training.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentCheckEmailBinding
import br.com.leandro.training.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A [Fragment] responsible for confirming verification email
 */
class CheckEmailFragment : Fragment() {

    private var _binding: FragmentCheckEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckEmailBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChecked.setOnClickListener { onCheckedButtonClicked() }

        binding.btnResend.setOnClickListener { onResendButtonClicked() }

        binding.btnExit.setOnClickListener { onExitButtonClicked() }
    }

    private fun onCheckedButtonClicked() {
        loading(true)
        binding.btnChecked.text = getString(R.string.loading)
        auth.currentUser?.reload()?.addOnCompleteListener {
            if (auth.currentUser!!.isEmailVerified) findNavController().navigateUp()
            else {
                requireContext().showToast(getString(R.string.email_has_not_yet_been_verified))
                binding.btnChecked.text = getString(R.string.email_checked)
                loading(false)
            }
        }
    }

    private fun onResendButtonClicked() {
        loading(true)
        binding.btnResend.text = getString(R.string.loading)
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) requireContext().showToast(getString(R.string.resent_verification_email))
            else requireContext().showToast(task.exception?.message!!)

            loading(false)
            binding.btnResend.text = getString(R.string.email_not_received)
        }
    }

    private fun onExitButtonClicked() {
        auth.signOut()
        findNavController().navigateUp()
    }

    private fun loading(flag: Boolean) {
        binding.btnChecked.isEnabled = !flag
        binding.btnResend.isEnabled = !flag
        binding.btnExit.isEnabled = !flag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}