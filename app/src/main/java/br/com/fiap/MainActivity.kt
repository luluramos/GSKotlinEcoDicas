package br.com.fiap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Eco Dicas"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }

        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    itemsAdapter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    itemsAdapter.filter(it)
                }
                return true
            }
        })

        val button = findViewById<Button>(R.id.button)
        val editTextTitle = findViewById<EditText>(R.id.editText) // Campo para título
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription) // Campo para descrição

        button.setOnClickListener {
            if (editTextTitle.text.isEmpty()) {
                editTextTitle.error = "Insira um título"
                return@setOnClickListener
            }
            if (editTextDescription.text.isEmpty()) {
                editTextDescription.error = "Insira uma descrição"
                return@setOnClickListener
            }

            viewModel.addItem(editTextTitle.text.toString(), editTextDescription.text.toString())
            editTextTitle.text.clear()
            editTextDescription.text.clear()
        }
    }
}