package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.gmail.lexxich2014.newsapiclient.view.DateIntervalPickerView

const val REQUEST_FILTER = "request_filter_data"
const val ARG_START_DATE="start_filtered_date"
const val ARG_END_DATE="end_filtered_date"
const val ARG_SEARCH="search_string"
const val ARG_LANGUAGE="language_for_request"

class FilterDialogFragment : DialogFragment() {

    lateinit var acceptBtn: Button
    lateinit var datePickerView: DateIntervalPickerView
    lateinit var searchView: EditText
    lateinit var languageRadioGroup: RadioGroup

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_filters, container, false)
        datePickerView=view.findViewById(R.id.datePickerView)
        searchView=view.findViewById(R.id.searchView)
        languageRadioGroup=view.findViewById(R.id.radioGroup)
        acceptBtn=view.findViewById(R.id.fragment_filters__accept_btn)
        acceptBtn.setOnClickListener{
            val args=Bundle()
            args.putSerializable(ARG_START_DATE,datePickerView.selectedStartDate)
            args.putSerializable(ARG_END_DATE,datePickerView.selectedEndDate)
            args.putString(ARG_SEARCH,searchView.text.toString())
            val lang=when(languageRadioGroup.checkedRadioButtonId){
                R.id.enBtn->"en"
                R.id.ruBtn->"ru"
                R.id.frBtn->"fr"
                R.id.itBtn->"it"
                else->"ru"
            }
            args.putString(ARG_LANGUAGE,lang)
            requireActivity().supportFragmentManager.setFragmentResult(REQUEST_FILTER,args)
            dismiss()
        }
        return view
    }

    companion object {
        fun newInstance(): FilterDialogFragment {
            return FilterDialogFragment()
        }
    }

}