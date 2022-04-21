package petros.efthymiou.groovy.playlist

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
import petros.efthymiou.groovy.data.PlayList
import petros.efthymiou.groovy.playlist.PlayListRepository
import petros.efthymiou.groovy.playlist.PlayListViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest

@ExperimentalCoroutinesApi
class PlayListViewModelShould : BaseUnitTest() {

    private val repository: PlayListRepository = mock()
    private val playlists = mock<List<PlayList>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlayListFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.playLists.getValueForTest()

        verify(repository, times(1)).getPlayLists()
    }

    @Test
    fun emitsPlayListsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playLists.getValueForTest())
    }

    @Test
    fun emitFailureWhenReceiveFailure() = runTest {
        whenever(repository.getPlayLists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        val viewModel = PlayListViewModel(repository)

        assertEquals(exception, viewModel.playLists.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockSuccessfulCase(): PlayListViewModel {
        return runBlocking {
            whenever(repository.getPlayLists()).thenReturn(
                flow {
                    emit(expected)
                }
            )

            PlayListViewModel(repository)
        }
    }
}