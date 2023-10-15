package data.domain.json.transformed

import data.domain.json.raw.Track
import utils.QuestionType

data class QuizQuestion(
    val track: Track,
    val questionType: QuestionType,
    val correctAnswer: String,
    val options: List<String>
)