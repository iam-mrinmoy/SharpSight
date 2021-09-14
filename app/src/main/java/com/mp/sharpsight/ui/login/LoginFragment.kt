package com.mp.sharpsight.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.mp.sharpsight.R
import com.mp.sharpsight.appUtil.hideKeyboard
import com.mp.sharpsight.appUtil.showToast
import com.mp.sharpsight.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.ivMainBg.load(R.drawable.bg)
        binding.ivCompanyLogo.load(R.drawable.sharpsight_logo)
        binding.btnSignIn.setOnClickListener {
            requireActivity().hideKeyboard()
            showToast()
        }
        binding.tvBackToSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}