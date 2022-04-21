package petros.efthymiou.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData

class PlayListViewModel(
    repository: PlayListRepository
) : ViewModel() {
    val playLists = liveData {
        emitSource(repository.getPlayLists().asLiveData())
    }
}
