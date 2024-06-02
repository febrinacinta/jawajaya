package com.jawajaya

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.SearchView
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jawajaya.Database.Api
import com.jawajaya.Database.TerjemahAdapter
import com.jawajaya.Database.TerjemahData
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.jawajaya.R

class TerjemahDetailJW : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TerjemahAdapter
    private var dataList = ArrayList<TerjemahData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terjemah_detailjw)

        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)

        mAdapter = TerjemahAdapter(this, dataList)
        mAdapter.setOnClickListener(object : TerjemahAdapter.OnClickListener {
            override fun onItemClick(item: TerjemahData) {
                showPopup(item.indo ?: "N/A")
            }
        })
        mRecyclerView.adapter = mAdapter

        val searchView = findViewById<SearchView>(R.id.searchView_jawa)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { mAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { mAdapter.filter(it) }
                return true
            }
        })

        fetchData()
    }

    private val showError: (String) -> Unit = { message ->
        runOnUiThread {
            Toast.makeText(this@TerjemahDetailJW, message, Toast.LENGTH_SHORT).show()
            Log.e("TerjemahDetailJW", message)
        }
    }

    private fun fetchData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Api.getReadUrlMenuTerjemahan())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (responseData != null) {
                    Log.d("TerjemahDetailJW", "Response received: $responseData")
                    try {
                        val jsonObject = JSONObject(responseData)
                        val jsonArray = jsonObject.getJSONArray("result")

                        val gson = Gson()
                        for (i in 0 until jsonArray.length()) {
                            val itemObject = jsonArray.getJSONObject(i)
                            val terjemahData = gson.fromJson(itemObject.toString(), TerjemahData::class.java)
                            dataList.add(terjemahData)
                        }

                        runOnUiThread {
                            mAdapter.setData(dataList)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showError("Error parsing data: ${e.message}")
                    } finally {
                        response.body?.close()
                    }
                } else {
                    showError("Failed to load data: Response is empty")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                showError("Network error: ${e.message}")
            }
        })
    }

    private fun showPopup(indoText: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val textView = dialogView.findViewById<TextView>(R.id.dialogTextView)
        textView.text = indoText

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
