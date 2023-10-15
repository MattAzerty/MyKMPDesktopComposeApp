package di

import data.repository.DataRepository
import data.repository.DataRepositoryImpl
import org.koin.dsl.module
import ui.screens.homeScreen.HomeScreenModel
import ui.screens.logScreen.LogScreenModel

val appModule = module {
    single<DataRepository> { DataRepositoryImpl() }
    //https://voyager.adriel.cafe/screenmodel
    factory { LogScreenModel(get()) }
    factory { HomeScreenModel(get()) }
}