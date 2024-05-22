package com.example.mystory04

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystory04.Response.ListStoryItem
import com.example.mystory04.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        StoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            // Handle the click jump to the uploadStoryActivity
            startActivity(Intent(this, UploadStoryActivity::class.java))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this) // inflate layout rv
        binding.recyclerView.setHasFixedSize(true) //based on xml @list_story.xml
        binding.recyclerView.adapter = adapter // adapter to adapter(story adapter)

        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient.getApiService.getAllStories()
                    emit(response)
                }.onStart { //run when start
                    binding.progressBar.isVisible = true
                }.onCompletion { // run when complete
                    binding.progressBar.isVisible = false
                }.catch { // run when error happen
                    Toast.makeText(this@MainActivity, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Error.catch", it.message.toString())
                }.collect { // handle response collect data from flow and send to adapter

                    response -> adapter.setData(response.listStory as List<ListStoryItem>)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }


}