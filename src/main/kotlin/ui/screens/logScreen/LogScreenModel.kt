package ui.screens.logScreen

import cafe.adriel.voyager.core.model.ScreenModel
import data.repository.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LogScreenModel(
    //dataRepository: DataRepository
):ScreenModel,KoinComponent {

    private val dataRepository:DataRepository by inject()

    val uiViewState = LogScreenUIViewState(
        localization = dataRepository.localization,
    )

}