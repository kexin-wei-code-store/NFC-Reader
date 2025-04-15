package com.igs.nfc_rw


import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
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
import com.igs.nfc_rw.ui.ExpandableCard
import com.igs.nfc_rw.ui.NFCReaderUI
import com.igs.nfc_rw.ui.theme.NFCReaderTheme
import com.igs.nfc_rw.utils.Logger


class MainActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private val loggerHead = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCReaderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NFCReaderApp()
                }
            }
        }
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        val handleNfcInitError: (String) -> Unit = { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            Logger.d(loggerHead, message)
            finish()
        }

        if (nfcAdapter == null) {
            handleNfcInitError("NFC is not available on this device")
            return
        }
        Logger.d(loggerHead, "NFC is available on this device")
        if (!nfcAdapter!!.isEnabled) {
            handleNfcInitError("NFC is not enabled on this device")
            return
        }
        Logger.d(loggerHead, "NFC is enabled on this device")

    }


//    override fun onResume() {
//        super.onResume()
//        nfcAdapter?.enableForegroundDispatch(this, null, null, null)
//    }
}

@Preview(showBackground = true)
@Composable
fun NFCReaderApp() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        TopBar()
        NFCReaderUI()
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
