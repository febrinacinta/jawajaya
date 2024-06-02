package com.jawajaya

import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.jawajaya.Database.Api
import com.jawajaya.Database.CeritaAdapter
import com.jawajaya.Database.CeritaData
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Cerita : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CeritaAdapter
    private var dataList = ArrayList<CeritaData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerita)

        listView = findViewById(R.id.list_cerita)
        searchView = findViewById(R.id.searchView_cerita)

        // Fetch data from the database
        fetchData()

        // Set up search view listener
        setupSearchView()
    }

    private val showError: (String) -> Unit = { message ->
        runOnUiThread {
            // Handle error UI feedback
        }
    }

    private fun fetchData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Api.getReadUrlMenuCerita())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (responseData != null) {
                    try {
                        val jsonObject = JSONObject(responseData)
                        val jsonArray = jsonObject.getJSONArray("result")

                        // Clear existing data
                        dataList.clear()

                        for (i in 0 until jsonArray.length()) {
                            val itemObject = jsonArray.getJSONObject(i)
                            val ceritaData = CeritaData(
                                itemObject.getString("judul"),
                                itemObject.getString("text_cerita")
                            )
                            dataList.add(ceritaData)
                        }

                        runOnUiThread {
                            // Initialize adapter
                            adapter = CeritaAdapter(this@Cerita, dataList)
                            listView.adapter = adapter
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
}
