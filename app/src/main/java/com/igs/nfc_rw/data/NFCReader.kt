package com.igs.nfc_rw.data

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.os.Build
import android.widget.Toast
import com.igs.nfc_rw.utils.Logger

enum class NFCStatus {
    UNINITIALIZED,
    READY,
    UNSUPPORTED,
    DISABLED,
}

class NFCReader(private val activity: Activity) {
    private var mNfcAdapter: NfcAdapter? = null
    private var mPendingIntent: PendingIntent? = null
    private val mIntentFiltersArarry: Array<IntentFilter>? = null
    private val mTechListsArray: Array<Array<String>>? = null
    var status: NFCStatus = NFCStatus.UNINITIALIZED
    private val loggerHead = "NFCReader"


    init {
        initNfcAdapter()
    }

    fun isSupported(): Boolean {
        return mNfcAdapter != null
    }

    fun isEnabled(): Boolean {
        return mNfcAdapter?.isEnabled == true
    }

    private fun initNfcAdapter() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (mNfcAdapter == null) {
            Logger.d(loggerHead, "NFC is not available on this device")
            status = NFCStatus.UNSUPPORTED
            return
        }
        Logger.d(loggerHead, "NFC is available on this device")
        if (!mNfcAdapter!!.isEnabled) {
            Logger.d(loggerHead, "NFC is not enabled on this device")
            status = NFCStatus.DISABLED
            return
        }
        Logger.d(loggerHead, "NFC is enabled on this device")
        status = NFCStatus.READY

        // Create PendingIntent with proper flags for Android 15 compatibility
        val flags = PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        // create pending intent for NFC intent
        val finalFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            flags or PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT
        } else {
            flags
        }

        mPendingIntent = PendingIntent.getActivity(
            activity,
            0,
            Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            finalFlags
        )
        handleIntent(activity.intent)
    }

    fun handleIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == action
        ) {
            Logger.d(loggerHead, "Tag discovered")
            Toast.makeText(activity, "Tag discovered", Toast.LENGTH_SHORT).show()
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.readTag()
        }
    }

    fun onResume() {
        mNfcAdapter?.enableForegroundDispatch(
            activity,
            mPendingIntent,
            mIntentFiltersArarry,
            mTechListsArray
        )

    }

    fun onPause() {
        mNfcAdapter?.disableForegroundDispatch(activity)
    }

    private fun Tag.readTag() {
        Logger.d(loggerHead, "Tag ID: $id")
        val result = StringBuilder()

        for (tech in this.techList) {
            if (tech == MifareClassic::class.java.name) {
                val mifareTag = MifareClassic.get(this)
                if (mifareTag == null) {
                    Logger.d(loggerHead, "Tag type: Unknown")
                    return
                }
                Logger.d(loggerHead, "Tag type: MifareClassic")
                result.append("Tag ID: ${id.toHexString()}\n")
                result.append(readMemory(mifareTag))
            }
        }

        Logger.d(loggerHead, result.toString())
    }

    private fun readMemory(mifareTag: MifareClassic): String {
        var result = StringBuilder()
        try {
            mifareTag.connect()

            // get tag type
            val tagSize = when (mifareTag.size) {
                MifareClassic.SIZE_1K -> "1K"
                MifareClassic.SIZE_2K -> "2K"
                MifareClassic.SIZE_4K -> "4K"
                else -> "Unknown"
            }
            result.append("Tag type: Mifare Classic $tagSize\n")

            // loop all the sectors
            for (sectorIndex in 0 until mifareTag.sectorCount) {
                var authenticated = false
                try {
                    authenticated = mifareTag.authenticateSectorWithKeyA(
                        sectorIndex,
                        MifareClassic.KEY_DEFAULT
                    ) ||
                            mifareTag.authenticateSectorWithKeyB(
                                sectorIndex,
                                MifareClassic.KEY_DEFAULT
                            )

                } catch (e: Exception) {
                    Logger.e(loggerHead, "Error authenticating sector", e)
                }
                result.append("Sector: $sectorIndex:\n")
                if (authenticated) {
                    val firstBlockIndex = mifareTag.sectorToBlock(sectorIndex)
                    val blockCount = mifareTag.getBlockCountInSector(sectorIndex)

                    for (i in 0 until blockCount) {
                        val blockIndex = firstBlockIndex + i
                        try {
                            val data = mifareTag.readBlock(blockIndex)
                            result.append(
                                "  Block $blockIndex: ${data.toHexString()}\n" +
                                        ""
                            )

                        } catch (e: Exception) {
                            result.append("  Block $blockIndex: Error reading block")
                        }
                    }
                }
            }
            mifareTag.close()

            Logger.d(loggerHead, "The data is:\n$result")

        } catch (e: Exception) {
            Logger.e(loggerHead, "Error reading tag", e)
        } finally {
            try {
                mifareTag.close()
            } catch (e: Exception) {

            }
        }
        return result.toString()
    }

    private fun ByteArray.toHexString(): String {
        return joinToString(" ") { "%02x".format(it) }
    }

}