package com.igs.nfc_rw.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.igs.nfc_rw.R
import com.igs.nfc_rw.utils.Logger

@Composable
fun NFCReaderUI() {
    val padding = 30f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(padding.dp)
        ) {
            Text(
                text = stringResource(R.string.greeting_igs),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = padding.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.nfc),
                contentDescription = "NFC Reading Icon",
            )
            Button(
                onClick = ::onClickButton,
                modifier = Modifier.padding(bottom = padding.dp)
            ) {
                Text("Read NFC")
            }
        }
    }
}

private fun onClickButton() {
    Logger.d("MainActivity", "Button clicked")
}