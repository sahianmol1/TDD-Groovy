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

@ExperimentalCoroutinesApi
class PlayListRepositoryShould: BaseUnitTest() {

    private val service: PlayListService = mock()
    private val playLists: List<PlayList> = mock()
    private val playListsRaw: List<PlayListRaw> = mock()
    private val mapper: PlayListMapper = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlayListsFromService() = runTest{
        val repository = mockSuccessfulCase()

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

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccessfulCase()

        repository.getPlayLists().first()
        verify(mapper, times(1)).invoke(playListsRaw)
    }

    private suspend fun mockFailureCase(): PlayListRepository {
        whenever(service.fetchPlayLists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        whenever(mapper.invoke(playListsRaw)).thenReturn(playLists)

        return PlayListRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlayListRepository {
        whenever(service.fetchPlayLists()).thenReturn(
            flow {
                emit(Result.success(playListsRaw))
            }
        )
        whenever(mapper.invoke(playListsRaw)).thenReturn(playLists)
        return PlayListRepository(service, mapper)
    }
}