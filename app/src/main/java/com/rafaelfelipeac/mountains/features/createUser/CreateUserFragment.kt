package com.rafaelfelipeac.mountains.features.createUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rafaelfelipeac.mountains.R
import com.rafaelfelipeac.mountains.app.prefs
import com.rafaelfelipeac.mountains.core.extension.*
import com.rafaelfelipeac.mountains.core.platform.BaseFragment
import com.rafaelfelipeac.mountains.features.main.MainActivity
import kotlinx.android.synthetic.main.fragment_create_user.*

class
CreateUserFragment : BaseFragment() {

    private val createUserViewModel by lazy { viewModelFactory.get<CreateUserViewModel>(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.fragment_create_user_title)

        hideNavigation()
        (activity as MainActivity).openToolbar()

        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        create_user_create_button.setOnClickListener {
            if (verifyElements()) {
                showProgressBar()
                createUserViewModel.createUser(create_user_email.text.toString(), create_user_password.text.toString())
            }
        }

        create_user_eye.setOnClickListener {
            create_user_password.showOrHidePassword()
            create_user_confirm_password.showOrHidePassword()
        }
    }

    private fun observeViewModel() {
        createUserViewModel.createResult.observe(this, Observer { createResult ->
            hideProgressBar()

            when {
                createResult.isSuccessful -> {
                    prefs.login = true
                    navController.navigate(CreateUserFragmentDirections.actionNavigationCreateUserToNavigationList())
                }
                createResult.message == getString(R.string.result_error_message_email_already_in_use) -> {
                    setErrorMessage(getString(R.string.snackbar_error_email_already_in_use))
                }
                else -> {
                    setErrorMessage(getString(R.string.empty_string))
                    showSnackBar(getString(R.string.snackbar_error_create_user))
                }
            }
        })
    }

    private fun verifyElements(): Boolean {
        when {
            create_user_name.isEmpty() || create_user_email.isEmpty() ||
                    create_user_password.isEmpty() || create_user_confirm_password.isEmpty() -> {
                setErrorMessage(getString(R.string.error_message_empty_fields))
                return false
            }
            create_user_email.emailIsInvalid() -> {
                setErrorMessage(getString(R.string.error_message_invalid_email))
                return false
            }
            create_user_password.text.toString() != create_user_confirm_password.text.toString() -> {
                setErrorMessage(getString(R.string.error_message_different_passwords))
                return false
            }
            create_user_password.text.toString() == create_user_confirm_password.text.toString() &&
                    create_user_password.text.toString().length < 6 -> {
                setErrorMessage(getString(R.string.error_message_min_characters))
                return false
            }
        }

        return true
    }

    private fun setErrorMessage(message: String) {
        create_user_error_message.text = message
    }

    private fun showProgressBar() {
        create_user_progress_bar.visible()
        create_user_eye.gone()
    }

    private fun hideProgressBar() {
        create_user_progress_bar.gone()
        create_user_eye.visible()
    }
}