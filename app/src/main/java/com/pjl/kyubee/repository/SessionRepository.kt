package com.pjl.kyubee.repository

import com.pjl.kyubee.database.SessionDao
import com.pjl.kyubee.model.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(private val sessionDao: SessionDao) {

    fun insert(session: Session) = sessionDao.insert(session)

    fun getAllSessions() = sessionDao.getAllSessions()

    fun getSession(id: Long) = sessionDao.getSession(id)
}