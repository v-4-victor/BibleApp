package com.v4vic.ui_kit

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

class PagerStateImpl(
    initialPage: Int = 0,
    initialPageOffsetFraction: Float = 0f,
    updatedPageCount: Int
) : PagerState(initialPage, initialPageOffsetFraction) {

    override val pageCount: Int = updatedPageCount

    companion object {
        /**
         * To keep current page and current page offset saved
         */
        val Saver: Saver<PagerStateImpl, *> = listSaver(
            save = {
                listOf(
                    it.currentPage,
                    it.currentPageOffsetFraction,
                    it.pageCount
                )
            },
            restore = {
                PagerStateImpl(
                    initialPage = it[0] as Int,
                    initialPageOffsetFraction = it[1] as Float,
                    updatedPageCount = it[2] as Int
                )
            }
        )
    }
}