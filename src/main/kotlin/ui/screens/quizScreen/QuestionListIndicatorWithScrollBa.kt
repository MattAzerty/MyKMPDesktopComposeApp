package ui.screens.quizScreen

import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.domain.json.transformed.QuizQuestion
import ui.theme.DesktopGreenColor
import ui.theme.DesktopRedColor
import ui.theme.DesktopYellowColor
import ui.theme.Localization
import utils.getTranslatedTypeOfQuestionString

@Composable
fun QuestionListIndicatorWithScrollBar(
    localization: Localization,
    listOfQuizQuestion:List<QuizQuestion>,
    questionResultList: List<Boolean?>,
) {

    Box(
        modifier = Modifier.wrapContentSize()
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()

        LazyColumn(
            Modifier.padding(end = 12.dp),
            state
        ) {

            items(questionResultList.size) { index ->
                QuestionItem(
                    text = "#${index + 1}: ${
                        getTranslatedTypeOfQuestionString(
                            localization,
                            listOfQuizQuestion[index].questionType
                        )
                    }",
                    questionResult = questionResultList[index]
                )
                Spacer(modifier = Modifier.height(5.dp))
            }

        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            style = ScrollbarStyle(
                unhoverColor = DesktopYellowColor,
                shape = CircleShape,
                hoverColor = DesktopRedColor,
                thickness = 8.dp,
                minimalHeight = 8.dp,
                hoverDurationMillis = 20
            ),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )

    }
}

@Composable
fun QuestionItem(
    text: String = "Item",
    questionResult:Boolean?,
) {

    val itemColor = when(questionResult){
        true -> DesktopGreenColor
        false -> DesktopRedColor
        null -> DesktopYellowColor
    }

    Box(
        modifier = Modifier.height(32.dp)
            .fillMaxWidth()
            .background(color = itemColor)
            .padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text)
    }
}