package com.v4vic.bibleapp

import android.app.Application
import com.v4vic.data.BibleSource
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApp : Application() {
    @Inject
    lateinit var bibleSource: BibleSource
}