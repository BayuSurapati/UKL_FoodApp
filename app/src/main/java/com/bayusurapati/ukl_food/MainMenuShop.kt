package com.bayusurapati.ukl_food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bayusurapati.ukl_food.adapter.RestaurentListAdapter
import com.bayusurapati.ukl_food.models.RestaurentModel
import com.google.gson.Gson
import java.io.*

class MainMenuShop : AppCompatActivity(), RestaurentListAdapter.RestaurentListClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_shop)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("List Restaurant")
        val restaurentModel = getRestaurentData()
        initRecycleView(restaurentModel)
    }
    private fun initRecycleView(restaurentList: List<RestaurentModel?>?){
        val recyclerViewRestaurent = findViewById<RecyclerView>(R.id.recyclerViewRestaurent)
        recyclerViewRestaurent.layoutManager = LinearLayoutManager(this)
        val adapter = RestaurentListAdapter(restaurentList, this)
        recyclerViewRestaurent.adapter = adapter
    }

    private fun getRestaurentData():List<RestaurentModel?>?{
        val inputStream: InputStream = resources.openRawResource(R.raw.restaurent)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try{
            val reader: Reader = BufferedReader(InputStreamReader(inputStream,"UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it }!= -1){
                writer.write(buffer,0,n)
            }

        }catch (e: Exception){}
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val restaurentModel = gson.fromJson<Array<RestaurentModel>>(jsonStr, Array<RestaurentModel>::class.java).toList()
        return restaurentModel
    }

    override fun onItemClick(restaurentModel: RestaurentModel) {
        val intent = Intent(this@MainMenuShop, MenuRestaurantActivity::class.java)
        intent.putExtra("RestaurentModel", restaurentModel)
        startActivity(intent)
    }
}