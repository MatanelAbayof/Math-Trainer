package heb.apps.mathtrainer.data.quiz.factory.creators

import android.content.Context
import heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic.*

class QuestionsInfo private constructor() {

	object Categories {

		object Arithmetic {
			const val ID = 1

			object Topics {
				const val ADDITION_ID = 1
				const val SUBTRACTION_ID = 2
				const val MULTIPLICATION_ID = 3
				const val DIVISION_ID = 4
				const val POWER_ID = 5
				const val SQRT_ID = 6
				const val MIX_ASM_ID = 7 // ASM = addition, subtraction. multiplication
				const val MOD_ID = 8 // MOD = Modulo
			}
		}

	}
	
	companion object {

		// register all topics
		fun registerAll(ctx: Context) {
			AddQtc(ctx)
			SubQtc(ctx)
			MultQtc(ctx)
			DivQtc(ctx)
			PowerQtc(ctx)
			SqrtQtc(ctx)
			MixAsmQtc(ctx)
			ModQtc(ctx)
		}
	}
	
}