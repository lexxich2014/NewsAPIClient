package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lexxich2014.newsapiclient.responsemodel.Article
import com.gmail.lexxich2014.newsapiclient.responsemodel.ResponseModel
import com.gmail.lexxich2014.newsapiclient.utils.AppUtils
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val ARG_API_KEY = "api_key_arg"

class ArticleListFragment : Fragment() {
    private lateinit var filterBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    private val articleListViewModel: ArticleListViewModel by lazy {
        ViewModelProvider(this).get(ArticleListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val key = arguments?.getString(ARG_API_KEY)
        articleListViewModel.apiKey = key ?: ""

        articleAdapter = ArticleAdapter()
/*
        articleListViewModel.apiService.getArticles(
            "Apple",
            "2021-11-22",
            "2021-11-22",
            "ru",
            "6afc0d3ed16147bab43360d8884d00c7"
        ).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val receivedItems = response.body()?.articles
                if (receivedItems != null) {
                    articleListViewModel.loadedItems.addAll(receivedItems)
                    articleAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

            }

        })
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
        recyclerView = view.findViewById(R.id.fragment_article_list__recyclerView)
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy>0 && filterBtn.isShown){
                    filterBtn.visibility=View.INVISIBLE
                }
                if(dy<0 && !filterBtn.isShown){
                    filterBtn.visibility=View.VISIBLE
                }
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
        filterBtn=view.findViewById(R.id.fragment_article__filter_btn)
        filterBtn.setOnClickListener{
            val fragment=FilterDialogFragment.newInstance()
           // fragment.show(requireActivity().supportFragmentManager,null)
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(REQUEST_FILTER,viewLifecycleOwner){
                requestKey,result->
            if(requestKey== REQUEST_FILTER){
                articleListViewModel.sendRequest(result).enqueue(object: Callback<ResponseModel>{
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        val receivedItems = response.body()?.articles
                        if (receivedItems != null) {
                            articleListViewModel.loadedItems.clear()
                            articleListViewModel.loadedItems.addAll(receivedItems)
                            articleAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

                    }

                })
            }
        }

        return view
    }



    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        lateinit var article: Article
        var titleView: TextView = view.findViewById(R.id.item_article_title)
        var descriptionView: TextView = view.findViewById(R.id.item_article_description)
        var imageView: ImageView = view.findViewById(R.id.item_article_imageview)
        var authorView: TextView = view.findViewById(R.id.item_article_author)
        var dateView: TextView = view.findViewById(R.id.item_article_date)
        var sourceNameView: TextView = view.findViewById(R.id.item_article_sourcename)

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(article: Article) {
            this.article = article
            article.also {
                titleView.text = it.title
                descriptionView.text = it.description
                Picasso.get().load(it.urlToImage).into(imageView)
                authorView.text = it.author
                dateView.text = AppUtils.parseDateStringFromUTC(it.publishedAt)
                sourceNameView.text = it.source.name//!!!!!!!!!!!!
            }
        }

        override fun onClick(v: View?) {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, WebViewFragment.newInstance(article.url))
                .addToBackStack(null)
                .commit()
        }
    }

    inner class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val itemListView = layoutInflater.inflate(R.layout.item_article, parent, false)
            return ArticleViewHolder(itemListView)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            holder.bind(articleListViewModel.loadedItems[position])
        }

        override fun getItemCount(): Int {
            return articleListViewModel.loadedItems.size
        }

    }

    companion object {
        fun newInstance(apiKey: String): ArticleListFragment {
            val args = Bundle().apply {
                putString(ARG_API_KEY, apiKey)
            }
            return ArticleListFragment().apply { arguments = args }
        }
    }


}