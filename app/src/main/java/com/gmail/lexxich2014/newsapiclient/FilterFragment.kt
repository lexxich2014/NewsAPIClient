package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.lexxich2014.newsapiclient.view.DateIntervalPickerView

const val REQUEST_FILTER = "request_filter_data"
const val ARG_FROM_DATE = "start_filtered_date"
const val ARG_TO_DATE = "end_filtered_date"
const val ARG_SEARCH = "search_string"
const val ARG_SEARCH_ONLY_TITLE = "search_only_title"
const val ARG_SORT_BY = "sort_by"
const val ARG_PAGE_SIZE = "page_size"
const val ARG_LANGUAGE = "language_for_request"
const val ARG_PAGE = "page_to_load"

class FilterFragment : Fragment() {

    lateinit var acceptBtn: Button
    lateinit var datePickerView: DateIntervalPickerView
    lateinit var startDateView: TextView
    lateinit var endDateView: TextView
    lateinit var searchInTitleView: EditText
    lateinit var searchView: EditText
    lateinit var languageView: TextView
    lateinit var pageSizeView: TextView
    lateinit var sortView: RadioGroup

    private val filterViewModel: FilterViewModel by lazy{
        ViewModelProvider(this).get(FilterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filters, container, false)
        languageView = view.findViewById(R.id.language_tv)
        languageView.text=filterViewModel.language
        pageSizeView = view.findViewById(R.id.page_size_text_view)
        pageSizeView.text=filterViewModel.pageSize
        pageSizeView.setOnClickListener {
            ListDialogFragment.newInstance(REQUEST_KEY_PAGE_SIZE)
                .show(requireActivity().supportFragmentManager, null)
        }
        languageView.setOnClickListener {
            ListDialogFragment.newInstance(REQUEST_KEY_LANGUAGES)
                .show(requireActivity().supportFragmentManager, null)
        }
        datePickerView = view.findViewById(R.id.datePickerView)
        searchView = view.findViewById(R.id.searchView)
        searchInTitleView = view.findViewById(R.id.qIn_textview)
        startDateView = view.findViewById(R.id.start_date_tv)
        endDateView = view.findViewById(R.id.end_date_tv)
        sortView = view.findViewById(R.id.sort_by_radioGroup)
        datePickerView.setOnTimeTextChangedListener(object :
            DateIntervalPickerView.OnTimeTextChangedListener {
            override fun onTimeIntervalTextChanged(startText: String, endText: String) {
                startDateView.text = startText
                endDateView.text = endText
            }

        })

        acceptBtn = view.findViewById(R.id.fragment_filters__accept_btn)
        acceptBtn.setOnClickListener {
            val args = Bundle()
            args.putSerializable(ARG_FROM_DATE, datePickerView.selectedStartDate)
            args.putSerializable(ARG_TO_DATE, datePickerView.selectedEndDate)
            args.putString(ARG_SEARCH_ONLY_TITLE, searchInTitleView.text.toString())
            args.putString(ARG_SEARCH, searchView.text.toString())
            args.putString(ARG_PAGE_SIZE, filterViewModel.pageSize)
            filterViewModel.sortBy = when (sortView.checkedRadioButtonId) {
                R.id.relevancy_rb -> "relevancy"
                R.id.popularity_rb -> "popularity"
                R.id.publishedAt_rb -> "publishedAt";
                else -> "publishedAt"
            }
            args.putString(ARG_SORT_BY, filterViewModel.sortBy)
            args.putString(ARG_LANGUAGE, filterViewModel.language)
            requireActivity().supportFragmentManager.setFragmentResult(REQUEST_FILTER, args)
            requireActivity().supportFragmentManager.popBackStack()
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_LANGUAGES,
            viewLifecycleOwner,
            { requestKey, result ->
                filterViewModel.language = result.getString(KEY_LANGUAGE) ?: ""
                languageView.text = filterViewModel.language
            }
        )
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_PAGE_SIZE,
            viewLifecycleOwner,
            { requestKey, result ->
                filterViewModel.pageSize = result.getString(KEY_PAGE_SIZE) ?: "100"
                pageSizeView.text = filterViewModel.pageSize
            }
        )

        return view
    }



    companion object {
        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }

}