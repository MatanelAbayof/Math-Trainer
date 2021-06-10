package heb.apps.mathtrainer.data.quiz.factory

import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.quizquestion.QuizQuestion

// create instance function. int param is level difficulty
private typealias CreateInsFunc = (Int) -> QuizQuestion

class QuizQuestionFactory private constructor() {
	
	 // key is topic id
	 private var map = mutableMapOf<Int, CreateInsFunc>()
	
	// register it
	fun register(topicId: Int, createInsFunc: CreateInsFunc) {
		// check if map have a key
		if (map.contains(topicId))
			throw Exception("QuizQuestionFactory map already contains the topicId=$topicId")
		map[topicId] = createInsFunc
	}
	
	// create quiz question
	fun createQQ(level: Level) : QuizQuestion {
		// check if map don't have a key
		if (!map.contains(level.topicId))
			throw Exception("QuizQuestionFactory map not contains the topicId=${level.topicId}")
		// get create instance function
		val createInsFunc = map[level.topicId]!!
		// return instance
		val qq = createInsFunc(level.difficulty)
		qq.level = level
		return qq
	}
	
	companion object {
        val instance by lazy { QuizQuestionFactory() }
    }
}