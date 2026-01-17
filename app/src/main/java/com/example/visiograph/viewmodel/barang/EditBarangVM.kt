package com.example.visiograph.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visiograph.modeldata.DetailBarang
import com.example.visiograph.modeldata.UIStateBarang
import com.example.visiograph.modeldata.toDataBarang
import com.example.visiograph.modeldata.toDetailBarang
import com.example.visiograph.modeldata.toUiStateBarang
import com.example.visiograph.repositori.RepositoryDataBarang
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangEdit
import kotlinx.coroutines.launch

class EditBarangVM(
    savedStateHandle: SavedStateHandle,
    private val repo: RepositoryDataBarang
) : ViewModel() {

    var editUiState by mutableStateOf(UIStateBarang())
        private set

    private val idBarang: Int =
        checkNotNull(
            savedStateHandle[DestinasiBarangEdit.itemIdArg] ?: 0
        ) {
            "idBarang tidak ditemukan di SavedStateHandle"
        }

    init {
        viewModelScope.launch {
            try {
                val barang = repo.getBarangById(idBarang)
                editUiState = barang.toUiStateBarang(
                    cekValidasi(barang.toDetailBarang())
                )
            } catch (e: Exception) {
                println("DEBUG_VM: Gagal load data edit: ${e.message}")
            }
        }
    }

    private fun cekValidasi(detail: DetailBarang): Boolean {
        return detail.nama.isNotBlank() &&
                detail.kategori.isNotBlank() &&
                detail.jumlah_total >= 0
    }

    fun updateUiState(detailBarang: DetailBarang) {
        editUiState = UIStateBarang(
            detailBarang = detailBarang,
            isEntryValid = cekValidasi(detailBarang)
        )
    }

    suspend fun updateBarang(): Boolean {
        return try {
            val response = repo.updateBarang(
                idBarang,
                editUiState.detailBarang.toDataBarang()
            )
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}