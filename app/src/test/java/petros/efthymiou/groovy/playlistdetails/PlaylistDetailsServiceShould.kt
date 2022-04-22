package petros.efthymiou.groovy.playlistdetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import petros.efthymiou.groovy.details.PlaylistDetails
import petros.efthymiou.groovy.details.PlaylistDetailsApi
import petros.efthymiou.groovy.details.PlaylistDetailsService
import petros.efthymiou.groovy.utils.BaseUnitTest

@ExperimentalCoroutinesApi
class PlaylistDetailsServiceShould: BaseUnitTest() {

    private val id = "1"
    private val api : PlaylistDetailsApi = mock<PlaylistDetailsApi>()
    private val playlistDetails: PlaylistDetails = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runTest{
        val service = mockSuccessfulCase()

        service.fetchPlaylistDetails(id).first()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowAndEmitThem() = runTest{
        val service = mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        val service = mockErrorCase()

        assertEquals("Something went wrong", service.fetchPlaylistDetails(id).first().exceptionOrNull()!!.message)
    }

    private suspend fun mockErrorCase(): PlaylistDetailsService {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(
            exception
        )

        return PlaylistDetailsService(api)
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailsService {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(
            playlistDetails
        )

        return PlaylistDetailsService(api)
    }
}