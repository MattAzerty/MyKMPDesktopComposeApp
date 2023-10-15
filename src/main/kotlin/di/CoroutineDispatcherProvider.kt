package com.melanoxylon.lumicomapp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Single

@Single
class CoroutineDispatcherProvider {
    val main: CoroutineDispatcher = Dispatchers.Main
    val io: CoroutineDispatcher = Dispatchers.IO
}