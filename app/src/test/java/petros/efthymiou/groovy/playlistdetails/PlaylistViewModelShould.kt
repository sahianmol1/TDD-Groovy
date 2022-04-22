package petros.efthymiou.groovy.playlistdetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import petros.efthymiou.groovy.details.PlaylistDetails
import petros.efthymiou.groovy.details.PlaylistDetailsService
import petros.efthymiou.groovy.details.PlaylistDetailsViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest

@ExperimentalCoroutinesApi
class PlaylistViewModelShould: BaseUnitTest() {

    private val service: PlaylistDetailsService = mock()
    private val id = "1"
    private val playlistDetails = mock<PlaylistDetails>()
    private val expected = Result.success(playlistDetails)
    private val error = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistDetailsFromService() = runTest{
        val viewModel = mockSuccessfulCase()

        viewModel.getPlayListDetails(id)

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() =  runTest{
        val viewModel = mockSuccessfulCase()

        viewModel.getPlayListDetails(id)

        assertEquals(expected, viewModel.playListDetails.getValueForTest()!!)
    }

    @Test
    fun emitsErrorWhenReceivesError() = runTest {
        val viewModel = mockErrorCase()
        viewModel.getPlayListDetails(id)

        assertEquals(Result.failure<Result<PlaylistDetails>>(error), viewModel.playListDetails.getValueForTest())
    }

    private suspend fun mockErrorCase(): PlaylistDetailsViewModel {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.failure(error))
            }
        )

        return PlaylistDetailsViewModel(service)
    }

    private fun mockSuccessfulCase(): PlaylistDetailsViewModel  = runBlocking{
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )

        return@runBlocking PlaylistDetailsViewModel(service)
    }

}