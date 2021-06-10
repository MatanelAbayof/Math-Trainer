package heb.apps.mathtrainer.data.quiz.answer

open class TextAnswer(open var answerText: String = "") : Answer() {

	// convert to equation
	override fun toEquation() : String = answerText
	
	override fun equals(other: Any?) : Boolean {
		if(other !is TextAnswer)
			return false
		return answerText == other.answerText
	}
	
	// convert to string
	override fun toString() : String {
		return "TextAnswer: { AnswerText=$answerText, ${super.toString()} }"
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + answerText.hashCode()
		return result
	}
}