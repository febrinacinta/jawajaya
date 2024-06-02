package com.jawajaya.Database

import com.jawajaya.DetailCerita
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.jawajaya.R
import java.util.*
import kotlin.collections.ArrayList

class CeritaAdapter(private val context: Context, private var dataList: ArrayList<CeritaData>) :
    BaseAdapter(), Filterable {

    private var filteredDataList: ArrayList<CeritaData> = ArrayList(dataList)
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return filteredDataList.size
    }

    override fun getItem(position: Int): Any {
        return filteredDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = inflater.inflate(R.layout.item_layout, parent, false)
        }

        val textView: TextView = view!!.findViewById(R.id.textView)
        val currentItem = filteredDataList[position]
        textView.text = currentItem.judul ?: "N/A"

        // Menambahkan OnClickListener ke setiap item
        view.setOnClickListener {
            val intent = Intent(context, DetailCerita::class.java)
            intent.putExtra("cerita_data", currentItem) // Mengirim data CeritaData yang sesuai
            context.startActivity(intent)
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<CeritaData>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(dataList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                    for (item in dataList) {
                        if (item.judul?.toLowerCase(Locale.getDefault())?.contains(filterPattern) == true) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDataList.clear()
                filteredDataList.addAll(results?.values as ArrayList<CeritaData>)
                notifyDataSetChanged()
            }
        }
    }
}
