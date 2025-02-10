package com.example.act2_miraveteperezdelcorraljuan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.act2_miraveteperezdelcorraljuan.databinding.ActivityMainBinding
import com.example.act2_miraveteperezdelcorraljuan.model.Products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductsAdapter
    private var productList: List<Products> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchProducts()
        displayProducts()
        buttons()
        binding.svProducts.setOnQueryTextListener(this)
    }

    private fun buttons() {
        binding.btnShowAll.setOnClickListener {
            adapter.updateProducts(productList)
        }
        binding.btnDescendente.setOnClickListener {
            sortByPriceDescending()
        }
        binding.btnMensClothing.setOnClickListener {
            filterByCategory("men's clothing")
        }
        binding.btnJewelery.setOnClickListener {
            filterByCategory("jewelery")
        }
        binding.btnElectronics.setOnClickListener {
            filterByCategory("electronics")
        }
        binding.btnWomensClothing.setOnClickListener {
            filterByCategory("women's clothing")
        }
    }

    private fun sortByPriceDescending() {
        val sortedList = productList.sortedBy { it.price }
        adapter.updateProducts(sortedList)
    }

    private fun filterByCategory(category: String) {
        val filtrado = productList.filter {
            it.category.equals(category, ignoreCase = true)
        }
        adapter.updateProducts(filtrado)
    }

    private fun displayProducts() {
        adapter = ProductsAdapter(productList)
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Products>> = getRetrofit()
                .create(ProductApiService::class.java)
                .getProducts()
            val productsList: List<Products>? = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    productList = productsList ?: emptyList()
                    adapter.updateProducts(productList)
                } else {
                    showError()
                }
            }
        }
    }

    private fun searchByTitle(query: String) {
        val filteredList = productList.filter {
            it.title.toLowerCase().contains(query.toLowerCase())
        }
        adapter.updateProducts(filteredList)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error al obtener los productos", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByTitle(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }
}