package di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mattazerty.MyDatabase
import com.melanoxylon.lumicomapp.di.CoroutineDispatcherProvider
import data.repository.DataRepository
import data.repository.DataRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.dsl.module
import ui.screens.homeScreen.HomeScreenModel
import ui.screens.logScreen.LogScreenModel
import ui.screens.quizScreen.QuizScreenModel
import utils.DATABASE_NAME

val appModule = module {
    single { CoroutineDispatcherProvider() }
    single { createDb() }
    single<DataRepository> { DataRepositoryImpl(get()) }
    //https://voyager.adriel.cafe/screenmodel
    factory { LogScreenModel(get()) }
    factory { HomeScreenModel(get()) }
    factory { QuizScreenModel(get(), get()) }
}

fun createDb(): MyDatabase {
    val driver = JdbcSqliteDriver("jdbc:sqlite:$DATABASE_NAME.db")
    MyDatabase.Schema.create(driver)
    return MyDatabase(driver)
}