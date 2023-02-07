package com.example.networkcall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.networkcall.adapter.MovieItemAdapter
import com.example.networkcall.data.DataItem
import com.example.networkcall.database.TvShowDataHelper
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val list: ArrayList<DataItem> = ArrayList()
    private var list2 :ArrayList<DataItem> = ArrayList()
    private lateinit var mAdapter : MovieItemAdapter
    private var dbHelper: TvShowDataHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
        mAdapter = MovieItemAdapter()
        val recyclerView :RecyclerView = findViewById(R.id.tv)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(MovieItemAdapter.SwipeToDeleteCallback(mAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        dbHelper = TvShowDataHelper(this)
        fetchDataFromDatabase()

    }
    private fun fetchDataFromDatabase(){
                list2 = dbHelper!!.getItem() as ArrayList<DataItem>
                mAdapter.updateList(list2)
                Log.d("list" , list2.toString())
    }

    private fun getData() {
        val url = "https://api.tvmaze.com/singlesearch/shows?q=girls&embed=episodes"
        val request = StringRequest(url,
            { response ->
                val myRequest = JSONObject(response)
                val myData = myRequest.getJSONObject("_embedded")
                val array = myData.getJSONArray("episodes")
                try {
                    for (i in 0 until array.length()) {
                       val jsonObject = array.getJSONObject(i)
                        val name = jsonObject.get("name")
                        val summary = jsonObject.get("summary").toString()
                        val date = jsonObject.get("airdate")
                        val id = jsonObject.get("id")
                        val image = jsonObject.getJSONObject("image").get("medium")

                        val plainText = Html.fromHtml(summary,Html.FROM_HTML_MODE_LEGACY).toString()

                        val data = DataItem()
                        data.image = image.toString()
                        data.name = name.toString()
                        data.summary =plainText
                        data.id = id.toString().toInt()
                        data.airdate = date.toString()
                        list.add(data)

                        if (dbHelper?.checkIfExist(id as Int?) == true) {
                            Log.d("exist", "Already Exist")
                        } else {
                            dbHelper?.add(data) as Boolean
//

                        }

                    }

                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },
            { }
         )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

    }
}

