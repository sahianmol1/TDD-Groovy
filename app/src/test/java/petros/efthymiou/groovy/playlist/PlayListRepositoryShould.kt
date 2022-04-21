package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import petros.efthymiou.groovy.data.PlayList
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlayListRepositoryShould: BaseUnitTest() {

    private val service: PlayListService = mock()
    private val playLists: List<PlayList> = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlayListsFromService() = runTest{
        val repository = PlayListRepository(service)

        repository.getPlayLists()

        verify(service, times(1)).fetchPlayLists()
    }

    @Test
    fun emitPlayListsFromService() = runTest {
        val repository = mockSuccessfulCase()

        assertEquals(playLists, repository.getPlayLists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runTest{
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlayLists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlayListRepository {
        whenever(service.fetchPlayLists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return PlayListRepository(service)
    }

    private suspend fun mockSuccessfulCase(): PlayListRepository {
        whenever(service.fetchPlayLists()).thenReturn(
            flow {
                emit(Result.success(playLists))
            }
        )

        return PlayListRepository(service)
    }
}