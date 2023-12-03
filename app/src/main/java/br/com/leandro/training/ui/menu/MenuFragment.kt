package br.com.leandro.training.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.training.R
import br.com.leandro.training.databinding.FragmentMenuBinding
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
        super.onViewCreated(view, savedInstanceState)

        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.navigation_edit_profile)
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.navigation_change_password)
        }

        binding.deleteAccount.setOnClickListener { }
        binding.exit.setOnClickListener { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}