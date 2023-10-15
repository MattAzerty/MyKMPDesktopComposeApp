package ui.screens.homeScreen

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.DataRepository
import utils.FEATURE

class HomeScreenModel(
    private val dataRepository: DataRepository,
): ScreenModel {
    val features = FEATURE
    val localization = dataRepository.localization
}