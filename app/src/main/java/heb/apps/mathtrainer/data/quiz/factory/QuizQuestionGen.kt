package heb.apps.mathtrainer.data.quiz.factory

import android.os.Parcel
import android.os.Parcelable
import heb.apps.mathtrainer.data.math.numbers.isNatural
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.data.quiz.question.Question
import heb.apps.mathtrainer.data.quiz.quizquestion.QuizQuestion
import heb.apps.mathtrainer.memory.db.tasks.levels.GetLevelsTask
import heb.apps.mathtrainer.ui.activities.MathBaseActivity
import java.lang.ref.WeakReference
import kotlin.random.Random

class QuizQuestionGen private constructor(activity: MathBaseActivity) {
	private val actRef: WeakReference<MathBaseActivity> = WeakReference(activity)
	private val activity: MathBaseActivity
	get() = actRef.get()!!

	// generate quiz questions
	fun genQuizQuestions(numOfQuestions: Int, filter: Filter = Filter(),
                         onFinish: (List<QuizQuestion>) -> Unit) {
		val input = GenQuestionsInput(numOfQuestions, filter)
		genQuizQuestionsTask(input) { quizQuestions ->
			onFinish(quizQuestions)
		}
	}

	private class GenQuestionsInput(val numOfQuestions: Int, val filter: Filter = Filter())


	// internal task for get quiz questions
	private fun genQuizQuestionsTask(input: GenQuestionsInput,
                                     onFinish: (List<QuizQuestion>) -> Unit) {

		require(input.numOfQuestions.isNatural()) {
			"Number of questions must be bigger or equals to 0"
		}

		// get levels from DB
		val getLevelsTask = GetLevelsTask(activity)
		getLevelsTask.onFinishListener = { levels ->
			val allLevelsIds = getLevelsIds(levels!!)
			val allowedLevelsIds = getAllowedLevelsIds(allLevelsIds, input.filter)

			val allowedLevels = levels.filter { it.id in allowedLevelsIds }

			// check if can random result
			require(allowedLevelsIds.isNotEmpty()) {
				"Cannot generate level id, because allowed levels ids is empty"
			}

			// random levels
			val resultQQs = mutableListOf<QuizQuestion>()
			val previousQuestions = mutableListOf<Question>()
			repeat(input.numOfQuestions) {
				val quizQuestion = createUniqueQuestion(allowedLevels, allowedLevelsIds,
					previousQuestions)
				resultQQs.add(quizQuestion)
				previousQuestions.add(quizQuestion.question)
			}

			onFinish(resultQQs)
		}
		getLevelsTask.start()
	}

	// create unique question, with limit of MAX_TRIES_FOR_CREATE_UNIQUE_QUES
	private fun createUniqueQuestion(levels: List<Level>, levelsIds: Set<Int>,
									 previousQuestions: List<Question>)
			: QuizQuestion {

		// count tries to create unique question
		var countTries = 0

		while(true) {
			countTries++
			val qqFactory = QuizQuestionFactory.instance
			val randLevelId = randLevelId(levelsIds)
			val randLevel = levels.find { it.id == randLevelId }!!
			val quizQuestion = qqFactory.createQQ(randLevel)
			if(quizQuestion.question !in previousQuestions)
				return quizQuestion

			// if not have tires anymore (this prevents infinity loop)
			if(countTries >= MAX_TRIES_FOR_CREATE_UNIQUE_QUES)
				return quizQuestion
		}
	}

	// get allowed levels ids
	private fun getAllowedLevelsIds(allLevelsIds: Set<Int>, filter: Filter) : Set<Int> {
		return when(filter.filterType) {
			Filter.FilterType.WHITE_LIST -> allLevelsIds.intersect(filter.levelsIds)
			Filter.FilterType.BLACK_LIST -> allLevelsIds.minus(filter.levelsIds)
		}
	}

	// random level id
	private fun randLevelId(allowedLevelsIds: Set<Int>) : Int {
		val levelIdIndex = Random.nextInt(0, allowedLevelsIds.size)
		return allowedLevelsIds.elementAt(levelIdIndex)
	}

	// get levels ids
	private fun getLevelsIds(levels: List<Level>): Set<Int> =
		levels.map { level -> level.id }.toSet()

	
	// filter for generator
	class Filter(var filterType: FilterType = FilterType.BLACK_LIST,
                 var levelsIds: Set<Int> = mutableSetOf())
		: Parcelable {


		constructor(input: Parcel) : this() {
			this.filterType = FilterType.values()[input.readInt()]
			this.levelsIds = input.createIntArray()!!.toSet()
		}

		override fun writeToParcel(dest: Parcel, flags: Int) {
			dest.writeInt(filterType.ordinal)
			dest.writeIntArray(levelsIds.toIntArray())
		}

		override fun describeContents() = 0

		
		// check if question type allowed
		fun isQuestionTypeAllowed(levelId: Int) : Boolean
			 = when(filterType) {
				FilterType.BLACK_LIST -> !levelsIds.contains(levelId)
				FilterType.WHITE_LIST -> levelsIds.contains(levelId)
			}


		// convert to string
		override fun toString() : String {
			return "Filter: { filterType=$filterType," +
					"levelsIds=$levelsIds }"
		}

		enum class FilterType {
			BLACK_LIST, WHITE_LIST
		}

		companion object CREATOR : Parcelable.Creator<Filter> {
			override fun createFromParcel(parcel: Parcel): Filter {
				return Filter(parcel)
			}

			override fun newArray(size: Int): Array<Filter?> {
				return arrayOfNulls(size)
			}
		}
	}

	companion object {
		// maximum tries for create unique question
		private const val MAX_TRIES_FOR_CREATE_UNIQUE_QUES = 100

		private const val LOG_TAG = "QuizQuestionGen"
		private var instance: QuizQuestionGen? = null

		// get instance
		fun getInstance(activity: MathBaseActivity) : QuizQuestionGen {
			if(instance == null) {
				QuestionsInfo.registerAll(activity) // Note: this is here instead of QuestionFactory to avoid circular dependency
				instance = QuizQuestionGen(activity)
			}
			return instance!!
		}
	}
}