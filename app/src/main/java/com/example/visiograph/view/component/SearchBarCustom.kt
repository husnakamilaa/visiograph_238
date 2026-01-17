package com.example.visiograph.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.visiograph.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCustom(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val navyGray = colorResource(id = R.color.navy_gray)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Cari...", color = Color.Gray) },
        modifier = modifier,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = navyGray) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = navyGray,
            focusedTextColor = navyGray,
            unfocusedTextColor = navyGray
        )
    )
}


