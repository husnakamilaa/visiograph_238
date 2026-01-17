package com.example.visiograph.apiservice

import com.example.visiograph.modeldata.DataBarang
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApiBarang {

    @GET("api/barang")
    suspend fun getAllBarang(): List<DataBarang>

    @GET("api/barang/{id}")
    suspend fun getBarangById(
        @Path("id") id: Int
    ): DataBarang

    @GET("api/barang/search")
    suspend fun searchBarang(
        @Query("nama") nama: String
    ): List<DataBarang>

    @POST("api/barang")
    suspend fun createBarang(
        @Body barang: DataBarang
    ): Response<Void>

    @PUT("api/barang/{id}")
    suspend fun updateBarang(
        @Path("id") id: Int,
        @Body barang: DataBarang
    ): Response<Void>

    @DELETE("api/barang/{id}")
    suspend fun deleteBarang(
        @Path("id") id: Int
    ): Response<Void>
}