package com.example.visiograph.view.peminjaman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.visiograph.modeldata.DataPeminjaman
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.view.anggota.ErrorScreen
import com.example.visiograph.view.anggota.LoadingScreen
import com.example.visiograph.view.component.SearchBarCustom
import com.example.visiograph.view.component.formatTanggalIndonesia
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.peminjaman.ListPeminjamanUiState
import com.example.visiograph.viewmodel.peminjaman.ListPeminjamanVM
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPeminjamanScreen(
    navigateToEntry: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ListPeminjamanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val navyGray = colorResource(id = R.color.navy_gray)
    val lightGray = colorResource(id = R.color.light_gray)

    val searchText by viewModel.searchText.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val filteredList by viewModel.filteredPeminjaman.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = "Daftar Peminjaman", canNavigateBack = true, navigateUp = navigateBack)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry, containerColor = navyGray, contentColor = Color.White) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(lightGray)
        ) {
            // ===== SEARCH & FILTER SECTION =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(navyGray)
                    .padding(16.dp)
            ) {
                SearchBarCustom(
                    value = searchText,
                    onValueChange = { viewModel.onSearchChange(it) }
                )

                Spacer(modifier = Modifier.height(12.dp))
                val statusOptions = listOf("Semua", "Dipinjam", "Kembali")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    statusOptions.forEach { status ->
                        val isSelected = selectedStatus == status
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onStatusChange(status) },
                            label = { Text(status) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.White,
                                selectedLabelColor = navyGray,
                                labelColor = Color.White,
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color.White,
                                selectedBorderColor = Color.Black
                            )
                        )
                    }
                }
            }
            when (viewModel.peminjamanUiState) {
                is ListPeminjamanUiState.Loading -> LoadingScreen()
                is ListPeminjamanUiState.Error -> ErrorScreen(retryAction = viewModel::getPeminjaman)
                is ListPeminjamanUiState.Success -> {
                    if (filteredList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Data peminjaman tidak ditemukan")
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredList) { pinjam ->
                                ItemPeminjaman(pinjam = pinjam, onClick = { navigateToDetail(pinjam.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPeminjaman(pinjam: DataPeminjaman, onClick: () -> Unit) {
    val navyGray = colorResource(id = R.color.navy_gray)
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = navyGray,
                modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(pinjam.nama_barang ?: "...", fontWeight = FontWeight.Bold, color = navyGray)
                Text("Peminjam: ${pinjam.nama_anggota ?: "..."}", fontSize = 14.sp)

                val tglPinjam = formatTanggalIndonesia(pinjam.tanggal_pinjam)
                val tglKembali = formatTanggalIndonesia(pinjam.tanggal_kembali)

                Text("Pinjam: $tglPinjam", fontSize = 12.sp, color = Color.Gray)
                Text("Kembali: $tglKembali", fontSize = 12.sp, color = Color.Gray)
            }
            StatusChip(status = pinjam.status_pinjam)
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val color = if (status == "Dipinjam") colorResource(id = R.color.navy_gray) else Color(0xFF2E7D32)
    Surface(
        color = color,
        shape = RoundedCornerShape(8.dp)) {
        Text(status,
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp))
    }
}
