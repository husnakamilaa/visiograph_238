package com.example.visiograph.view.peminjaman

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
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.peminjaman.DetailPeminjamanVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPeminjamanScreen(
    navigateToEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: DetailPeminjamanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Detail Peminjaman",
                canNavigateBack = true,
                navigateUp = navigateBack) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(viewModel.detailUiState.id) },
                containerColor = colorResource(R.color.navy_gray),
                contentColor = Color.White) {
                Icon(Icons.Default.Edit,
                    contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(colorResource(id = R.color.light_gray)).padding(24.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetailRow(
                        label = "Barang",
                        value = viewModel.detailUiState.nama_barang,
                        icon = Icons.Default.ShoppingCart)
                    DetailRow(
                        label = "Peminjam",
                        value = viewModel.detailUiState.nama_anggota,
                        icon = Icons.Default.Person)
                    DetailRow(
                        label = "Status",
                        value = viewModel.detailUiState.status_pinjam,
                        icon = Icons.Default.Info)
                    DetailRow(
                        label = "Jumlah",
                        value = "${viewModel.detailUiState.jumlah_pinjam} unit",
                        icon = Icons.Default.List)
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { deleteConfirmationRequired = true }, // Ubah ini agar memicu pop-up
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Hapus Peminjaman", color = Color.White, fontWeight = FontWeight.Bold)
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        coroutineScope.launch {
                            viewModel.deletePeminjaman()
                            navigateBack()
                        }
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false }
                )
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog (
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.tanya)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        }
    )
}