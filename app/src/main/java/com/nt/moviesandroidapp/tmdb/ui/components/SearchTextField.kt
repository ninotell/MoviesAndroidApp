package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nt.moviesandroidapp.ui.theme.CustomYellow
import com.nt.moviesandroidapp.util.Constants.Companion.ROUNDED_ITEM_VALUE


@Composable
fun SearchTextField(modifier: Modifier, queryValue: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(ROUNDED_ITEM_VALUE),
        value = queryValue,
        onValueChange = {
            onValueChange(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = CustomYellow
        ),
        singleLine = true,
    )
}