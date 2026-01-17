package com.example.visiograph.modeldata

import kotlinx.serialization.Serializable


@Serializable
data class DataBarang(
    val id: Int,
    val nama: String,
    val kategori: String,
    val jumlah_total: Int
)

data class UIStateBarang(
    val detailBarang: DetailBarang = DetailBarang(),
    val isEntryValid: Boolean = false
)

data class DetailBarang(
    val id: Int = 0,
    val nama: String = "",
    val kategori: String = "",
    val jumlah_total: Int = 0
)

fun DetailBarang.toDataBarang(): DataBarang = DataBarang(
    id = id,
    nama = nama,
    kategori = kategori,
    jumlah_total = jumlah_total
)

fun DataBarang.toDetailBarang(): DetailBarang = DetailBarang(
    id = id,
    nama = nama,
    kategori = kategori,
    jumlah_total = jumlah_total
)

fun DataBarang.toUiStateBarang(
    isEntryValid: Boolean = false
): UIStateBarang = UIStateBarang(
    detailBarang = this.toDetailBarang(),
    isEntryValid = isEntryValid
)

// helper search
fun DataBarang.doesMatchSearchQuery(query: String): Boolean {
    val matchingCombinations = listOf(
        nama,
        kategori,
        "$nama $kategori"
    )
    return matchingCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}