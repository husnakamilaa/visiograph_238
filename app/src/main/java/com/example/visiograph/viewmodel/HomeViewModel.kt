package com.example.visiograph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visiograph.repositori.RepositoryAuth
import com.example.visiograph.uicontroller.route.anggota.DestinasiAnggotaList
import com.example.visiograph.uicontroller.route.barang.DestinasiBarangList
import com.example.visiograph.uicontroller.route.kerusakan.DestinasiKerusakanList
import com.example.visiograph.uicontroller.route.peminjaman.DestinasiPeminjamanList
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val menuList: List<Pair<String, String>> = listOf(
        "Anggota" to DestinasiAnggotaList.route,
        "Barang" to DestinasiBarangList.route,
        "Peminjaman" to DestinasiPeminjamanList.route,
        "Kerusakan" to DestinasiKerusakanList.route
    )

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                onLogoutComplete()
            } catch (e: Exception) {
            }
        }
    }
}
