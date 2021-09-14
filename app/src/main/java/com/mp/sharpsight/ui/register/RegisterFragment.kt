package com.mp.sharpsight.ui.register

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import coil.load
import com.mp.sharpsight.R
import com.mp.sharpsight.appUtil.hideKeyboard
import com.mp.sharpsight.appUtil.isOnline
import com.mp.sharpsight.appUtil.showToast
import com.mp.sharpsight.appUtil.validateEmail
import com.mp.sharpsight.databinding.RegisterFragmentBinding
import com.mp.sharpsight.ui.register.viewModel.RegisterViewModel

class RegisterFragment : Fragment() {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

        /** observing data changes **/
        viewModel.userEmail.observe(viewLifecycleOwner, { userEmail ->
            binding.edtUserEmail.setText(userEmail)
        })
        viewModel.userName.observe(viewLifecycleOwner, { userName ->
            binding.edtUserName.setText(userName)
        })
        viewModel.userPassword.observe(viewLifecycleOwner, { userPassword ->
            binding.edtPassword.setText(userPassword)
        })

        binding.ivMainBg.load(R.drawable.bg)
        binding.ivCompanyLogo.load(R.drawable.sharpsight_logo)

        binding.tvBtnSignup.setOnClickListener {
            requireActivity().hideKeyboard()
            viewModel.userEmail.value = binding.edtUserEmail.text.toString()
            viewModel.userName.value = binding.edtUserName.text.toString()
            viewModel.userPassword.value = binding.edtPassword.text.toString()
            if (isValidate()) {
                registerNewUser()
            }
        }
        binding.tvLogin.paintFlags = binding.tvLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    /** validation for register form **/
    private fun isValidate(): Boolean {

        return when {
            viewModel.userEmail.value.toString().isEmpty() -> {
                showToast("Enter Email")
                binding.edtUserEmail.requestFocus()
                false
            }
            !viewModel.userEmail.value.toString().validateEmail() -> {
                showToast("Enter Valid Email")
                binding.edtUserEmail.requestFocus()
                false
            }
            viewModel.userName.value.toString().isEmpty() -> {
                showToast("Enter User Name")
                binding.edtUserName.requestFocus()
                false
            }
            viewModel.userPassword.value.toString().isEmpty() -> {
                showToast("Enter Password")
                binding.edtPassword.requestFocus()
                false
            }
            else -> {
                true
            }
        }
    }

    /** register api with online availability check **/
    private fun registerNewUser() {
        if (isOnline()) {
            binding.pBar.visibility = View.VISIBLE
            binding.tvBtnSignup.visibility = View.INVISIBLE
            viewModel.registerNewUser()?.observe(viewLifecycleOwner, {
                if (it.isSuccessful) {
                    if (it.getResponse()?.asJsonObject?.get("status_code")?.asString == "1") {
                        showAlert()
                        clearValues()
                    } else
                        showToast(it.getResponse()?.asJsonObject?.get("status_msg")?.asString!!)
                } else {
                    showToast(getString(R.string.bad_api_response_head))
                }
                binding.pBar.visibility = View.GONE
                binding.tvBtnSignup.visibility = View.VISIBLE
            })
        } else {
            showToast(getString(R.string.no_internet_txt_head))
        }
    }

    /** This alert pop's up when registration successfully done **/
    private fun showAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle("Congratulation")
            .setMessage("You are successfully registered")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            .show()
    }

    /** clear text after registration done successfully **/
    private fun clearValues() {
        viewModel.userEmail.value = ""
        viewModel.userName.value = ""
        viewModel.userPassword.value = ""
        binding.edtPassword.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}