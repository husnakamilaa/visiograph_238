package com.example.visiograph.uicontroller.route.barang

import com.example.visiograph.R
import com.example.visiograph.uicontroller.route.DestinasiNavigasi

object DestinasiBarangEdit : DestinasiNavigasi {
    override val route = "barang_edit"
    override val titleRes = R.string.barang_edit

    const val itemIdArg = "idBarang"
    val routeWithArgs = "${route}/{$itemIdArg}"
}