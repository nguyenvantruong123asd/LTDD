package com.appveg.farmfamily.ui.garden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.vegetable.Vegetable
import kotlinx.android.synthetic.main.activity_select_veg_garden.*

class SelectVegGardenActivity : AppCompatActivity() {
    private val activity = this@SelectVegGardenActivity
    private var vegForGardens: ArrayList<Vegetable> = ArrayList()
    private lateinit var database: Database

    private lateinit var grid : GridView
    private lateinit var vegCountSelect : TextView
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_veg_garden)
        vegCountSelect = activity.findViewById(R.id.count_select_veg)

        grid = activity.findViewById(R.id.gird_veg_for_garden_select)
        vegForGardens = getListVegForGarden()

        var gardenId: Int = getDataFromItent()
        grid.adapter = this.activity?.let { SelectVegGardenAdapter (it, vegForGardens,gardenId,count) }

        /*action button*/
        actionButton()
    }
    private fun actionButton() {
        /*event cancel*/
        btn_veg_cancel.setOnClickListener {
            activity.finish()
        }

    }
    /**
     * the method to display batch
     */
    private fun getListVegForGarden() : ArrayList<Vegetable>{
        database = Database(activity)
        var gardenId: Int = getDataFromItent()
        vegForGardens = database.findAllVegetableForGarden(gardenId)
        if (vegForGardens.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.Veg_Empty_Message_vi), Toast.LENGTH_LONG).show()
        }else{
            for (item in 0 until vegForGardens.size){
                if(0 != vegForGardens[item].gardenId){
                    count++
                }
            }
            vegCountSelect.text = getString(R.string.firt_count_veg) +  "($count)" + getString(R.string.last_count_veg)
        }
        return vegForGardens
    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): Int {
        val bundle: Bundle = intent.extras
        return bundle.get("garden_id") as Int
    }
}
