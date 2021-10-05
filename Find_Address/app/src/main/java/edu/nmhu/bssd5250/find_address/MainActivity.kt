package edu.nmhu.bssd5250.find_address

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var loc: EditText? = null

    companion object{
        const val LOCATION_RESULT:String = "com.sbb5250.multipleactivities.LOCATION_RESULT"
    }
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                loc?.setText(intent?.getStringExtra(LOCATION_RESULT).toString())
                // Handle the Intent
                Log.i("MACTResult1", intent?.getStringExtra(LOCATION_RESULT).toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loc = EditText(this).apply {
            hint = "Enter a Street Address"
        }

        val submitButton = Button(this).apply {
            "Submit".also { text = it }
            setOnClickListener {
                val passableData = Intent(applicationContext, ShowMap::class.java).apply {
                    putExtra(ShowMap.LOCATION_REQUESTED, "#"+ loc!!.text.toString())

                }
                startForResult.launch(passableData)
            }
        }

        val linearLayout = LinearLayoutCompat(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT)
            orientation = LinearLayoutCompat.VERTICAL
            addView(loc)
            addView(submitButton)
        }

        //look up the main layout by the id we just gave it
        findViewById<ConstraintLayout>(R.id.main_layout).apply {
            addView(linearLayout)
        }
    }
}