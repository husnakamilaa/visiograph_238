package com.example.visiograph.apiservice

import com.example.visiograph.modeldata.DataKerusakan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiceApiKerusakan {
    @GET("api/kerusakan")
    suspend fun getAllKerusakan(): List<DataKerusakan>

    @GET("api/kerusakan/{id}")
    suspend fun getKerusakanById(@Path("id") id: Int): DataKerusakan

    @POST("api/kerusakan")
    suspend fun createKerusakan(@Body data: DataKerusakan): Response<Unit>

    @PUT("api/kerusakan/{id}")
    suspend fun updateKerusakan(@Path("id") id: Int, @Body data: DataKerusakan): Response<Unit>

    // Menghapus data kerusakan
    @DELETE("api/kerusakan/{id}")
    suspend fun deleteKerusakan(@Path("id") id: Int): Response<Unit>
}