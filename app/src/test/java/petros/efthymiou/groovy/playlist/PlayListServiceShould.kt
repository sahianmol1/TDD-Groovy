package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayListServiceShould {

    private val api: PlayListAPI = mock()
    private val playLists: List<PlayListRaw> = mock()

    @Test
    fun fetchPlayListsFromAPI() = runTest {
        val service = PlayListService(api)
        service.fetchPlayLists().first()

        verify(api, times(1)).fetchAllPlayLists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runTest {
        val service = mockSuccessfulCase()

        assertEquals(playLists, service.fetchPlayLists().first().getOrNull())
    }

    private suspend fun mockSuccessfulCase(): PlayListService {
        whenever(api.fetchAllPlayLists()).thenReturn(
            playLists
        )

        return PlayListService(api)
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        val service = mockFailureCase()
        assertEquals(
            "Something Went Wrong",
            service.fetchPlayLists().first().exceptionOrNull()!!.message
        )
    }

    private suspend fun mockFailureCase(): PlayListService {
        whenever(api.fetchAllPlayLists()).thenThrow(RuntimeException("Damn backend Developers"))

        return PlayListService(api)
    }
}