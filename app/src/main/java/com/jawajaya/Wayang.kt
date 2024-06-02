package com.jawajaya


import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jawajaya.Database.Api
import com.jawajaya.Database.WayangAdapter
import com.jawajaya.Database.WayangData
import com.jawajaya.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Wayang : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: WayangAdapter
    private var dataList = ArrayList<WayangData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wayang)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView_wayang)

        // Set up search view listener
        setupSearchView()

        // Fetch data from the database
        fetchData()
    }

    private val showError: (String) -> Unit = { message ->
        // Handle error UI feedback
    }

    private fun fetchData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Api.getReadUrlMenuWayang())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    try {
                        val responseData = responseBody.string()
                        val jsonObject = JSONObject(responseData)
                        val jsonArray = jsonObject.getJSONArray("result")

                        // Clear existing data
                        dataList.clear()

                        for (i in 0 until jsonArray.length()) {
                            val itemObject = jsonArray.getJSONObject(i)
                            val wayangData = WayangData(
                                itemObject.getString("tokoh_wayang"),
                                itemObject.getString("deskripsi_singkat")
                            )
                            dataList.add(wayangData)
                        }

                        runOnUiThread {
                            // Initialize adapter
                            adapter = WayangAdapter(this@Wayang, dataList)
                            recyclerView.adapter = adapter

                            // Set up item click listener for recycler view
                            setupRecyclerViewClickListener()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showError("Error parsing data: ${e.message}")
                    } finally {
                        responseBody.close()
                    }
                } ?: showError("Failed to load data: Response body is null")
            }

            override fun onFailure(call: Call, e: IOException) {
                showError("Network error: ${e.message}")
            }
        })
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupRecyclerViewClickListener() {
        adapter.setOnClickListener(object : WayangAdapter.OnClickListener {
            override fun onItemClick(item: WayangData) {
                showPopup(item.deskripsiSingkat)
            }
        })
    }

    private fun showPopup(description: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val textView = dialogView.findViewById<TextView>(R.id.dialogTextView)
        textView.text = description

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
