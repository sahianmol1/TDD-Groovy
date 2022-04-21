package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import petros.efthymiou.groovy.data.PlayList

class PlayListRepository(private val service: PlayListService) {
    suspend fun getPlayLists(): Flow<Result<List<PlayList>>> =
        service.fetchPlayLists()

}
