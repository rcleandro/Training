package br.com.leandro.training.ui.menu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.leandro.training.databinding.FragmentMenuBinding
import br.com.leandro.training.ui.auth.AuthActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] that displays a menu.
 */
@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.changePassword.setOnClickListener {
            val action = MenuFragmentDirections.showChangePasswordFragment()
            findNavController().navigate(action)
        }

        binding.deleteAccount.setOnClickListener {
            val action = MenuFragmentDirections.showDeleteAccountFragment()
            findNavController().navigate(action)
        }

        binding.exit.setOnClickListener { showConfirmationDialog() }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Confirmação")
            .setMessage("Tem certeza de que deseja sair?")
            .setPositiveButton("Sim") { _, _ ->
                Firebase.auth.signOut()
                presentAuthScreen()}
            .setNegativeButton("Não") { _, _ ->  }
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()
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