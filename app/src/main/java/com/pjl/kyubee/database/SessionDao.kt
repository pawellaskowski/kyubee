package com.pjl.kyubee.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pjl.kyubee.model.Session
import io.reactivex.Flowable

@Dao
interface SessionDao {

    @Insert
    fun insert(session: Session)

    @Query("SELECT * FROM Session")
    fun getAllSessions(): Flowable<List<Session>>

    @Query("SELECT * FROM Session WHERE id = :id")
    fun getSession(id: Long): Flowable<Session>
}