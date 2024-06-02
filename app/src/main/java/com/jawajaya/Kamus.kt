package com.jawajaya

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jawajaya.Database.Api
import com.jawajaya.Database.KamusData
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Kamus : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: KamusAdapter
    private var dataList = ArrayList<KamusData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kamus)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView_kamus)

        recyclerView.layoutManager = LinearLayoutManager(this)

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
            .url(Api.getReadUrlMenuKamus())
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
                            val kamusData = KamusData(
                                itemObject.getString("ngoko"),
                                itemObject.getString("madya"),
                                itemObject.getString("inggil")
                            )
                            dataList.add(kamusData)
                        }

                        runOnUiThread {
                            // Initialize adapter
                            adapter = KamusAdapter(this@Kamus, dataList)
                            recyclerView.adapter = adapter
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
