package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class InitFragment : Fragment() {

    interface Callbacks {
        fun OnApiKeyEntered(key: String)
    }

    private lateinit var callback: Callbacks
    private lateinit var apiKeyView: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = requireActivity() as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_init, container, false)
        apiKeyView = view.findViewById(R.id.apikey_text_view)
        loginBtn = view.findViewById(R.id.login_btn)
        loginBtn.setOnClickListener {
            val key = apiKeyView.text.toString()
            if (key != null && key.isNotBlank()) {
                callback.OnApiKeyEntered(key)
            } else {
                Toast.makeText(requireContext(), R.string.api_key_is_blank, Toast.LENGTH_SHORT)
            }
        }
        return view
    }
}