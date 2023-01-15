package com.example.driedpork.composable.convert

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.driedpork.composable.home.CryptoItem
import com.example.driedpork.screen.convert.ConvertScreenViewModel
import com.example.driedpork.screen.convert.ConvertedCoinItem
import java.text.NumberFormat


@Composable
fun ConvertScreen(
    convertScreenViewModel: ConvertScreenViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val uiState by convertScreenViewModel.uiState.collectAsState()
        val coinList = uiState.convertedCoinsList as List<ConvertedCoinItem>
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text("USD")
            InputPriceTextField(model = convertScreenViewModel)
        }

        ConvertResults(coins = coinList)
    }
}

@Composable
fun InputPriceTextField(model: ConvertScreenViewModel) {
    val queryText = remember { mutableStateOf("1") }
    TextField(
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        value = queryText.value,
        onValueChange = {
            queryText.value = it
            model.submitValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Blue,
            textColor = Color.Blue
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp)

    )
}

@Composable
fun ConvertResults(coins: List<ConvertedCoinItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(coins.size) { index ->
            ConvertDisplayItem(coin = coins[index])
        }
    }
}

@Composable
fun ConvertDisplayItem(coin: ConvertedCoinItem) {
    val numberFormat: NumberFormat = NumberFormat.getNumberInstance();
    numberFormat.maximumFractionDigits = 10
    numberFormat.minimumFractionDigits = 2
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                // crypto icon
                Image(
                    painter = rememberImagePainter(coin.image),
                    contentDescription = "Crypto Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterVertically)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // crypto name
                    Text(coin.name)
                    // price
                    Text(numberFormat.format(coin.convertedPrice))
                }
            }
        }
    }
}
