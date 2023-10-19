package di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mattazerty.db.MyDatabase
import com.melanoxylon.lumicomapp.di.CoroutineDispatcherProvider
import data.repository.DataRepository
import data.repository.DataRepositoryImpl
import org.koin.dsl.module
import ui.screens.homeScreen.HomeScreenModel
import ui.screens.logScreen.LogScreenModel
import ui.screens.quizScreen.QuizScreenModel
import utils.DATABASE_NAME

val appModule = module {
    single {
        val driver = JdbcSqliteDriver("jdbc:sqlite:$DATABASE_NAME.db", schema = MyDatabase.Schema)
        MyDatabaseWrapper(MyDatabase(driver))
    }
    single { CoroutineDispatcherProvider() }
    single<DataRepository> { DataRepositoryImpl() }
    //https://voyager.adriel.cafe/screenmodel
    factory { LogScreenModel() }
    factory { HomeScreenModel() }
    factory { QuizScreenModel() }
}

