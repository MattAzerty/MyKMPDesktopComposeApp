package data.repository

import org.koin.core.annotation.Single
import ui.theme.getCurrentLocalization


@Single
class DataRepositoryImpl : DataRepository {

    override val localization = getCurrentLocalization()

}