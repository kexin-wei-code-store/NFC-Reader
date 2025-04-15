package com.igs.nfc_rw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.igs.nfc_rw.data.NFCReader
import com.igs.nfc_rw.ui.ExpandableCard
import com.igs.nfc_rw.ui.theme.NFCReaderTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCReaderTheme {
                NFCReaderPreview()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NFCReaderPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        NFCReaderApp()
    }
}


@Composable
fun NFCReaderApp() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        TopBar()
        NFCReader()
        BottomBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(text = "NFC Reader", style = MaterialTheme.typography.titleLarge)
        },
    )
}




@Preview
@Composable
fun BottomBar() {
    Box {
        ExpandableCard()
    }
}
