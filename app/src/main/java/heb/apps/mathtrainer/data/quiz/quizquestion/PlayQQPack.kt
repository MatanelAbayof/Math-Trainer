package heb.apps.mathtrainer.data.quiz.quizquestion

import android.os.Parcel
import android.os.Parcelable
import heb.apps.mathtrainer.data.quiz.factory.QuizQuestionGen

class PlayQQPack(var numOfQuestions: Int = 0, var filter: QuizQuestionGen.Filter = QuizQuestionGen.Filter()) : Parcelable {

    constructor(input: Parcel) : this(
        input.readInt(),
        input.readParcelable(QuizQuestionGen.Filter::class.java.classLoader)!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(numOfQuestions)
        dest.writeParcelable(filter, 0)
    }

    override fun describeContents() = 0

    // convert to string
    override fun toString() = "PlayQQPack: { numOfQuestions=$numOfQuestions, $filter }"

    companion object CREATOR : Parcelable.Creator<PlayQQPack> {
        override fun createFromParcel(parcel: Parcel): PlayQQPack {
            return PlayQQPack(parcel)
        }

        override fun newArray(size: Int): Array<PlayQQPack?> {
            return arrayOfNulls(size)
        }
    }
}