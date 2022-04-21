package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.data.PlayList
import java.lang.RuntimeException

class PlayListService(private val api: PlayListAPI) {
    suspend fun fetchPlayLists(): Flow<Result<List<PlayList>>> {
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
