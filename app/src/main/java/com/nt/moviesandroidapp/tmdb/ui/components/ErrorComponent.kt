package com.nt.moviesandroidapp.tmdb.ui.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.ui.theme.CustomYellow

@Composable
fun ErrorComponent(modifier: Modifier, error: ApiError, navController: NavHostController) {
    val activity = LocalContext.current as Activity
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = error.icon),
            contentDescription = "errorIcon",
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(CustomYellow)
        )
        Text(
            text = error.message ?: "Error",
            fontSize = 38.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.LightGray,
        )
        TextButton(onClick = { navController.popBackStack() }) {
            Text(
                text = "Go back",
                fontSize = 18.sp,
                color = Color(0xFF4EA8E9)
            )
        }
    }
}

@Preview
@Composable
fun ErrorPreview() {
    ErrorComponent(
        modifier = Modifier.fillMaxSize(),
        error = ApiError.IncorrectApiKey,
        navController = rememberNavController()
    )
}
