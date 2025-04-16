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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.igs.nfc_rw.data.AppDatabase
import com.igs.nfc_rw.data.Attendee
import com.igs.nfc_rw.data.AttendeeDAO
import com.igs.nfc_rw.data.NFCReader
import com.igs.nfc_rw.ui.AttendeeUI
import com.igs.nfc_rw.ui.ExpandableCard
import com.igs.nfc_rw.ui.WelcomeFragment
import com.igs.nfc_rw.ui.theme.NFCReaderTheme
import com.igs.nfc_rw.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    private var mNfcReader: NFCReader? = null
    private var mDatabase: AppDatabase? = null
    private var mDao: AttendeeDAO? = null
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

//        mNfcReader = NFCReader(this)
//
//        val handleNfcInitError: (String) -> Unit = { message ->
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//            Logger.d(loggerHead, message)
//            finish()
//        }
//
//        mNfcReader?.isSupported()?.let {
//            if (!it)
//                handleNfcInitError("NFC is not available on this device")
//        }
//
//        mNfcReader?.isEnabled()?.let {
//            if (!it)
//                handleNfcInitError("NFC is not enabled on this device")
//        }

        mDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "attendee_database"
        ).build()
        mDao = mDatabase?.attendeeDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val newUser = Attendee(firstName = "John", lastName = "Doe", workBadgeId = "123456")
                mDatabase?.attendeeDao()?.insert(newUser)
            }
            Logger.d(loggerHead, "User added to database")
            Toast.makeText(this@MainActivity, "User added to database", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO) {
                mDao?.getAll() // Assume getAll returns List<YourEntity>
            }
            Logger.d(loggerHead, "Attendees: ${data?.size}")
        }
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


@Preview(showBackground = true)
@Composable
fun NFCReaderApp() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        TopBar()
        WelcomeFragment()
        AttendeeUI()
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
