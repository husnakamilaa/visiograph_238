package com.example.visiograph.view.peminjaman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visiograph.R
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.peminjaman.EditPeminjamanVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPeminjamanScreen(
    navigateBack: () -> Unit,
    viewModel: EditPeminjamanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopAppBar(title = "Edit Peminjaman", canNavigateBack = true, navigateUp = navigateBack) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(colorResource(id = R.color.light_gray)).verticalScroll(rememberScrollState()).padding(24.dp)) {
            FormPeminjaman(
                detailPeminjaman = viewModel.editUiState.detailPeminjaman,
                onValueChange = viewModel::updateUiState,
                daftarBarang = viewModel.daftarBarang,
                daftarAnggota = viewModel.daftarAnggota,
                isEdit = true
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { scope.launch { if(viewModel.updatePeminjaman()) navigateBack() } },
                modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.navy_gray))
            ) {
                Text("Perbarui Transaksi", color = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}