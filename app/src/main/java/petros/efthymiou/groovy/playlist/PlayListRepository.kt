package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.data.PlayList
import javax.inject.Inject

class PlayListRepository @Inject constructor(
    private val service: PlayListService,
    private val mapper: PlayListMapper
) {
    suspend fun getPlayLists(): Flow<Result<List<PlayList>>> =
        service.fetchPlayLists().map {
            if (it.isSuccess) {
                Result.success(mapper(it.getOrNull()!!))
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }

}
