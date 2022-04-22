package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import petros.efthymiou.groovy.data.PlayList
import javax.inject.Inject

class PlayListRepository @Inject constructor(private val service: PlayListService) {
    suspend fun getPlayLists(): Flow<Result<List<PlayList>>> =
        service.fetchPlayLists()

}
