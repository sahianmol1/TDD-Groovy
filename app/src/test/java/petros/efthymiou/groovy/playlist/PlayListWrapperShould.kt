package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.utils.BaseUnitTest

@ExperimentalCoroutinesApi
class PlayListWrapperShould: BaseUnitTest() {

    private val playlistRaw = PlayListRaw("title", "category", "1")
    private val playlistRawRock = PlayListRaw("title", "rock", "1")
    val mapper = PlayListMapper()
    private val playlists = mapper(listOf(playlistRaw))
    private val playListsRock = mapper(listOf(playlistRawRock))

    @Test
    fun keepTheIdSame() {
        assertEquals(playlistRaw.id, playlists[0].id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlists[0].name)
    }

    @Test
    fun keepCategorySame() {
        assertEquals(playlistRaw.category, playlists[0].category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.drawable.playlist, playlists[0].image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.rock, playListsRock[0].image)
    }
}