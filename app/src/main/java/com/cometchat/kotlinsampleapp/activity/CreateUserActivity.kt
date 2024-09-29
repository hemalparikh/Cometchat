package com.cometchat.kotlinsampleapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.kotlinsampleapp.AppUtils.Companion.changeTextColorToBlack
import com.cometchat.kotlinsampleapp.AppUtils.Companion.changeTextColorToWhite
import com.cometchat.kotlinsampleapp.AppUtils.Companion.fetchDefaultObjects
import com.cometchat.kotlinsampleapp.AppUtils.Companion.isNightMode
import com.cometchat.kotlinsampleapp.R
import com.cometchat.kotlinsampleapp.databinding.ActivityCreateUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var uid: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var collegeName: TextInputEditText
    private lateinit var domainSpinner: Spinner
    private lateinit var hackathons: TextInputEditText
    private lateinit var createUserBtn: AppCompatButton
    private lateinit var progressBar: ProgressBar
    private lateinit var parentView: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentView = binding.parentView
        progressBar = binding.createUserPb
        uid = binding.etUID
        name = binding.etName
        collegeName = binding.etCollegeName
        domainSpinner = binding.spinnerDomain
        hackathons = binding.etHackathons
        createUserBtn = binding.createUserBtn

        createUserBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
        createUserBtn.setOnClickListener {
            if (uid.text.toString().isEmpty()) {
                uid.error = getString(R.string.fill_this_field)
            } else if (name.text.toString().isEmpty()) {
                name.error = getString(R.string.fill_this_field)
            } else if (collegeName.text.toString().isEmpty()) {
                collegeName.error = getString(R.string.fill_this_field)
            } else if (hackathons.text.toString().isEmpty()) {
                hackathons.error = getString(R.string.fill_this_field)
            } else {
                progressBar.visibility = View.VISIBLE
                createUserBtn.isClickable = false
                val user = User().apply {
                    uid = this@CreateUserActivity.uid.text.toString()
                    name = this@CreateUserActivity.name.text.toString()
                    // Add collegeName and domainSpinner to user metadata if needed
                }
                CometChatUIKit.createUser(user, object : CometChat.CallbackListener<User>() {
                    override fun onSuccess(user: User) {
                        login(user)
                    }

                    override fun onError(e: CometChatException) {
                        createUserBtn.isClickable = true
                        Toast.makeText(this@CreateUserActivity, "Failed to create user", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                    }
                })
            }
        }
        setUpUI()
        setUpSpinner()
    }

    private fun login(user: User) {
        CometChatUIKit.login(user.uid, object : CometChat.CallbackListener<User?>() {
            override fun onSuccess(user: User?) {
                fetchDefaultObjects()
                startActivity(Intent(this@CreateUserActivity, HomeActivity::class.java))
                finishAffinity()
            }

            override fun onError(e: CometChatException) {
                progressBar.visibility = View.GONE
                createUserBtn.isClickable = true
                if (uid != null) Snackbar.make(uid.rootView, "Unable to login", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again") {
                        startActivity(Intent(this@CreateUserActivity, LoginActivity::class.java))
                    }.show()
            }
        })
    }

    private fun setUpUI() {
        if (isNightMode(this)) {
            changeTextColorToWhite(this, binding.tvTitle)
            changeTextColorToWhite(this, binding.tvDes2)
            uid.setTextColor(ContextCompat.getColor(this, R.color.white))
            name.setTextColor(ContextCompat.getColor(this, R.color.white))
            collegeName.setTextColor(ContextCompat.getColor(this, R.color.white))
            hackathons.setTextColor(ContextCompat.getColor(this, R.color.white))
            parentView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_background_dark))
        } else {
            changeTextColorToBlack(this, binding.tvTitle)
            changeTextColorToBlack(this, binding.tvDes2)
            parentView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_background))
        }
    }

    private fun setUpSpinner() {
        // List of all items including the placeholder
        val domains = listOf(
            "Web Development",
            "Mobile Development",
            "Artificial Intelligence",
            "Embedded Systems",
            "Robotics"
        )

        // Create a separate list for dropdown items, excluding the placeholder
        val dropdownItems = domains.drop(0) // Drops the first item from the list

        // Set up the adapter with the dropdown items
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dropdownItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        domainSpinner.adapter = adapter
    }
}
