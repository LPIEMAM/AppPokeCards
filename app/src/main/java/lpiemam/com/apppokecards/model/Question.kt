package lpiemam.com.apppokecards.model

class Question(question: String, choiceList: List<String>, answerIndex: Int) {
    var question: String? = null
    var choiceList: List<String>? = null
        set(choiceList) {
            if (choiceList == null) {
                throw IllegalArgumentException("Array cannot be null")
            }

            field = choiceList
        }
    var answerIndex: Int = 0
        set(answerIndex) {
            if (answerIndex < 0 || answerIndex > choiceList!!.size) {
                throw IllegalArgumentException("Answer index is out of bound")
            }

            field = answerIndex
        }

    init {
        this.question = question
        this.choiceList = choiceList
        this.answerIndex = answerIndex
    }

    override fun toString(): String {
        return "Question{" +
                "mQuestion='" + question + '\''.toString() +
                ", mChoiceList=" + choiceList +
                ", mAnswerIndex=" + answerIndex +
                '}'.toString()
    }
}
