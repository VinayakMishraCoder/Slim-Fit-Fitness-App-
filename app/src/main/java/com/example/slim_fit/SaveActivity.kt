package com.example.slim_fit

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.slim_fit.database.appDao
import com.example.slim_fit.database.appDatabase
import com.example.slim_fit.database.weightMod
import com.example.slim_fit.databinding.ActivitySaveBinding
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class SaveActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaveBinding
    var currUri: String? = null
    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }
    lateinit var db: appDao
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Init Database instance
        db  = appDatabase.getDatabase(this).weightDatabaseDao()

        // Leave Button
        binding.backButton.setOnClickListener{
            currUri = null
            super.onBackPressed()
        }

        // Set URI string
        binding.addPhoto.setOnClickListener {
            bringPhoto()
        }

        // Insert the current data
        binding.addBtn.setOnClickListener {
            var weightString = binding.editName.text.toString()
            var date = getDateAndTime().toString()

            if(currUri != null) {
                if(weightString.isEmpty()) {
                    Toast.makeText(this@SaveActivity, "Please enter your current weight!!", Toast.LENGTH_SHORT).show()
                }
                else {
                    CoroutineScope(Dispatchers.Default).launch {
                        var weight = weightString.toDouble()
                        db.insert(weightMod(0L,weight, currUri!!, date))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SaveActivity, "Saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else {
                Toast.makeText(this@SaveActivity, "Please select a picture of your current progress.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun bringPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateAndTime(): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        return formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            currUri = data?.data.toString()
            Log.d("insertion2: ", "date message")
            binding.photoStatic.setImageURI(Uri.parse(currUri))
        }
    }
}