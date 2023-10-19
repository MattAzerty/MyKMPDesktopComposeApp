package ui.screens.homeScreen

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.FEATURE

class HomeScreenModel: ScreenModel,KoinComponent {

    private val dataRepository: DataRepository by inject()

    val features = FEATURE
    val localization = dataRepository.localization
}