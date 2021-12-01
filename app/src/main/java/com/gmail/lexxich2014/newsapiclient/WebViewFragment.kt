package com.gmail.lexxich2014.newsapiclient

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

const val ARG_URL = "ARG_URL"

class WebViewFragment : Fragment() {

    lateinit var webView: WebView
    lateinit var url:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_web_view,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_item_share){
            val intent=Intent(Intent.ACTION_SEND).apply {
                type="text/plain"
                putExtra(Intent.EXTRA_TEXT,url)
                putExtra(Intent.EXTRA_SUBJECT,R.string.shared_from)
            }
            if(intent.resolveActivity(requireActivity().packageManager)!=null){
                startActivity(intent)
            }
        }

        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        webView = view.findViewById(R.id.webView)
        webView.webViewClient=AppWebClient()
        webView.settings.javaScriptEnabled=true
        url=arguments?.getString(ARG_URL) ?: "www.google.com"
        webView.loadUrl(url)
        return webView
    }

    companion object {
        fun newInstance(url: String): WebViewFragment {
            val args = Bundle().apply { putString(ARG_URL, url) }
            return WebViewFragment().apply { arguments = args }
        }
    }


    class AppWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (view != null && request != null) {
                view.loadUrl(request.url.toString())
            }
            return true
        }

        //for elder devices
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if(view!=null&&url!=null){
                view.loadUrl(url)
            }
            return true
        }
    }


}