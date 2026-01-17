package com.example.visiograph.view.kerusakan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visiograph.R
import com.example.visiograph.view.barang.DetailRow
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.view.component.formatTanggalIndonesia
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.kerusakan.DetailKerusakanVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKerusakanScreen(
    navigateToEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: DetailKerusakanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = "Detail Kerusakan", canNavigateBack = true, navigateUp = navigateBack) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(viewModel.detailUiState.id) },
                containerColor = colorResource(id = R.color.navy_gray),
                contentColor = Color.White
            ) { Icon(Icons.Default.Edit, null) }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(colorResource(id = R.color.light_gray)).padding(24.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetailRow("Barang", viewModel.detailUiState.nama_barang, Icons.Default.ShoppingCart)
                    Divider()
                    DetailRow("Deskripsi", viewModel.detailUiState.deskripsi, Icons.Default.Info)
                    Divider()
                    DetailRow("Jumlah", "${viewModel.detailUiState.jumlah} unit", Icons.Default.List)
                    Divider()
                    DetailRow("Tanggal", formatTanggalIndonesia(viewModel.detailUiState.tanggal), Icons.Default.DateRange)
                    Divider()
                    DetailRow("Status", viewModel.detailUiState.status_perbaikan, Icons.Default.Build)
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Delete, null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Hapus Data Kerusakan", color = Color.White, fontWeight = FontWeight.Bold)
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        coroutineScope.launch { viewModel.deleteKerusakan(); navigateBack() }
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false }
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.attention)) },
        text = { Text("Apakah Anda yakin ingin menghapus data kerusakan ini?") },
        dismissButton = { TextButton(onClick = onDeleteCancel) { Text(stringResource(R.string.no)) } },
        confirmButton = { TextButton(onClick = onDeleteConfirm) { Text(stringResource(R.string.yes)) } }
    )
}