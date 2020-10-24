package com.anikulki.movie.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.anikulki.movie.utils.common.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<DB, REMOTE> {

    abstract suspend fun saveRemoteData(response: REMOTE)

    abstract fun getFromDatabase(): Flow<DB>

    abstract suspend fun getFromRemoteServer(): Response<REMOTE>

    fun asFlow() = flow<State<DB>> {
        emit(State.loading())

        emit(State.success(getFromDatabase().first()))

        val serverResponse = getFromRemoteServer()

        val responseBody = serverResponse.body()

        if(serverResponse.isSuccessful && responseBody != null){
            saveRemoteData(responseBody)
        }else{
            emit(State.error(serverResponse.message()))
        }

        emitAll(
            getFromDatabase().map {
                State.success(it)
            }
        )
    }.catch { e ->
        emit(State.error(e.localizedMessage ?: ""))
        e.printStackTrace()
    }
}