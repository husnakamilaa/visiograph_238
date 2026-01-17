package com.example.visiograph.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataKerusakan(
    val id: Int = 0,
    val id_barang: Int,
    val deskripsi: String,
    val jumlah: Int,
    val tanggal: String,
    val status_perbaikan: String,

    val nama_barang: String? = null
)

data class UIStateKerusakan(
    val detailKerusakan: DetailKerusakan = DetailKerusakan(),
    val isEntryValid: Boolean = false
)

data class DetailKerusakan(
    val id: Int = 0,
    val id_barang: Int = 0,
    val nama_barang: String = "",
    val deskripsi: String = "",
    val jumlah: Int = 0,
    val tanggal: String = "",
    val status_perbaikan: String = "Belum"
)

fun DetailKerusakan.toDataKerusakan(): DataKerusakan = DataKerusakan(
    id = id,
    id_barang = id_barang,
    deskripsi = deskripsi,
    jumlah = jumlah,
    tanggal = tanggal,
    status_perbaikan = status_perbaikan
)

fun DataKerusakan.toDetailKerusakan(): DetailKerusakan = DetailKerusakan(
    id = id,
    id_barang = id_barang,
    nama_barang = nama_barang ?: "",
    deskripsi = deskripsi,
    jumlah = jumlah,
    tanggal = tanggal,
    status_perbaikan = status_perbaikan
)

fun DataKerusakan.toUiStateKerusakan(
    isEntryValid: Boolean = false
): UIStateKerusakan = UIStateKerusakan(
    detailKerusakan = this.toDetailKerusakan(),
    isEntryValid = isEntryValid
)