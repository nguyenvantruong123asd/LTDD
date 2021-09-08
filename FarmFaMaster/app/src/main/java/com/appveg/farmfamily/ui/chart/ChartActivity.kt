@file:Suppress("DEPRECATION")

package com.appveg.farmfamily.ui.chart


import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Half.toFloat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.appveg.farmfamily.R
import com.appveg.farmfamily.ui.Screenshot
import com.appveg.farmfamily.ui.home.DetailGardenFirebase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chart.*
import kotlinx.android.synthetic.main.fragment_vegetable.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.data.DataSet as DataSet1


class ChartActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var database: DatabaseReference

    private var sharePath = "no"
    private var iv: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        // take info temp
        //gardenInfo()
        btn_chart_print.setOnClickListener {
            takeScreenshot()
        }

        //button save
        var viewChartPrint = findViewById<Button>(R.id.btn_chart_print)
        viewChartPrint.setOnClickListener {
            var dialog = Dialog(this)
            // get id by position
            dialog.setContentView(R.layout.custom_dialog_screenhot)
            dialog.setTitle(getString(R.string.choose_mode_for_device))
            var imageView = dialog.findViewById(R.id.imageView) as ImageView
            var button = dialog.findViewById(R.id.detail_mode_btn_screen_ok) as Button

            //handler
            val activityView = layoutChart_screenshot
            var screenshot = Screenshot()
            val b = screenshot.takescreenshot(activityView)
            imageView.setImageBitmap(b)
            saveImageSDcard(b)
            //get list mode
            button.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }

    override fun onNothingSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    // build chart
    private fun buildChart(arrayList: ArrayList<Float>, arrayListDate: ArrayList<String>) {

        combinedChart.description.isEnabled = false
        combinedChart.setDrawBarShadow(false)
        combinedChart.setBackgroundColor(Color.WHITE)
        combinedChart.setDrawGridBackground(false)
        combinedChart.isHighlightFullBarEnabled = false
        combinedChart.setOnChartValueSelectedListener(this)

        // column right
        var rightAxis = combinedChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f

        // column left
        var leftAxis = combinedChart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f

        // created month
        var xLabel: ArrayList<String> = arrayListDate


        var xAxis = combinedChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        xAxis.valueFormatter =
            IAxisValueFormatter { value, axis -> xLabel[value.toInt() % xLabel.size] }

        var data = CombinedData()
        var lineDatas = LineData()
        lineDatas.addDataSet(dataChart(arrayList) as ILineDataSet)

        data.setData(lineDatas)

        xAxis.axisMaximum = data.xMax + 0.25f

        combinedChart.data = data
        combinedChart.invalidate()
    }

    //data: IntArray
    private fun dataChart(arrayList: ArrayList<Float>): DataSet1<Entry> {

        var d = LineData()

        var data = arrayList

        var entries: ArrayList<Entry> = ArrayList()
        if (!data.isNullOrEmpty()) {
            for (index in 0 until data.size) {
                entries.add(Entry(index.toFloat(), data[index]))
            }
        }
        var set = LineDataSet(entries, getString(R.string.decription_Humidity_vi))
        set.color = Color.GREEN
        set.lineWidth = 2.5f
        set.setCircleColor(Color.GREEN)
        set.circleRadius = 5f
        set.fillColor = Color.GREEN
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.setDrawValues(true)
        set.valueTextSize = 10f
        set.valueTextColor = Color.GREEN

        set.axisDependency = YAxis.AxisDependency.LEFT
        d.addDataSet(set)

        return set
    }

    // build chart
    private fun buildChart1(arrayList: ArrayList<Float>, arrayListDate: ArrayList<String>) {

        combinedChart1.description.isEnabled = false
        combinedChart1.setDrawBarShadow(false)
        combinedChart1.setBackgroundColor(Color.WHITE)
        combinedChart1.setDrawGridBackground(false)
        combinedChart1.isHighlightFullBarEnabled = false
        combinedChart1.setOnChartValueSelectedListener(this)

        // column right
        var rightAxis = combinedChart1.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f

        // column left
        var leftAxis = combinedChart1.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f

        // created hours
        var xLabel: ArrayList<String> = arrayListDate


        var xAxis = combinedChart1.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f

        xAxis.valueFormatter =
            IAxisValueFormatter { value, axis -> xLabel[value.toInt() % xLabel.size] }

        var data = CombinedData()
        var lineDatas = LineData()
        lineDatas.addDataSet(dataChart1(arrayList) as ILineDataSet)

        data.setData(lineDatas)

        xAxis.axisMaximum = data.xMax + 0.25f

        combinedChart1.data = data
        combinedChart1.invalidate()
    }

    //data: IntArray
    private fun dataChart1(arrayList: ArrayList<Float>): DataSet1<Entry> {

        var d = LineData()

        var data = arrayList

        var entries: ArrayList<Entry> = ArrayList()
        if (!data.isNullOrEmpty()) {
            for (index in 0 until data.size) {
                entries.add(Entry(index.toFloat(), data[index]))
            }
        }
        var set = LineDataSet(entries, getString(R.string.decription_Temperature_vi))
        set.color = Color.RED
        set.lineWidth = 2.5f
        set.setCircleColor(Color.RED)
        set.circleRadius = 5f
        set.fillColor = Color.RED
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.setDrawValues(true)
        set.valueTextSize = 10f
        set.valueTextColor = Color.RED

        set.axisDependency = YAxis.AxisDependency.LEFT
        d.addDataSet(set)

        return set
    }

