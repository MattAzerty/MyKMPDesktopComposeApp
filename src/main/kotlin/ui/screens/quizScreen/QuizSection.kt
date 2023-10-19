package ui.screens.quizScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Dizzy
import compose.icons.fontawesomeicons.regular.GrinStars
import data.domain.json.transformed.QuizQuestion
import kotlinx.coroutines.delay
import ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuizSection(
    modifier: Modifier = Modifier,
    localization: Localization,
    quizQuestion: QuizQuestion,
    onAnswerClicked: (isAnswerCorrect: Boolean) -> Unit,
) {

    var rotated by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }

    fun handleAnswerClick(selectedOption: String) {
        isAnswerCorrect = selectedOption == quizQuestion.correctAnswer
        rotated = !rotated
        onAnswerClicked(isAnswerCorrect)
    }

    LaunchedEffect(key1 = rotated) {
        delay(1000)
        rotated = false
    }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .height(200.dp)
                .wrapContentWidth()
                .padding(10.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                },
            shape = RoundedCornerShape(14.dp),
            elevation = 4.dp,
            backgroundColor = DesktopGreyColor,
            contentColor = Color.White
        ) {
            if (!rotated) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                ) {

                    Row(horizontalArrangement = Arrangement.Start) {

                        Image(
                            painter = painterResource("image/album.png"),
                            contentDescription = "Album",
                            modifier = Modifier
                                .padding(DefaultImagePadding)
                                .width(50.dp)
                                .height(50.dp)
                                .clip(CircleShape)
                                .graphicsLayer {
                                    alpha = animateFront
                                },
                        )

                        Spacer(modifier = Modifier.width(MinimumPadding))

                        Text(
                            modifier = Modifier
                                .padding(DefaultImagePadding)
                                .graphicsLayer {
                                    alpha = animateFront
                                },
                            text = quizQuestion.track.album,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )

                    }

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.title,
                            itemText = quizQuestion.track.title ?: "---",
                        )
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.artist,
                            itemText = quizQuestion.track.artist,
                        )
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.genre,
                            itemText = quizQuestion.track.genre,
                        )
                    }

                    Spacer(modifier = Modifier.width(MinimumPadding))

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.year,
                            itemText = if (quizQuestion.track.year == -1) "??" else quizQuestion.track.year.toString(),
                        )
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.tempo,
                            itemText = if (quizQuestion.track.tempo == -1) "??" else quizQuestion.track.tempo.toString(),
                        )
                        AlbumItem(
                            animateFront = animateFront,
                            headerTextItem = localization.rhythm,
                            itemText = quizQuestion.track.rhythm,
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(DefaultItemPadding)
                            .background(DesktopYellowColor)
                            .fillMaxWidth()
                            .height(DefaultSpacerHeight)
                            .graphicsLayer {
                                alpha = animateFront
                            },
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp),
                ) {

                    Icon(
                        imageVector = if(isAnswerCorrect) FontAwesomeIcons.Regular.GrinStars else FontAwesomeIcons.Regular.Dizzy,
                        contentDescription = "validation",
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = animateBack
                                rotationY = rotation
                            }
                            .size(70.dp),
                        tint = if(isAnswerCorrect) DesktopGreenColor else DesktopRedColor
                    )


                    Text(
                        text = if(isAnswerCorrect) localization.correctAnswerQuizScreen else localization.wrongAnswerQuizScreen,
                        color = Color.White,
                        modifier = Modifier
                            .wrapContentSize()
                            .graphicsLayer {
                                alpha = animateBack
                                rotationY = rotation
                            }
                            .padding(DefaultItemPadding),

                        fontFamily = DesktopFontSecondFamily,
                        fontWeight = FontWeight.Thin,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column(
            modifier = modifier,
            content = {

                val rows = quizQuestion.options.chunked(2)

                Text(
                    modifier = Modifier.padding(MinimumPadding),
                    text = localization.optionAnswerFieldHintQuizSection,
                    color = DesktopLightGreyColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )

                rows.forEachIndexed { _, rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(DefaultImagePadding),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEachIndexed { _, option ->

                            var active by remember { mutableStateOf(false) }

                            Button(
                                modifier = Modifier
                                    .onPointerEvent(PointerEventType.Enter) { active = true }
                                    .onPointerEvent(PointerEventType.Exit) { active = false }
                                    .weight(1f)
                                    .padding(DefaultImagePadding),
                                onClick = { handleAnswerClick(option) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = if(active) DesktopYellowColor  else DesktopPurpleColor),
                            ) {
                                Text(
                                    text = option,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    //fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}


