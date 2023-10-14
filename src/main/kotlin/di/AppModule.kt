package di

import data.repository.DataRepository
import data.repository.DataRepositoryImpl
import data.repository.DataService
import org.koin.dsl.module
import ui.screens.LogScreenModel

val appModule = module {
    single<DataRepository> { DataRepositoryImpl() }
    single { DataService(get()) }
    factory { LogScreenModel(get()) }//https://voyager.adriel.cafe/screenmodel
}