//    /**
//     * the method to get data from intent
//     */
//    private fun gardenInfo() {
//        database = FirebaseDatabase.getInstance().reference
//        // My top posts by number of stars
//        var garden = getDataFromItent()
//        var gardenChild = getDataFromItent() + "D1"
//        // My top posts by number of stars
//        database.child(garden).child(gardenChild).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                var arrayListOf = ArrayList<Float>()
//                var arrayListTemp = ArrayList<String>()
//
//                var arrayListOf1 = ArrayList<Float>()
//                var arrayListTemp1 = ArrayList<String>()
//
//                for (postSnapshot in dataSnapshot.children) {
//                    var chartGarden: DetailGardenFirebase? =
//                        postSnapshot.getValue(DetailGardenFirebase::class.java)
//                    arrayListOf.add(chartGarden?.Humidity?.split(" ")!![0].toFloat())
//                    arrayListOf1.add(chartGarden?.Temperature?.split(" ")!![0].toFloat())
//
//                    val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//                    val formatted: String = formatter.format(Date(chartGarden.timestamp!!))
//
//                    val formatter1: SimpleDateFormat = SimpleDateFormat("HH:mm")
//                    val dayOnly: String = formatter1.format(Date(chartGarden.timestamp!!))
//                    title_chart.text = "Biểu đồ độ ẩm, " + formatted
//                    title_chart_1.text = "Biểu đồ nhiệt độ, " + formatted
//
//                    arrayListTemp.add(dayOnly)
//                    arrayListTemp1.add(dayOnly)
//                }
//                buildChart(arrayListOf, arrayListTemp)
//                buildChart1(arrayListOf1, arrayListTemp1)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("AAA", "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        })
//    }

    /**
     * the method to get data from intent
     */
    private fun getDataFromItent(): String {
        val bundle: Bundle = intent.extras
        val id: String =
            bundle.get("garden_code") as String
        return id

    }

    /**
     * the method to take screen hot
     */
    private fun takeScreenshot() {
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg"

            // create bitmap screen capture
            val v1 = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

            val imageFile = File(mPath)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            //setting screenshot in imageview
            val filePath = imageFile.path

            val ssbitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            iv!!.setImageBitmap(ssbitmap)
            sharePath = filePath

        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }

    private fun getScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false

        return bitmap
    }

    /**
     * save picture
     */
    private fun saveImageSDcard(bitmap: Bitmap) : Uri? {

        val path = Environment.getExternalStorageDirectory().toString()
        val file = File(path, "${UUID.randomUUID()}.png")

        var outputStream : OutputStream? = null

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absoluteFile.toString())
    }
}
