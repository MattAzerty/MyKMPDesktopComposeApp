package ui.screens.quizScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import ui.composable.AppBar
import ui.composable.Footer
import ui.composable.scrollable.QuestionListIndicatorWithScrollBar
import ui.theme.*
import ui.window.MyApplicationWindowState

data class QuizScreen(
    val state: MyApplicationWindowState
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<QuizScreenModel>()

        //UI STATE
        val appBarText = screenModel.uiState.appBarTextFlow.collectAsState()
        val formattedTime = screenModel.uiState.formattedTimeCounterFlow.collectAsState()
        val quizQuestions = screenModel.uiState.quizQuestionFlow.collectAsState()
        val resultList = screenModel.uiState.resultListFlow.collectAsState()

        val isThisQuizNotOver = derivedStateOf { resultList.value[resultList.value.size - 1] == null }

        val quizSectionScrollState = rememberScrollState(0)

        LifecycleEffect(
            onStarted = { screenModel.startJob() },
            onDisposed = { screenModel.cancelJob() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesktopBackgroundColor)
        ) {

            Row(
                modifier = Modifier
                    .padding(vertical = DefaultItemPadding, horizontal = MinimumPadding)
                    .weight(0.9f)
                    .wrapContentSize()
            ) {
                // [1] - LEFT QUESTION TYPE INDICATOR
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = DesktopBlackColor,
                    elevation = DefaultShadowElevation,
                    modifier = Modifier
                        .weight(0.9f)
                        .width(200.dp)
                        .padding(DefaultItemPadding)
                ) {
                    quizQuestions.value?.let {
                        QuestionListIndicatorWithScrollBar(
                            localization = screenModel.uiState.localization,
                            listOfQuizQuestion = it,
                            result = resultList.value
                        )
                    }

                }
                    if (formattedTime.value != null && isThisQuizNotOver.value) {
                        TimerDisplay(
                            modifier = Modifier.weight(0.1f),
                            formattedTime = formattedTime.value!!
                        )
                    }

                }
                // [2] - RIGHT SCREEN WITH QUIZ INTERFACE
                Column {

                    AppBar(appBarText = appBarText.value)

                    Spacer(modifier = Modifier.height(DefaultItemPadding))

                    quizQuestions.value?.let { quizQuestions ->

                        if (isThisQuizNotOver.value) {
                            // [2.1] Vertical scroll box for Quiz section
                            Box(Modifier.fillMaxSize()) {

                                Box(modifier = Modifier.verticalScroll(quizSectionScrollState)) {

                                    QuizSection(
                                        localization = screenModel.uiState.localization,
                                        quizQuestion = quizQuestions[resultList.value.indexOfFirst { it == null }],
                                        onAnswerClicked = {
                                            screenModel.onAnswerClicked(it)
                                        }
                                    )

                                }

                                VerticalScrollbar(
                                    adapter = rememberScrollbarAdapter(quizSectionScrollState),
                                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                                )
                            }

                        } else {
                            // [2.2] Alternative UI when quiz over
                            var text by remember { mutableStateOf("") }
                            Text(
                                text = "${screenModel.uiState.localization.score} ${resultList.value.count { it == true }}",
                                style = TextStyle(
                                    color = DesktopLightGreyColor,
                                    fontSize = 24.sp,
                                )
                            )

                            Button({ screenModel.onResetButtonPressed() }) { Text(screenModel.uiState.localization.resetButtonQuizScreen) }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {

                                TextField(
                                    value = text,
                                    onValueChange = { text = it.take(3).uppercase() },
                                    textStyle = TextStyle(
                                        fontSize = 24.sp,
                                        color = DesktopLightGreyColor
                                    ),
                                    singleLine = true,
                                )

                                Button({screenModel.onSaveScoreButtonClicked(text)}){ Text(screenModel.uiState.localization.saveScoreButtonQuizScreen)}

                            }



                        }
                    }
                }
            }
            // [3] - FOOTER
            Footer(Modifier.weight(0.1f))
        }

    }
}