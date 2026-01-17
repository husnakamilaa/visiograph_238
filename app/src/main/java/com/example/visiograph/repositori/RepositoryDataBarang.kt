package com.example.visiograph.repositori

import com.example.visiograph.apiservice.ServiceApiBarang
import com.example.visiograph.modeldata.DataBarang
import retrofit2.Response

interface RepositoryDataBarang {

    suspend fun getAllBarang(): List<DataBarang>

    suspend fun getBarangById(id: Int): DataBarang

    suspend fun searchBarang(nama: String): List<DataBarang>

    suspend fun createBarang(dataBarang: DataBarang): Response<Void>

    suspend fun updateBarang(
        id: Int,
        dataBarang: DataBarang
    ): Response<Void>

    suspend fun deleteBarang(id: Int): Response<Void>
}

class JaringanRepositoryBarang(
    private val serviceApiBarang: ServiceApiBarang
) : RepositoryDataBarang {

    override suspend fun getAllBarang(): List<DataBarang> =
        serviceApiBarang.getAllBarang()

    override suspend fun getBarangById(id: Int): DataBarang =
        serviceApiBarang.getBarangById(id)

    override suspend fun searchBarang(nama: String): List<DataBarang> =
        serviceApiBarang.searchBarang(nama)

    override suspend fun createBarang(
        dataBarang: DataBarang
    ): Response<Void> =
        serviceApiBarang.createBarang(dataBarang)

    override suspend fun updateBarang(
        id: Int,
        dataBarang: DataBarang
    ): Response<Void> =
        serviceApiBarang.updateBarang(id, dataBarang)

    override suspend fun deleteBarang(id: Int): Response<Void> =
        serviceApiBarang.deleteBarang(id)
}