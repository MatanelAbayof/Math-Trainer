package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.utils.language.Language

class LevelsBuilder(ctx: Context)  : JSONComponentBuilder<Level>(ctx) {
    override val fileName: String = "levels"
    override val listJsonObjName: String = Level.listJsonObjName
    private var nextLevelId = 1 // helper variable for next level

    init {
        register(Language.ENGLISH, ({ buildJSON(buildLevelsEn()) }))
        register(Language.HEBREW, ({ buildJSON(buildLevelsHeb()) }))
    }


    // build levels in Hebrew
    private fun buildLevelsHeb() : List<Level> {
        val lang = Language.HEBREW
        val levels = mutableListOf<Level>()
        resetNextLevel()

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.ADDITION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "addition"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "חיבור היא פעולה המבוצעת על שני מספרים, ופירושה הוספה של אחד מהם לשני. הפעולה מסומנת בסימן +\n" +
                            "דוגמה לחיבור: 5 = 2 + 3"
                    level.tutorialLink = "https://he.wikibooks.org/wiki/%D7%97%D7%A9%D7%91%D7%95%D7%9F/%D7%97%D7%99%D7%91%D7%95%D7%A8"
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.SUBTRACTION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "subtraction"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MULTIPLICATION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "multiplication"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.DIVISION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "division"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MOD_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "mod"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.POWER_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "power"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "בפעולת החזקה, מכפילים מספר כלשהו בעצמו מספר מסוים של פעמים.<br>\n" +
                            "החזקה מסומנת בצורה הבאה: \\(5^3\\), אך לעתים נוהגים להשתמש בסימן ^.<br>\n" +
                            "דוגמה לחזקה:<br>\n" +
                            "\\( 5^3 = 5 \\times 5 \\times 5 = 125 \\)"
                    level.tutorialLink = "https://he.wikibooks.org/wiki/%D7%97%D7%A9%D7%91%D7%95%D7%9F/%D7%97%D7%96%D7%A7%D7%94"
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.SQRT_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "sqrt"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "לפעולת השורש קרבה גדולה לפעולת החזקה. זוהי פעולת השורש.\n" +
                            "השורש הנפוץ והשימושי ביותר הינו כמובן השורש הריבועי. שורש זה הוא הפעולה ההפוכה להעלאה בריבוע (חזקת 2).\n" +
                            "אילו העלינו מספר כלשהו, למשל 3 בריבוע, הפעולה שהייתה מחזירה את התוצאה חזרה ל-3 הינה פעולת השורש הריבועי.\n" +
                            "את פעולת השורש הריבועי של a מסמנים כך:\n" +
                            "\\( \\sqrt {a} \\)\n" +
                            "\n" +
                            "על מנת לנסות להמחיש את הפעולה לאשורה, ניקח למשל את המספר 25. ננסה למצוא שורש למספר זה. מכיוון שאנו כבר יודעים מראש ש-25 הוא מכפלה של 5 בעצמו, או במילים אחרות 5 בריבוע, הרי ששורש שלו הוא 5. כלומר\n" +
                            "\\( \\sqrt {25} = 5 \\)\n" +
                            "\n" +
                            "\n" +
                            "זאת מכיוון ש\n" +
                            "\\( (\\sqrt{25})^2 ={}5^2{}=25 \\)"
                    level.tutorialLink = "https://he.wikibooks.org/wiki/%D7%9E%D7%AA%D7%9E%D7%98%D7%99%D7%A7%D7%94_%D7%AA%D7%99%D7%9B%D7%95%D7%A0%D7%99%D7%AA/%D7%90%D7%9C%D7%92%D7%91%D7%A8%D7%94_%D7%AA%D7%99%D7%9B%D7%95%D7%A0%D7%99%D7%AA/%D7%97%D7%95%D7%A7%D7%99_%D7%94%D7%97%D7%A9%D7%91%D7%95%D7%9F/%D7%A9%D7%95%D7%A8%D7%A9%D7%99%D7%9D"
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MIX_ASM_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "add_sub_mult"
        }

       /*levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "${TopicsBuilder.TOPICS_IMG_PATH}/.png"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }*/

