package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gmail.lexxich2014.newsapiclient.view.DateIntervalPickerView

const val REQUEST_FILTER = "request_filter_data"
const val ARG_START_DATE="start_filtered_date"
const val ARG_END_DATE="end_filtered_date"
const val ARG_SEARCH="search_string"
const val ARG_LANGUAGE="language_for_request"

class FilterDialogFragment : Fragment() {

    var selectedLanguage: String="ru"
    var pageSize: Int=100

    lateinit var acceptBtn: Button
    lateinit var datePickerView: DateIntervalPickerView
    lateinit var startDateView: TextView
    lateinit var endDateView: TextView
    lateinit var searchView: EditText
    lateinit var languageView: TextView
    lateinit var pageSizeView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_filters, container, false)
        languageView=view.findViewById(R.id.language_tv)
        languageView.text=selectedLanguage
        pageSizeView=view.findViewById(R.id.page_size_text_view)
        pageSizeView.text=pageSize.toString()
        pageSizeView.setOnClickListener{

        }
        languageView.setOnClickListener{
            ListDialogFragment.newInstance(REQUEST_KEY_LANGUAGES).show(requireActivity().supportFragmentManager,null)
            //LanguagesDialogFragment().show(requireActivity().supportFragmentManager,null)
        }
        datePickerView=view.findViewById(R.id.datePickerView)
        searchView=view.findViewById(R.id.searchView)
        startDateView=view.findViewById(R.id.start_date_tv)
        endDateView=view.findViewById(R.id.end_date_tv)
        datePickerView.setOnTimeTextChangedListener(object : DateIntervalPickerView.OnTimeTextChangedListener{
            override fun onTimeIntervalTextChanged(startText: String, endText: String) {
                startDateView.text=startText
                endDateView.text=endText
            }

        })

        acceptBtn=view.findViewById(R.id.fragment_filters__accept_btn)
        acceptBtn.setOnClickListener{
            val args=Bundle()
            args.putSerializable(ARG_START_DATE,datePickerView.selectedStartDate)
            args.putSerializable(ARG_END_DATE,datePickerView.selectedEndDate)
            args.putString(ARG_SEARCH,searchView.text.toString())
            args.putString(ARG_LANGUAGE,selectedLanguage)
            requireActivity().supportFragmentManager.setFragmentResult(REQUEST_FILTER,args)
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_LANGUAGES,
            viewLifecycleOwner,
            { requestKey, result ->
                selectedLanguage=result.getString(KEY_LANGUAGE) ?: ""
                languageView.text=selectedLanguage
            }
        )
        return view
    }

    companion object {
        fun newInstance(): FilterDialogFragment {
            return FilterDialogFragment()
        }
    }

}