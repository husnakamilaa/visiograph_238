package com.example.visiograph.view.kerusakan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visiograph.R
import com.example.visiograph.view.component.TopAppBar
import com.example.visiograph.viewmodel.PenyediaViewModel
import com.example.visiograph.viewmodel.kerusakan.EditKerusakanVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKerusakanScreen(
    navigateBack: () -> Unit,
    viewModel: EditKerusakanVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopAppBar(title = "Edit Kerusakan", canNavigateBack = true, navigateUp = navigateBack) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(colorResource(id = R.color.light_gray)).verticalScroll(rememberScrollState()).padding(24.dp)) {
            FormKerusakan(
                detailKerusakan = viewModel.editUiState.detailKerusakan,
                onValueChange = viewModel::updateUiState,
                daftarBarang = viewModel.daftarBarang,
                isEdit = true
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { scope.launch { if(viewModel.updateKerusakan()) navigateBack() } },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.navy_gray)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Perbarui Data Kerusakan", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}