        return levels
    }

    // build levels in English
    private fun buildLevelsEn() : List<Level> {
        val lang = Language.ENGLISH
        val levels = mutableListOf<Level>()
        resetNextLevel()

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.ADDITION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "addition"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "Addition operations are denoted by the + sign. The addition operator (plus sign) will take any two numbers, called addends, as operands to work on. The result is called the sum of the two numbers.\n" +
                            "The operation of addition is commutative. This means that the addition of two numbers will give the same sum regardless of the order in which the numbers are added.\n" +
                            "Example: 3 + 2 = 5"
                    level.tutorialLink = "https://en.wikibooks.org/wiki/Arithmetic/Addition_and_Subtraction"

                }
            }

        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.SUBTRACTION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "subtraction"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MULTIPLICATION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "multiplication"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "Multiplication (often denoted by the cross symbol \"\\( \\times \\)\" or by the dot \"\\( \\cdot \\)\" is one of the four elementary mathematical operations of arithmetic, with the others being addition, subtraction and division.<br><br>\n" +
                            "The multiplication of whole numbers may be thought as a repeated addition; that is, the multiplication of two numbers is equivalent to adding as many copies of one of them, the multiplicand, as the value of the other one, the multiplier. The multiplier can be written first and multiplicand second; both can be called factors.<br><br>\n" +
                            "For example, 4 multiplied by 3 (often written as \\( 3\\times 4\\) and spoken as \"3 times 4\") can be calculated by adding 3 copies of 4 together:<br>\n" +
                            "\n" +
                            "\\( 3 \\times 4 = 4 + 4 + 4 = 12 \\)<br>\n" +
                            "Here 3 and 4 are the factors and 12 is the product."
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.DIVISION_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "division"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MOD_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "mod"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.POWER_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "power"
            when(level.difficulty) {
                1 -> {
                    level.tutorialTextInfo = "Power is the number of times a number is multiplied by itself. <br>" +
                            "\\( a^n = {} a \\times \\cdots \\times a \\) <br>" +
                            "a is raised to power of n is equal to a times a times a n times."
                    level.tutorialLink = "https://en.wikibooks.org/wiki/Arithmetic_Course/Number_Operation/Power"
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.SQRT_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "sqrt"
            when(level.difficulty) {
                1 -> {
                   /* TODO level.tutorialTextInfo = ""
                    level.tutorialLink = ""*/
                }
            }
        }

        levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.MIX_ASM_ID
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "add_sub_mult"
        }

        /*levels.appendLevels(lang, DEF_NUM_LEVELS_IN_TOPIC) { level ->
            level.topicId = QuestionsInfo.Categories.Arithmetic.Topics.
            level.minWinTime = DEF_MIN_WIN_TIME
            level.imagePath = "?"
            when(level.difficulty) {
                1 -> {
                    // TODO level.tutorialTextInfo = ""
                    // TODO level.tutorialLink = ""
                }
            }
        }*/

        return levels
    }

    // append pack of levels to list
    private fun MutableList<Level>.appendLevels(lang: Language, numOfLevels: Int = DEF_NUM_LEVELS_IN_TOPIC, onAddLevel: ((Level) -> Unit)? = null) {
        repeat(numOfLevels) { index ->
            appendLevel(lang,index+1, onAddLevel)
        }
    }

    // append new level to list
    private fun MutableList<Level>.appendLevel(lang: Language, numInTopic: Int? = null, onAddLevel: ((Level) -> Unit)? = null) {
        val level = Level()
        level.id = nextLevelId
        level.uniqueName = "level$nextLevelId"
        if(numInTopic != null) {
            level.displayName = when(lang) {
                Language.HEBREW -> "שלב $numInTopic"
                Language.ENGLISH -> "Level $numInTopic"
            }
            level.difficulty = numInTopic
        }
        level.xpPerStar = DEF_XP_PER_STAR

        nextLevelId++
        add(level)
        if (onAddLevel != null)
            onAddLevel(level)
    }

    // rest next level id
    private fun resetNextLevel() {
        nextLevelId = 1
    }

    companion object {
        // default number of levels in topic
        private const val DEF_NUM_LEVELS_IN_TOPIC = 10
        // default XP per star
        private const val DEF_XP_PER_STAR = 5
        // default min time to win
        private const val DEF_MIN_WIN_TIME = 15000L // = 15 seconds
        // levels
        //private const val LEVELS_IMG_PATH = "${AssetsManager.IMAGES_PATH}/levels"
    }
}