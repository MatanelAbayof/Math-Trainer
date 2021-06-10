package heb.apps.mathtrainer.data.quiz.answer


abstract class Answer {
	
	abstract override fun equals(other: Any?) : Boolean

	// convert to equation
	abstract fun toEquation() : String
	
	// convert to string
	override fun toString() : String {
		return "Answer: {}"
	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}
}