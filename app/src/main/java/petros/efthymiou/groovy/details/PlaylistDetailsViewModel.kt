package petros.efthymiou.groovy.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(private val service: PlaylistDetailsService) : ViewModel() {

    val loader = MutableLiveData<Boolean>()
    val playListDetails = MutableLiveData<Result<PlaylistDetails>>()

    fun getPlayListDetails(id: String) {
        loader.value = true
        viewModelScope.launch {
            service.fetchPlaylistDetails(id).collect {
                playListDetails.value = it
                loader.value = false
            }
        }
    }

}
