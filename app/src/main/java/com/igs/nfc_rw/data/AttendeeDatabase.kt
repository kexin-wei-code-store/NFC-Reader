package com.igs.nfc_rw.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Attendee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attendeeDao(): AttendeeDAO
}