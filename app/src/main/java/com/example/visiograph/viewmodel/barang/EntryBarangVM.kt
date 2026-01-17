package com.example.visiograph.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.visiograph.modeldata.DetailBarang
import com.example.visiograph.modeldata.UIStateBarang
import com.example.visiograph.modeldata.toDataBarang
import com.example.visiograph.repositori.RepositoryDataBarang

class EntryBarangVM(
    private val repo: RepositoryDataBarang
) : ViewModel() {

    var uiStateBarang by mutableStateOf(UIStateBarang())
        private set

    private fun validasiInput(detail: DetailBarang = uiStateBarang.detailBarang): Boolean {
        return with(detail) {
            nama.isNotBlank() && kategori.isNotBlank() && jumlah_total > 0
        }
    }

    fun updateUiState(detailBarang: DetailBarang) {
        val jumlahValid = maxOf(0, detailBarang.jumlah_total)

        uiStateBarang = UIStateBarang(
            detailBarang = detailBarang.copy(jumlah_total = jumlahValid),
            isEntryValid = validasiInput(detailBarang.copy(jumlah_total = jumlahValid))
        )
    }

    suspend fun addBarang(): Boolean {
        return if (validasiInput()) {
            try {
                val response = repo.createBarang(uiStateBarang.detailBarang.toDataBarang())
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }
}