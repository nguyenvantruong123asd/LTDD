package com.appveg.farmfamily

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import com.appveg.farmfamily.ui.database.Database
import com.appveg.farmfamily.ui.garden.QLKVAdapter
import com.appveg.farmfamily.ui.settings.SettingsActivity
import com.appveg.farmfamily.ui.vegetable.AddVegetableActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.settings_activity.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.appveg.farmfamily.ui.garden.GalleryFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_mode,
                R.id.nav_gallery,
                R.id.nav_share,
                R.id.nav_send,
                R.id.nav_thamso,
                R.id.nav_vegetable,
                R.id.nav_device,
                R.id.nav_users,
                R.id.nav_device_category
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        /*by vu*/
        var database : Database = Database(this)
//        var data : SQLiteDatabase = database.readableDatabase
//        deleteDatabase("smartfarm")
//          database.QueryData("CREATE TABLE batch (batch_id INTEGER PRIMARY KEY AUTOINCREMENT,batch_image VARCHAR(100),batch_name VARCHAR(100),the_end_date VARCHAR(15)," +
//              "total_quantity VARCHAR(100),garden_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
//                      "deleted_date VARCHAR(50),deleted_flag INTEGER)")
//          database.QueryData("CREATE TABLE batch_quantity_detail (qty_detail_id INTEGER PRIMARY KEY AUTOINCREMENT,vegetable_name VARCHAR(100)," +
//                  "vegetable_quantity VARCHAR(100),qty_id INTEGER,created_by VARCHAR(50),created_date VARCHAR(50),updated_by VARCHAR(50),updated_date VARCHAR(50),deleted_by VARCHAR(50)," +
//                  "deleted_date VARCHAR(50),deleted_flag INTEGER)")
//        database.QueryData("DROP TABLE IF EXISTS roles")

//        database.QueryData("INSERT INTO users VALUES(null,'admin','NGUYEN HOANG VU','hvu3011@gmail.com','admin','1','active',1,'vu',null,null,null,null,null,1)")

//        var db: SQLiteDatabase = database.writableDatabase
//        database.onCreate(db)

//        var result: Cursor = data.rawQuery("SELECT * FROM batch",null);
//        while (result.moveToNext()){
//            var username: String = result.getString(1)
//            Toast.makeText(this, username,Toast.LENGTH_LONG).show()
//        }
        /*the end by vu*/

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_settings) {
            var intent: Intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
