package com.example.slim_fit

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slim_fit.Adapters.homeAdapter
import com.example.slim_fit.database.appDao
import com.example.slim_fit.database.appDatabase
import com.example.slim_fit.database.weightMod
import com.example.slim_fit.databinding.ActivityMainBinding
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db : appDao
    lateinit var adapter: homeAdapter
    lateinit var recyclerview: RecyclerView
    lateinit var lineGraphView: GraphView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Create database instance
        db  = appDatabase.getDatabase(this).weightDatabaseDao()

        // Go to save details page
        binding.detailButton.setOnClickListener {
            val intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
        }

        // Refresh List and Graph
        binding.refreshView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val lst : List<weightMod> = getAllWeights()
                val lst2 : List<weightMod> = lst
                withContext(Dispatchers.Main) {
                    setupRecyclerView(lst)
                    setUpGraph(lst2)
                }
            }
        }

        // All clear Button
        binding.clearAllView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.clearAllEntries()
                val lst : List<weightMod> = getAllWeights()
                withContext(Dispatchers.Main) {
                    setupRecyclerView(lst)
                    setUpGraph(lst)
                }
            }
        }

        // Init Recycler View
        CoroutineScope(Dispatchers.IO).launch {
            val lst : List<weightMod> = getAllWeights()
            val lst2 : List<weightMod> = lst // change
            withContext(Dispatchers.Main) {
                setupRecyclerView(lst)
                setUpGraph(lst2)
            }
        }
    }

    suspend fun getAllWeights(): List<weightMod> {
        var lst : List<weightMod> = db.getAllEntries()
        return lst
    }

    fun setupRecyclerView(mods: List<weightMod>) {
        ActivityCompat.requestPermissions(
            this, // HERE
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            123
        )
        recyclerview = binding.recyclerView
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = homeAdapter(mods)
        recyclerview.adapter = adapter
    }
    fun setUpGraph(mods: List<weightMod>) {
        lineGraphView = binding.graphView
        lineGraphView.removeAllSeries()
        // on below line we are adding data to our graph view.
        var series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        var sortedMap = sortedMapOf<Double,Double>()

        for(x in mods.reversed()) {
            sortedMap.put(x.weightId.toDouble(), x.weight)
        }

        // series.appendData(DataPoint(x.weightId.toDouble(), x.weight), true, mods.size)
        if(!sortedMap.isEmpty()) {
            for(x in sortedMap) {
                series.appendData(DataPoint(x.key, x.value), true, sortedMap.size)
            }
        }

        // on below line adding animation
        lineGraphView.animate()

        // on below line we are setting scrollable
        // for point graph view
        lineGraphView.viewport.isScrollable = true

        // on below line we are setting scalable.
        lineGraphView.viewport.isScalable = true

        // on below line we are setting scalable y
        lineGraphView.viewport.setScalableY(true)

        // on below line we are setting scrollable y
        lineGraphView.viewport.setScrollableY(true)

        lineGraphView.addSeries(series)
    }
}