package com.example.visiograph.view.peminjaman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visiograph.R
import com.example.visiograph.modeldata.DetailPeminjaman
import com.example.visiograph.modeldata.UIStatePeminjaman
import com.example.visiograph.view.component.DateRangePickerModal
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.peminjaman.EntryPeminjamanVM
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPeminjamanScreen(
    navigateBack: () -> Unit,
    viewModel: EntryPeminjamanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopAppBar(title = "Tambah Peminjaman", canNavigateBack = true, navigateUp = navigateBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
                .background(colorResource(id = R.color.light_gray))
                .verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FormPeminjaman(
                detailPeminjaman = viewModel.uiStatePeminjaman.detailPeminjaman,
                onValueChange = viewModel::updateUiState,
                daftarBarang = viewModel.daftarBarang,
                daftarAnggota = viewModel.daftarAnggota
            )
            Button(
                onClick = { coroutineScope.launch { if (viewModel.addPeminjaman()) navigateBack() } },
                enabled = viewModel.uiStatePeminjaman.isEntryValid,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.navy_gray)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Simpan Transaksi", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPeminjaman(
    detailPeminjaman: DetailPeminjaman,
    onValueChange: (DetailPeminjaman) -> Unit,
    daftarBarang: List<com.example.visiograph.modeldata.DataBarang>,
    daftarAnggota: List<com.example.visiograph.modeldata.DataAnggota>,
    isEdit: Boolean = false
) {
    var expBarang by remember { mutableStateOf(false) }
    var expAnggota by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Dropdown Barang
        ExposedDropdownMenuBox(expanded = expBarang, onExpandedChange = { expBarang = !expBarang }) {
            OutlinedTextField(
                value = detailPeminjaman.nama_barang, onValueChange = {}, readOnly = true,
                label = { Text("Pilih Barang") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expBarang) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(expanded = expBarang, onDismissRequest = { expBarang = false }) {
                daftarBarang.forEach { barang ->
                    DropdownMenuItem(
                        text = { Text("${barang.nama} (Stok: ${barang.jumlah_total})") },
                        onClick = {
                            onValueChange(detailPeminjaman.copy(id_barang = barang.id, nama_barang = barang.nama))
                            expBarang = false
                        }
                    )
                }
            }
        }

        // Dropdown Anggota
        ExposedDropdownMenuBox(expanded = expAnggota, onExpandedChange = { expAnggota = !expAnggota }) {
            OutlinedTextField(
                value = detailPeminjaman.nama_anggota, onValueChange = {}, readOnly = true,
                label = { Text("Pilih Anggota") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expAnggota) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(expanded = expAnggota, onDismissRequest = { expAnggota = false }) {
                daftarAnggota.forEach { anggota ->
                    DropdownMenuItem(
                        text = { Text(anggota.nama) },
                        onClick = {
                            onValueChange(detailPeminjaman.copy(id_anggota = anggota.id, nama_anggota = anggota.nama))
                            expAnggota = false
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = if (detailPeminjaman.tanggal_pinjam.isBlank()) ""
                else "${detailPeminjaman.tanggal_pinjam} s/d ${detailPeminjaman.tanggal_kembali}",
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Rentang Tanggal Pinjam") },
                trailingIcon = { Icon(Icons.Default.DateRange, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        OutlinedTextField(
            value = if (detailPeminjaman.jumlah_pinjam == 0) "" else detailPeminjaman.jumlah_pinjam.toString(),
            onValueChange = { it ->
                val jumlah = it.toIntOrNull() ?: 0
                onValueChange(detailPeminjaman.copy(jumlah_pinjam = jumlah))
            },
            label = { Text("Jumlah Pinjam") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
            )
        )

        if (isEdit) {
            Text("Status Peminjaman", fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = detailPeminjaman.status_pinjam == "Dipinjam", onClick = { onValueChange(detailPeminjaman.copy(status_pinjam = "Dipinjam")) })
                Text("Dipinjam")
                Spacer(Modifier.width(16.dp))
                RadioButton(selected = detailPeminjaman.status_pinjam == "Kembali", onClick = { onValueChange(detailPeminjaman.copy(status_pinjam = "Kembali")) })
                Text("Kembali")
            }
        }
    }

    if (showDatePicker) {
        DateRangePickerModal(
            onDismiss = { showDatePicker = false },
            onDateSelected = { start, end ->
                onValueChange(detailPeminjaman.copy(
                    tanggal_pinjam = convertMillisToDate(start),
                    tanggal_kembali = convertMillisToDate(end)
                ))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeminjamanDateRangePicker(onDismiss: () -> Unit, onDateSelected: (Long?, Long?) -> Unit) {
    val state = rememberDateRangePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = { onDateSelected(state.selectedStartDateMillis, state.selectedEndDateMillis); onDismiss() }) { Text("OK") } }
    ) {
        DateRangePicker(state = state, modifier = Modifier.height(450.dp))
    }
}

fun convertMillisToDate(millis: Long?): String {
    return millis?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it)) } ?: ""
}