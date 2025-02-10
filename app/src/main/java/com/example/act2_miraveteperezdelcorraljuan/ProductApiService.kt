package com.example.act2_miraveteperezdelcorraljuan

import com.example.act2_miraveteperezdelcorraljuan.model.Products
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
        @GET("products")
        suspend fun getProducts(): Response<List<Products>>
}
