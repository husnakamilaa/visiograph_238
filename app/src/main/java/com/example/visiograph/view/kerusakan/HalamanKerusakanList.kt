package com.example.visiograph.view.kerusakan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import com.example.visiograph.modeldata.DataKerusakan
import com.example.visiograph.view.anggota.ErrorScreen
import com.example.visiograph.view.anggota.LoadingScreen
import com.example.visiograph.view.component.SearchBarCustom
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.kerusakan.ListKerusakanUiState
import com.example.visiograph.viewmodel.kerusakan.ListKerusakanVM
import com.example.visiograph.view.component.formatTanggalIndonesia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListKerusakanScreen(
    navigateToItemEntry: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ListKerusakanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val navyGray = colorResource(id = R.color.navy_gray)
    val lightGray = colorResource(id = R.color.light_gray)

    val searchText by viewModel.searchText.collectAsState()
    val filteredList by viewModel.filteredKerusakan.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = "Daftar Kerusakan", canNavigateBack = true, navigateUp = navigateBack) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                containerColor = navyGray,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(lightGray)) {
            // SEARCH & FILTER STATUS
            Column(modifier = Modifier.fillMaxWidth().background(navyGray).padding(16.dp)) {
                SearchBarCustom(value = searchText, onValueChange = { viewModel.onSearchChange(it) })
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Semua", "Belum", "Sudah").forEach { status ->
                        val selected = selectedStatus == status
                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.onStatusChange(status) },
                            label = { Text(status) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.White,
                                selectedLabelColor = navyGray,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }

            when (viewModel.kerusakanUiState) {
                is ListKerusakanUiState.Loading -> LoadingScreen()
                is ListKerusakanUiState.Error -> ErrorScreen(retryAction = viewModel::getKerusakan)
                is ListKerusakanUiState.Success -> {
                    if (filteredList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Data kerusakan tidak ditemukan")
                        }
                    } else {
                        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(items = filteredList, key = { it.id }) { data ->
                                ItemKerusakan(kerusakan = data, onClick = { navigateToDetail(data.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemKerusakan(kerusakan: DataKerusakan, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Build, null, tint = colorResource(R.color.navy_gray), modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(kerusakan.nama_barang ?: "Barang", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Jumlah: ${kerusakan.jumlah} unit", fontSize = 14.sp, color = Color.Gray)
                Text("Tgl: ${formatTanggalIndonesia(kerusakan.tanggal)}", fontSize = 12.sp, color = Color.Gray)
            }
            // Badge Status
            val colorStatus = if(kerusakan.status_perbaikan == "Sudah") Color(0xFF4CAF50) else Color(0xFFF44336)
            Text(kerusakan.status_perbaikan, color = colorStatus, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Gray)
        }
    }
}

