package com.igs.nfc_rw


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.igs.nfc_rw.data.NFCReader
import com.igs.nfc_rw.ui.ExpandableCard
import com.igs.nfc_rw.ui.NavContainer
import com.igs.nfc_rw.ui.theme.NFCReaderTheme
import com.igs.nfc_rw.utils.Logger


class MainActivity : ComponentActivity() {
    private var mNfcReader: NFCReader? = null

    private val loggerHead = "MainActivity"

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNfcReader = NFCReader(this)
        setContent {
            NFCReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    mNfcReader?.let { nfcReader ->
                        NFCReaderApp(
                            modifier = Modifier.padding(paddingValues),
                            nfcReader = nfcReader
                        )
                    }
                }
            }
        }

        val handleNfcInitError: (String) -> Unit = { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            Logger.d(loggerHead, message)
            finish()
        }

//        mNfcReader?.isSupported()?.let {
//            if (!it)
//                handleNfcInitError("NFC is not available on this device")
//        }
//
//        mNfcReader?.isEnabled()?.let {
//            if (!it)
//                handleNfcInitError("NFC is not enabled on this device")
//        }
    }


    override fun onResume() {
        super.onResume()
        mNfcReader?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mNfcReader?.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Logger.d(loggerHead, "onNewIntent")
        mNfcReader?.handleIntent(intent)
    }


}

@Composable
fun NFCReaderApp(modifier: Modifier = Modifier, nfcReader: NFCReader? = null) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar()
        NavContainer(nfcReader)
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
            Text(
                text = stringResource(R.string.app_top_bar),
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}


@Composable
fun BottomBar() {
    Box {
        ExpandableCard()
    }
}


@Preview(showBackground = true)
@Composable
fun NFCReaderAppPreview() {
    NFCReaderTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            NFCReaderApp(
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
