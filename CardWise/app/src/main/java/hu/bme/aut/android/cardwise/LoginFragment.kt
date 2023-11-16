package hu.bme.aut.android.cardwise

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.cardwise.databinding.FragmentLoginBinding
import hu.bme.aut.android.cardwise.databinding.FragmentMenuBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //installSplashScreen()
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            /*if(binding.etUsername.text.toString().isEmpty()) {
                binding.etUsername.requestFocus()
                binding.etUsername.error = "Please enter your username"
            }
            else if(binding.etPassword.text.toString().isEmpty()) {
                binding.etPassword.requestFocus()
                binding.etPassword.error = "Please enter your password"
            }
            else {
                findNavController().navigate(R.id.action_loginFragment3_to_menuFragment)
            }*/
            findNavController().navigate(R.id.action_loginFragment3_to_menuFragment)

        }
    }
}