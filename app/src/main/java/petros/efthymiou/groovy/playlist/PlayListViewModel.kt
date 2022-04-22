package petros.efthymiou.groovy.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach

class PlayListViewModel(
    repository: PlayListRepository
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()

    val playLists = liveData {
        loader.postValue(true)

        emitSource(repository.getPlayLists()
            .onEach { loader.postValue(false) }
            .asLiveData())
    }
}
