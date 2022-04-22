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
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
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
        val viewModel = mockFailureCase()

        assertEquals(exception, viewModel.playLists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showLoaderWhileFetchingTheDataFromRemote() {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.playLists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlayListLoaded(){
        val viewmodel = mockSuccessfulCase()

        viewmodel.loader.captureValues {
            viewmodel.playLists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playLists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun showLoaderBeforeError() = runTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playLists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    private suspend fun mockFailureCase(): PlayListViewModel {
        whenever(repository.getPlayLists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return PlayListViewModel(repository)
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