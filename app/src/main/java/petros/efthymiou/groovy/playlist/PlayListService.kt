package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayListService @Inject constructor(private val api: PlayListAPI) {
    suspend fun fetchPlayLists(): Flow<Result<List<PlayListRaw>>> {
        return flow {
            val playLists = api.fetchAllPlayLists()
            emit(Result.success(playLists))
        }.catch {
            emit(Result.failure(EXCEPTION))
        }
    }

    companion object {
        val EXCEPTION = RuntimeException("Something Went Wrong")
    }

}
