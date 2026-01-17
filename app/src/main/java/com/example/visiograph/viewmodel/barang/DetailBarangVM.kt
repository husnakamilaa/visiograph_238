package com.example.visiograph.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visiograph.modeldata.DetailBarang
import com.example.visiograph.modeldata.toDetailBarang
import com.example.visiograph.repositori.RepositoryDataBarang
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangDetail
import kotlinx.coroutines.launch

class DetailBarangVM(
    savedStateHandle: SavedStateHandle,
    private val repo: RepositoryDataBarang
) : ViewModel() {

    private val barangId: Int =
        checkNotNull(
            savedStateHandle[DestinasiBarangDetail.itemIdArg] ?: 0
        )

    var detailUiState by mutableStateOf(DetailBarang())
        private set

    init {
        viewModelScope.launch {
            try {
                val data = repo.getBarangById(barangId)
                detailUiState = data.toDetailBarang()
            } catch (e: Exception) {
                println("ERROR_VM_BARANG: ${e.message}")
            }
        }
    }

    suspend fun deleteBarang() {
        repo.deleteBarang(barangId)
    }
}
