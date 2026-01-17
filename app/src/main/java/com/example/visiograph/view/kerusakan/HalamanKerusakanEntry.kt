package com.example.visiograph.view.kerusakan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visiograph.R
import com.example.visiograph.modeldata.DetailKerusakan
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.kerusakan.EntryKerusakanVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKerusakanScreen(
    navigateBack: () -> Unit,
    viewModel: EntryKerusakanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopAppBar(title = "Tambah Kerusakan", canNavigateBack = true, navigateUp = navigateBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
                .background(colorResource(id = R.color.light_gray))
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FormKerusakan(
                detailKerusakan = viewModel.uiStateKerusakan.detailKerusakan,
                onValueChange = viewModel::updateUiState,
                daftarBarang = viewModel.daftarBarang
            )
            Button(
                onClick = { coroutineScope.launch { if (viewModel.addKerusakan()) navigateBack() } },
                enabled = viewModel.uiStateKerusakan.isEntryValid,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.navy_gray)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Simpan Data Kerusakan", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormKerusakan(
    detailKerusakan: DetailKerusakan,
    onValueChange: (DetailKerusakan) -> Unit,
    daftarBarang: List<com.example.visiograph.modeldata.DataBarang>,
    isEdit: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Dropdown Barang
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = detailKerusakan.nama_barang, onValueChange = {}, readOnly = true,
                label = { Text("Pilih Barang") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                daftarBarang.forEach { barang ->
                    DropdownMenuItem(
                        text = { Text(barang.nama) },
                        onClick = {
                            onValueChange(detailKerusakan.copy(id_barang = barang.id, nama_barang = barang.nama))
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = detailKerusakan.deskripsi,
            onValueChange = { onValueChange(detailKerusakan.copy(deskripsi = it)) },
            label = { Text("Deskripsi Kerusakan") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 3
        )

        OutlinedTextField(
            value = if (detailKerusakan.jumlah == 0) "" else detailKerusakan.jumlah.toString(),
            onValueChange = { it ->
                val jml = it.toIntOrNull() ?: 0
                onValueChange(detailKerusakan.copy(jumlah = jml))
            },
            label = { Text("Jumlah Barang Rusak") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )

        if (isEdit) {
            Text("Status Perbaikan", fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = detailKerusakan.status_perbaikan == "Belum", onClick = { onValueChange(detailKerusakan.copy(status_perbaikan = "Belum")) })
                Text("Belum")
                Spacer(Modifier.width(16.dp))
                RadioButton(selected = detailKerusakan.status_perbaikan == "Sudah", onClick = { onValueChange(detailKerusakan.copy(status_perbaikan = "Sudah")) })
                Text("Sudah")
            }
        }
    }
}