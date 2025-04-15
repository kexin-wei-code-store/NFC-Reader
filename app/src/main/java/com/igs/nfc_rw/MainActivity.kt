package com.igs.nfc_rw


import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Build
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
    private var nfcPendingIntent: PendingIntent? = null
    private val nfcIntentFiltersArarry: Array<IntentFilter>? = null
    private val nfcTechListsArray: Array<Array<String>>? = null

    private val loggerHead = "MainActivity"

    @SuppressLint("UnsafeIntentLaunch")
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

        // Create PendingIntent with proper flags for Android 15 compatibility
        val flags = PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        // create pending intent for NFC intent
        val finalFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            flags or PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT
        } else {
            flags
        }
        nfcPendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            finalFlags
        )

        handleIntent(intent)
    }


    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(
            this,
            { tag ->
//                // Handle tag discovery here
//                val tagId = tag.id.toHexString()
//                val data = readFromTag(tag)
//
//                runOnUiThread {
//                    // Update UI
//                    tagInfoTextView.text = "Tag ID: $tagId\nData: $data"
//                }
                Logger.d(loggerHead, "Tag discovered")
            },
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V,
            null
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action
        Logger.d(loggerHead, "Action: $action")
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action) {
            Logger.d(loggerHead, "Tag discovered")
            val tag = intent.getStringExtra(NfcAdapter.EXTRA_TAG)
            tag?.let {
                // get the tag id
                val tagId = it
                // do something with the tag id
                Logger.d(loggerHead, "Tag ID: $tagId")
            }
        }

    }
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
