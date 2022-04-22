package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.data.PlayList
import javax.inject.Inject

class PlayListMapper @Inject constructor(): Function1<List<PlayListRaw>, List<PlayList>> {

    override fun invoke(playlistRaw: List<PlayListRaw>): List<PlayList> {
        return playlistRaw.map {

            val image = when(it.category) {
                "rock" -> R.drawable.rock
                else -> R.drawable.playlist
            }

            PlayList(it.name, it.category, it.id, image)
        }
    }

}
