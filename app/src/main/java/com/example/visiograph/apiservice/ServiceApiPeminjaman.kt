package com.example.visiograph.apiservice

import com.example.visiograph.modeldata.DataPeminjaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiceApiPeminjaman  {
    @GET("api/peminjaman")
    suspend fun getAllPeminjaman(): List<DataPeminjaman>

    @GET("api/peminjaman/{id}")
    suspend fun getPeminjamanById(@Path("id") id: Int): DataPeminjaman

    @POST("api/peminjaman")
    suspend fun insertPeminjaman(@Body data: DataPeminjaman): Response<Unit>

    @PUT("api/peminjaman/{id}")
    suspend fun updatePeminjaman(@Path("id") id: Int, @Body data: DataPeminjaman): Response<Unit>

    @DELETE("api/peminjaman/{id}")
    suspend fun deletePeminjaman(@Path("id") id: Int): Response<Unit>
}