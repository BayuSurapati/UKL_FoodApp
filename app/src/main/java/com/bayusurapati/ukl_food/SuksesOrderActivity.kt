package com.bayusurapati.ukl_food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.bayusurapati.ukl_food.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_sukses_order.*

class SuksesOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sukses_order)

        val restaurentModel: RestaurentModel? = intent.getParcelableExtra("RestaurentModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurentModel?.name)
        actionBar?.setSubtitle(restaurentModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        buttonDone.setOnClickListener{
            setResult(RESULT_OK)
            finish()
        }
    }
}