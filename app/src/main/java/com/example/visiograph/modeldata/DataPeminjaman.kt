package com.example.visiograph.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataPeminjaman(
    val id: Int,
    val id_barang: Int,
    val id_anggota: Int,
    val tanggal_pinjam: String,
    val tanggal_kembali: String,
    val jumlah_pinjam: Int,
    val status_pinjam: String,

//    ini buat join2 itu yak
    val nama_barang: String? = null,
    val nama_anggota: String? = null
)

data class UIStatePeminjaman(
    val detailPeminjaman: DetailPeminjaman = DetailPeminjaman(),
    val isEntryValid: Boolean = false
)

data class DetailPeminjaman(
    val id: Int = 0,
    val id_barang: Int = 0,
    val id_anggota: Int = 0,
    val nama_barang: String = "",
    val nama_anggota: String = "",
    val tanggal_pinjam: String = "",
    val tanggal_kembali: String = "",
    val jumlah_pinjam: Int = 0,
    val status_pinjam: String = "Dipinjam"
)

fun DetailPeminjaman.toDataPeminjaman(): DataPeminjaman = DataPeminjaman(
    id = id,
    id_barang = id_barang,
    id_anggota = id_anggota,
    tanggal_pinjam = tanggal_pinjam,
    tanggal_kembali = tanggal_kembali,
    jumlah_pinjam = jumlah_pinjam,
    status_pinjam = status_pinjam
)

fun DataPeminjaman.toDetailPeminjaman(): DetailPeminjaman = DetailPeminjaman(
    id = id,
    id_barang = id_barang,
    id_anggota = id_anggota,
    tanggal_pinjam = tanggal_pinjam,
    tanggal_kembali = tanggal_kembali,
    jumlah_pinjam = jumlah_pinjam,
    status_pinjam = status_pinjam
)

fun DataPeminjaman.toUiStatePeminjaman(
    isEntryValid: Boolean = false
): UIStatePeminjaman = UIStatePeminjaman(
    detailPeminjaman = this.toDetailPeminjaman(),
    isEntryValid = isEntryValid
)