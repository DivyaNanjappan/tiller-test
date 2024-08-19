package com.tillertest.views.viewitem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tiller.test.databinding.ViewEmptyBinding
import com.tiller.test.databinding.ViewItemCountryBinding
import com.tillertest.response.CountryItem

class CountryAdapter(
    private var countries: List<CountryItem>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var searchList: List<CountryItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {

            VIEW_TYPE_EMPTY -> {
                val binding = ViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = EmptyViewHolder(binding)
            }

            VIEW_TYPE -> {
                val binding = ViewItemCountryBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = ViewHolder(binding)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            VIEW_TYPE_EMPTY -> {
                val emptyViewHolder = holder as EmptyViewHolder
            }

            VIEW_TYPE -> {
                val viewHolder = holder as ViewHolder
                searchList?.get(position)?.let { viewHolder.init(it) }
            }
        }
    }

    private fun isListEmpty(): Boolean {
        return searchList.isNullOrEmpty()
    }

    override fun getItemViewType(position: Int): Int =
        if (isListEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE

    override fun getItemCount(): Int = if (isListEmpty()) 1 else searchList?.size!!

    @SuppressLint("NotifyDataSetChanged")
    fun reload(countries: List<CountryItem>?) {
        this.countries = countries
        this.searchList = countries
        notifyDataSetChanged()
    }

    /**
     * Filter - Search
     */
    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString().trim()
                searchList = if (charString.isEmpty()) {
                    countries
                } else {
                    val filteredList = ArrayList<CountryItem>()

                    countries?.forEach { country ->
                        if (country.countryName?.lowercase()
                                ?.contains(charSequence, ignoreCase = true) == true ||
                            country.countryCode?.lowercase()
                                ?.contains(charSequence, ignoreCase = true) == true
                        ) {
                            filteredList.add(country)
                        }
                    }

                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (filterResults.values != null) {
                    searchList = filterResults.values as List<CountryItem>
                }
                searchList?.let {
                    notifyDataSetChanged()
                }
            }
        }
    }

    internal inner class ViewHolder(private val binding: ViewItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun init(countryItem: CountryItem) {
            binding.apply {
                textViewCountryName.text = countryItem.countryName
                textViewIsoCode.text = countryItem.countryCode
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE = 1
    }
}
