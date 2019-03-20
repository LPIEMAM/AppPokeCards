package lpiemam.com.apppokecards.model

import java.util.*

class PokemonQuestions(var questionList: List<Question>?) {
    var nextQuestionIndex = 0

    val question: Question
        get() {
            if (nextQuestionIndex == questionList!!.size) {
                nextQuestionIndex = 0
            }
            return questionList!![nextQuestionIndex++]
        }

    init {
        Collections.shuffle(this.questionList)
    }
}
