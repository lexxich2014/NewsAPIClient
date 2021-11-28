package com.gmail.lexxich2014.newsapiclient

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle

import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

val REQUEST_KEY_LANGUAGES = "REQUEST_LANGUAGE"
val REQUEST_KEY_PAGE_SIZE="REQUEST_PAGE_SIZE"
val ARG_REQUEST_TYPE="REQUEST_TYPE"
val KEY_LANGUAGE = "KEY_LANGUAGE"
val KEY_PAGE_SIZE="KEY_PAGE_SIZE"
 class  ListDialogFragment private constructor(): DialogFragment() {

    private lateinit var languages: Array<String>
    private lateinit var pageSizes: Array<String>
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentCalledBy= arguments?.getString(ARG_REQUEST_TYPE)
            ?: throw IllegalStateException("the type for the ${LanguagesDialogFragment@this::class} was not passed")

        builder=AlertDialog.Builder(requireContext())
        when(fragmentCalledBy){
            REQUEST_KEY_LANGUAGES->{
                languages=resources.getStringArray(R.array.languages)
                builder.setTitle(resources.getString(R.string.select_language_text))
                    .setItems(languages){dialog,which->
                        val result = Bundle().apply { putString(KEY_LANGUAGE, languages[which]) }
                        requireActivity().supportFragmentManager.setFragmentResult(
                            REQUEST_KEY_LANGUAGES,
                            result
                        )
                        dismiss()
                    }
            }
            REQUEST_KEY_PAGE_SIZE->{
                pageSizes=resources.getStringArray(R.array.page_size)
                builder.setTitle(resources.getString(R.string.select_page_size))
                    .setItems(pageSizes){dialog,which->
                        val result=Bundle().apply { putString(KEY_PAGE_SIZE,pageSizes[which]) }
                        requireActivity().supportFragmentManager.setFragmentResult(
                            REQUEST_KEY_PAGE_SIZE,
                            result
                        )
                        dismiss()
                    }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return builder.create()
    }
companion object{
    fun newInstance(requestKey: String): DialogFragment{
        val args=Bundle().apply {
            putString(ARG_REQUEST_TYPE,requestKey)
        }
        return ListDialogFragment().apply { arguments=args }
    }
}


}