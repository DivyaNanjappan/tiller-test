package com.tillertest.views

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.tiller.test.R
import com.tiller.test.databinding.ActivityMainBinding
import com.tillertest.BaseApp
import com.tillertest.response.AppResult
import com.tillertest.utils.extractAccessToken
import com.tillertest.utils.hide
import com.tillertest.utils.show
import com.tillertest.utils.stopRefreshing
import com.tillertest.utils.toast
import com.tillertest.viewmodel.AppViewModel
import com.tillertest.views.viewitem.CountryAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).netComponent.inject(this)
        (application as BaseApp).netComponent.inject(viewModel)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressDialog()

        initView()

        setSearchView()

        setSwipeToRefresh()

        setObservables()
    }

    private fun setSwipeToRefresh() {
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                getCountries(true)
            }
        }
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                countryAdapter?.filter?.filter(newText)
                return false
            }
        })

        countryAdapter?.filter?.filter("")
    }

    private var countryAdapter: CountryAdapter? = null
    private fun initView() {
        countryAdapter = CountryAdapter(emptyList())
        binding.recyclerViewCountry.apply {
            layoutManager =
                LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL, false
                )
            itemAnimator = DefaultItemAnimator()
            adapter = countryAdapter
        }

        binding.buttonRetry.setOnClickListener { onRetry() }
    }

    fun onRetry() {
        setObservables()
    }

    private fun getCountries(isRefresh: Boolean = false) {
        if (!isRefresh) showProgressDialog()
        binding.buttonRetry.hide()
        viewModel.getCountries().observe(this) { result ->
            dismissProgressDialog()
            if (isRefresh) binding.swipeRefresh.stopRefreshing()
            when (result) {
                is AppResult.Success -> {
                    Log.d("Response", result.data.toString())
                    countryAdapter?.reload(result.data.sortedBy { it.displayOrder })
                }

                is AppResult.Error -> {
                    toast(result.message)
                    countryAdapter?.reload(emptyList())
                    binding.buttonRetry.show()
                }
            }
        }
    }

    private fun getAccessToken() {
        showProgressDialog()
        viewModel.fetchAccessToken()
    }

    private fun setObservables() {
        getAccessToken()

        viewModel.accessTokenLiveData?.observe(this) { result ->
            dismissProgressDialog()
            when (result) {
                is AppResult.Success -> {
                    val response = result.data.toString()
                    viewModel.appPreferenceManager.accessToken = response.extractAccessToken()
                    getCountries()
                }

                is AppResult.Error -> {
                    toast(result.message)
                }
            }
        }
    }

    private var dialog: Dialog? = null
    private fun setProgressDialog() {
        dialog = Dialog(this)
        dialog?.setContentView(R.layout.view_progress_indicator)
        dialog?.setCancelable(true)
        if (dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        }
    }

    private fun showProgressDialog() {
        if (dialog != null && !dialog?.isShowing!!) {
            dialog?.show()
        }
    }

    private fun dismissProgressDialog() {
        if (dialog != null) {
            dialog?.dismiss()
        }
    }
}