package com.igs.nfc_rw.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttendeeDAO {

    @Query("SELECT * FROM attendee")
    fun getAll(): List<Attendee>

    @Query("SELECT * FROM attendee WHERE uid IN (:attendeeIds)")
    fun loadAllByIds(attendeeIds: IntArray): List<Attendee>

    @Query(
        "SELECT * FROM attendee WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByFirstName(first: String, last: String): Attendee

    @Insert
    fun insertAll(vararg attendees: Attendee)

    @Insert
    fun insert(attendee: Attendee)

    @Delete
    fun delete(attendee: Attendee)


}

