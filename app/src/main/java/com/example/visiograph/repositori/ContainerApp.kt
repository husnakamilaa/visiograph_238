package com.example.visiograph.repositori

import android.app.Application
import com.example.visiograph.apiservice.ServiceApiAnggota
import com.example.visiograph.apiservice.ServiceApiAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface ContainerApp {
    val repositoryAuth: RepositoryAuth
    val repositoryDataAnggota: RepositoryDataAnggota
}

/**
 * Implementasi container
 */
class DefaultContainerApp : ContainerApp {

    private val baseUrl = "http://10.0.2.2:3000/" //10.0.2.2: klo run make emulator, tp kalau hp ntar make ip

    // logging interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // http client
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // retrofit
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

    // ===== API SERVICES =====

    private val serviceApiAuth: ServiceApiAuth by lazy {
        retrofit.create(ServiceApiAuth::class.java)
    }

    private val serviceApiAnggota: ServiceApiAnggota by lazy {
        retrofit.create(ServiceApiAnggota::class.java)
    }

    // ===== REPOSITORIES =====

    override val repositoryAuth: RepositoryAuth by lazy {
        JaringanAuthRepository(serviceApiAuth)
    }

    override val repositoryDataAnggota: RepositoryDataAnggota by lazy {
        JaringanRepositoryAnggota(serviceApiAnggota)
    }
}

class AplikasiVisiograph : Application() {
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        this.container = DefaultContainerApp() //jalanin container default yang diatas
    }
}