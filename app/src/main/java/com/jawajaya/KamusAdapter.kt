package com.jawajaya

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jawajaya.Database.KamusData
import com.jawajaya.R
import java.util.*
import kotlin.collections.ArrayList

class KamusAdapter(private val context: Context, private var dataList: ArrayList<KamusData>) :
    RecyclerView.Adapter<KamusAdapter.ViewHolder>(), Filterable {

    private var filteredDataList: ArrayList<KamusData> = ArrayList(dataList)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNgoko: TextView = itemView.findViewById(R.id.textViewNgoko)
        val textViewMadya: TextView = itemView.findViewById(R.id.textViewMadya)
        val textViewInggil: TextView = itemView.findViewById(R.id.textViewInggil)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_kamus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filteredDataList[position]
        holder.textViewNgoko.text = currentItem.ngoko
        holder.textViewMadya.text = currentItem.madya
        holder.textViewInggil.text = currentItem.inggil
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<KamusData>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(dataList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                    for (item in dataList) {
                        if (item.ngoko.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                            item.madya.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                            item.inggil.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
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
                filteredDataList.addAll(results?.values as ArrayList<KamusData>)
                notifyDataSetChanged()
            }
        }
    }
}
