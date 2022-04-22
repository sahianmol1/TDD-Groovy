package petros.efthymiou.groovy.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(private val api: PlaylistDetailsApi) {
    suspend fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> {
        return flow<Result<PlaylistDetails>> {
            val details = api.fetchPlaylistDetails(id)
            emit(Result.success(details))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
