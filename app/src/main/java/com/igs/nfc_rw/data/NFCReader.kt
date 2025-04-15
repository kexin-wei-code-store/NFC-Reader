package com.igs.nfc_rw.data

import android.app.Activity
import android.app.PendingIntent
import android.nfc.NfcAdapter

class NFCReader(private val activity: Activity) {
    private var nfcAdapter: NfcAdapter? = null
    private var nfcPendingIntent: PendingIntent? = null

    var onTagDiscovered: ((tagId: String, data: String) -> Unit)? = null

    init {

    }

    private fun initNfcAdapter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        nfcPendingIntent = PendingIntent.getActivity(
            activity,
            0,
            activity.intent, PendingIntent.FLAG_MUTABLE
        )

    }
}