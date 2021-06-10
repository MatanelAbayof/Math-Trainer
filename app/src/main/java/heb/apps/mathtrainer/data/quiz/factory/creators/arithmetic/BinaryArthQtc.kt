package heb.apps.mathtrainer.data.quiz.factory.creators.arithmetic

import android.content.Context
import heb.apps.mathtrainer.data.math.MathEvaluateAble
import heb.apps.mathtrainer.data.math.function.binary.BinaryOperation
import heb.apps.mathtrainer.data.math.type.IntNum
import heb.apps.mathtrainer.data.math.utils.IntegerRange
import heb.apps.mathtrainer.data.math.utils.RandomHelper
import kotlin.math.floor

// binary arithmetic QTC
abstract class BinaryArthQtc(ctx: Context, topicId: Int) : ArithmeticQtc(ctx, topicId) {

    // relation of maximum operand per level difficulty
    protected open val maxOperandPerLd = DEF_MAX_OPERAND_PER_LD
    // minimum of operands
    protected open val minOperands = DEF_MIN_OPERANDS
    // relation of number of operands per level difficulty
    protected open val numOfOperandsPerLd = DEF_NUM_OPERANDS_PER_LD
    // minimum operand number
    protected open val minOperandNum = DEF_OPERAND_MIN_NUM

    init {
        printInfo.isParenthesesUsed = false
    }

    // random rational number
    override fun randCorrectAnswer(levelDiff: Int) : BinaryOperation<Int?, Int> {
        val operands = randOperandsChecked(levelDiff).map { IntNum(it) }
        return createBinaryFunction(operands)
    }

    // create function of N operands
    private fun createBinaryFunction(operands: List<MathEvaluateAble<Int?, Int>>)
            : BinaryOperation<Int?, Int> {
        return when {
            operands.size < DEF_MIN_OPERANDS -> throw IllegalArgumentException("Cannot build binary " +
                                                    "function with smaller then $DEF_MIN_OPERANDS operands")
            operands.size == DEF_MIN_OPERANDS -> createBinaryFunction(operands[0], operands[1])
            else -> {
                val leftOperand = createBinaryFunction(operands.dropLast(1))
                val rightOperand = operands.last()
                createBinaryFunction(leftOperand, rightOperand)
            }
        }
    }

    // create function of 2 operands
    protected abstract fun createBinaryFunction(leftOperand: MathEvaluateAble<Int?, Int>,
                                                rightOperand: MathEvaluateAble<Int?, Int>)
            : BinaryOperation<Int?, Int>

    // get number of operands
    private fun getNumOfOperands(levelDiff: Int) : Int
            = floor((levelDiff.toFloat()*numOfOperandsPerLd) + minOperands).toInt()

    // get operand range
    private fun getOperandRange(levelDiff: Int) : IntegerRange
        = IntegerRange(minOperandNum, getMaxOperandNum(levelDiff)+1)

    // calculate result of operands
    protected abstract fun calcResult(operands: List<Int>) : Int

    // random list of operands with checking
    private fun randOperandsChecked(levelDiff: Int) : List<Int> {
        val numOfOperands = getNumOfOperands(levelDiff)
        val operandRange = getOperandRange(levelDiff)
        val randFunc: () -> List<Int> = {
            randOperandsUnchecked(levelDiff, numOfOperands, operandRange)
        }
        val checkFunc: (List<Int>) -> Boolean = { operands ->
            val answer = calcResult(operands)
            canBeResNum(levelDiff, answer)
        }

        return tryRandWithLimit(randFunc, checkFunc)
    }

    // random operands without checking
    protected open fun randOperandsUnchecked(levelDiff: Int, numOfOperands: Int,
                                             operandRange: IntegerRange) : List<Int>
        = RandomHelper.nextArr(numOfOperands, operandRange)

    // calculate maximum operand number
    private fun getMaxOperandNum(levelDiff: Int) : Int = maxOperandPerLd*levelDiff

    companion object {
        // default relation of maximum operand per level difficulty
        private const val DEF_MAX_OPERAND_PER_LD = 5
        // default minimum of operands
        private const val DEF_MIN_OPERANDS = 2
        // default relation of number of operands per level difficulty
        private const val DEF_NUM_OPERANDS_PER_LD = 0.2f
        // default operand minimum number
        private const val DEF_OPERAND_MIN_NUM = 1
    }
}