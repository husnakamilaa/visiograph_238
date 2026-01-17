package com.example.visiograph.viewmodel

import EntryAnggotaVM
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.visiograph.repositori.AplikasiVisiograph
import com.example.visiograph.view.peminjaman.EntryPeminjamanScreen
import com.example.visiograph.viewmodel.anggota.DetailAnggotaVM
import com.example.visiograph.viewmodel.anggota.EditAnggotaVM
import com.example.visiograph.viewmodel.anggota.ListAnggotaVM
import com.example.visiograph.viewmodel.barang.DetailBarangVM
import com.example.visiograph.viewmodel.barang.EditBarangVM
import com.example.visiograph.viewmodel.barang.EntryBarangVM
import com.example.visiograph.viewmodel.barang.ListBarangVM
import com.example.visiograph.viewmodel.kerusakan.DetailKerusakanVM
import com.example.visiograph.viewmodel.kerusakan.EditKerusakanVM
import com.example.visiograph.viewmodel.kerusakan.EntryKerusakanVM
import com.example.visiograph.viewmodel.kerusakan.ListKerusakanVM
import com.example.visiograph.viewmodel.peminjaman.DetailPeminjamanVM
import com.example.visiograph.viewmodel.peminjaman.EditPeminjamanVM
import com.example.visiograph.viewmodel.peminjaman.EntryPeminjamanVM
import com.example.visiograph.viewmodel.peminjaman.ListPeminjamanVM

fun CreationExtras.aplikasiVisiograph(): AplikasiVisiograph = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                AplikasiVisiograph
        )

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { LoginViewModel(aplikasiVisiograph().container.repositoryAuth) }
        initializer { HomeViewModel() }

        initializer {
            ListAnggotaVM(aplikasiVisiograph().container.repositoryDataAnggota)
        }
        initializer {
            EntryAnggotaVM(aplikasiVisiograph().container.repositoryDataAnggota)
        }
        initializer {
            DetailAnggotaVM(
                savedStateHandle = createSavedStateHandle(),
                repo = aplikasiVisiograph().container.repositoryDataAnggota
            )
        }
        initializer {
            EditAnggotaVM(
                createSavedStateHandle(),
                aplikasiVisiograph().container.repositoryDataAnggota
            )
        }

        //barang===================================================================================

        initializer {
            ListBarangVM(aplikasiVisiograph().container.repositoryDataBarang)
        }
        initializer {
            EntryBarangVM(aplikasiVisiograph().container.repositoryDataBarang)
        }
        initializer {
            DetailBarangVM(
                savedStateHandle = createSavedStateHandle(),
                repo = aplikasiVisiograph().container.repositoryDataBarang
            )
        }
        initializer {
            EditBarangVM(
                createSavedStateHandle(),
                aplikasiVisiograph().container.repositoryDataBarang)
        }

        //peminjaman===================================================================================
        initializer {
            ListPeminjamanVM(aplikasiVisiograph().container.repositoryDataPeminjaman)
        }
        initializer {
            EntryPeminjamanVM(
                repoPinjam = aplikasiVisiograph().container.repositoryDataPeminjaman,
                repoBarang = aplikasiVisiograph().container.repositoryDataBarang,
                repoAnggota = aplikasiVisiograph().container.repositoryDataAnggota
            )
        }
        initializer {
            DetailPeminjamanVM(
                savedStateHandle = createSavedStateHandle(),
                repo = aplikasiVisiograph().container.repositoryDataPeminjaman
            )
        }

        initializer {
            EditPeminjamanVM(
                savedStateHandle = createSavedStateHandle(),
                repoPinjam = aplikasiVisiograph().container.repositoryDataPeminjaman,
                repoBarang = aplikasiVisiograph().container.repositoryDataBarang,
                repoAnggota = aplikasiVisiograph().container.repositoryDataAnggota
            )
        }

        //lastt kerusakaann //////////////////////////////////////////////////////////////////////////
        initializer {
            ListKerusakanVM(
                aplikasiVisiograph().container.repositoryDataKerusakan
            )
        }

        initializer {
            EntryKerusakanVM(
                repoKerusakan = aplikasiVisiograph().container.repositoryDataKerusakan,
                repoBarang = aplikasiVisiograph().container.repositoryDataBarang
            )
        }

        initializer {
            DetailKerusakanVM(
                savedStateHandle = createSavedStateHandle(),
                repoKerusakan = aplikasiVisiograph().container.repositoryDataKerusakan
            )
        }

        initializer {
            EditKerusakanVM(
                savedStateHandle = createSavedStateHandle(),
                repoKerusakan = aplikasiVisiograph().container.repositoryDataKerusakan,
                repoBarang = aplikasiVisiograph().container.repositoryDataBarang
            )
        }
    }
}