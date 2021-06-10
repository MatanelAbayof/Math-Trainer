package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.utils.language.Language

class CategoriesBuilder(ctx: Context)  : JSONComponentBuilder<Category>(ctx) {
    override val fileName: String = "categories"
    override val listJsonObjName: String = Category.listJsonObjName

    init {
        register(Language.ENGLISH, ({ buildJSON(buildCategoriesEn()) }))
        register(Language.HEBREW, ({ buildJSON(buildCategoriesHeb()) }))
    }

    // build categories in Hebrew
    private fun buildCategoriesHeb() : List<Category> {
        val categories = mutableListOf<Category>()

        categories.add(
            Category(
                QuestionsInfo.Categories.Arithmetic.ID, "arithmetic", "אריתמטיקה",
                "אריתמטיקה, הידועה גם בשם חשבון, היא הענף העתיק והבסיסי ביותר במתמטיקה. זהו החלק היסודי בלימוד המתמטיקה, הוא משתלב ביתר ענפי המתמטיקה וחיוני להבנתם. עקרונות האריתמטיקה הבסיסיים, המבוססים על ארבע פעולות החשבון וסדר בין מספרים.",
                "https://he.wikipedia.org/wiki/%D7%90%D7%A8%D7%99%D7%AA%D7%9E%D7%98%D7%99%D7%A7%D7%94", "arithmetic", categories.size)
        )

        /*categories.add(
            Category(2, "set_theory", "תורת הקבוצות",
                "תורת הקבוצות היא תורה מתמטית בסיסית העוסקת במושג הקבוצה, שהיא אוסף מופשט של איברים שונים זה מזה.",
                "https://he.wikipedia.org/wiki/%D7%AA%D7%95%D7%A8%D7%AA_%D7%94%D7%A7%D7%91%D7%95%D7%A6%D7%95%D7%AA", null, categories.size)
        )*/

        // TODO add more categories here

        return categories
    }

    // build categories in English
    private fun buildCategoriesEn() : List<Category> {
        val categories = mutableListOf<Category>()

        categories.add(
            Category(1, "arithmetic", "Arithmetic",
                "Traditional operations: addition, subtraction, multiplication and division",
                "https://en.wikipedia.org/wiki/Arithmetic", "arithmetic",categories.size)
        )

        /*categories.add(
            Category(2, "set_theory", "Set theory",
                "Set theory is a branch of mathematical logic that studies sets, which informally are collections of objects",
                "https://en.wikipedia.org/wiki/Set_theory", null, categories.size)
        )*/

        // TODO add more categories here

        return categories
    }
}