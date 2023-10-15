package ui.screens.logScreen

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.DataRepository

class LogScreenModel(
    dataRepository: DataRepository
):ScreenModel {


    val uiViewState = LogScreenUIViewState(
        localization = dataRepository.localization,
    )

    private fun onStartButtonClicked() {
    }

}