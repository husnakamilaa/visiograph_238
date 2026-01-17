package com.example.visiograph.uicontroller.route.barang

import com.example.visiograph.R
import com.example.visiograph.uicontroller.route.DestinasiNavigasi

object DestinasiBarangDetail: DestinasiNavigasi {
    override val route = "barang_detail"
    override val titleRes = R.string.barang_detail

    const val itemIdArg = "idBarang"
    val routeWithArgs = "$route/{$itemIdArg}"
}