package com.example.randomuser.utility

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.TestDispatcher


@ExperimentalCoroutinesApi
class TestLazyPaging<T : Any> constructor(
    private val testDispatcher: TestDispatcher,
    private val flow: Flow<PagingData<T>>
) {

    private var itemSnapshotList by mutableStateOf(
        ItemSnapshotList<T>(0, 0, emptyList())
    )

    val itemCount: Int get() = itemSnapshotList.size

    private val differCallback: DifferCallback = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {
            if (count > 0) {
                updateItemSnapshotList()
            }
        }

        override fun onInserted(position: Int, count: Int) {
            if (count > 0) {
                updateItemSnapshotList()
            }
        }

        override fun onRemoved(position: Int, count: Int) {
            if (count > 0) {
                updateItemSnapshotList()
            }
        }
    }

    private val pagingDataDiffer = object : PagingDataDiffer<T>(
        differCallback = differCallback,
        mainDispatcher = testDispatcher
    ) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ): Int? {
            onListPresentable()
            updateItemSnapshotList()
            return null
        }
    }

    private fun updateItemSnapshotList() {
        itemSnapshotList = pagingDataDiffer.snapshot()
    }

    fun getSnapShot(): ItemSnapshotList<T> {
        return itemSnapshotList
    }

    fun get(index: Int): T? {
        pagingDataDiffer[index] // this registers the value load
        return itemSnapshotList[index]
    }

    fun peek(index: Int): T? {
        return itemSnapshotList[index]
    }

    fun retry() {
        pagingDataDiffer.retry()
    }

    fun refresh() {
        pagingDataDiffer.refresh()
    }

    private val incompleteLoadState = LoadState.NotLoading(false)

    private val initialLoadStates = LoadStates(
        incompleteLoadState,
        incompleteLoadState,
        incompleteLoadState
    )

    /**
     * A [CombinedLoadStates] object which represents the current loading state.
     */
    private var loadState: CombinedLoadStates by mutableStateOf(
        CombinedLoadStates(
            refresh = initialLoadStates.refresh,
            prepend = initialLoadStates.prepend,
            append = initialLoadStates.append,
            source = initialLoadStates
        )
    )

    internal suspend fun collectLoadState() {
        pagingDataDiffer.loadStateFlow.collect {
            loadState = it
        }
    }

    internal suspend fun collectPagingData() {
        flow.collectLatest {
            pagingDataDiffer.collectFrom(it)
        }
    }


}