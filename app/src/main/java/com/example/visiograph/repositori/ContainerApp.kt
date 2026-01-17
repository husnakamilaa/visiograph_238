package com.example.visiograph.repositori

import android.app.Application
import com.example.visiograph.apiservice.ServiceApiAnggota
import com.example.visiograph.apiservice.ServiceApiAuth
import com.example.visiograph.apiservice.ServiceApiBarang
import com.example.visiograph.apiservice.ServiceApiKerusakan
import com.example.visiograph.apiservice.ServiceApiPeminjaman
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface ContainerApp {
    // 1. STEP 1 BUAT REPO2 NYA //////////////////////////////////////////////////////////////////////////
    val repositoryAuth: RepositoryAuth
    val repositoryDataAnggota: RepositoryDataAnggota
    val repositoryDataBarang: RepositoryDataBarang
    val repositoryDataPeminjaman: RepositoryDataPeminjaman
    val repositoryDataKerusakan: RepositoryDataKerusakan
}

class DefaultContainerApp : ContainerApp {

    private val baseUrl = "http://10.175.227.235:3001/" //10.0.2.2

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .client(client)
        .build()

    // 2. STEP 2 JANGAN LUPA BUAT API SERVICENYA //////////////////////////////////////////////////////////
    private val serviceApiAuth: ServiceApiAuth by lazy {
        retrofit.create(ServiceApiAuth::class.java)
    }
    private val serviceApiAnggota: ServiceApiAnggota by lazy {
        retrofit.create(ServiceApiAnggota::class.java)
    }
    private val serviceApiBarang: ServiceApiBarang by lazy {
        retrofit.create(ServiceApiBarang::class.java)
    }

    private val serviceApiPeminjaman: ServiceApiPeminjaman by lazy {
        retrofit.create(ServiceApiPeminjaman::class.java)
    }

    private val serviceApiKerusakan: ServiceApiKerusakan by lazy {
        retrofit.create(ServiceApiKerusakan::class.java)
    }

    // 3. STEP 3 BUAT DEKLARASI REPO ///////////////////////////////////////////////////////////////////////
    override val repositoryAuth: RepositoryAuth by lazy {
        JaringanAuthRepository(serviceApiAuth)
    }
    override val repositoryDataAnggota: RepositoryDataAnggota by lazy {
        JaringanRepositoryAnggota(serviceApiAnggota)
    }
    override val repositoryDataBarang: RepositoryDataBarang by lazy {
        JaringanRepositoryBarang(serviceApiBarang)
    }
    override val repositoryDataPeminjaman: RepositoryDataPeminjaman by lazy {
        JaringanRepositoryPeminjaman(serviceApiPeminjaman)
    }
    override val repositoryDataKerusakan: RepositoryDataKerusakan by lazy {
        JaringanRepositoryKerusakan(serviceApiKerusakan)
    }
}

class AplikasiVisiograph : Application() {
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        this.container = DefaultContainerApp()
    }
}