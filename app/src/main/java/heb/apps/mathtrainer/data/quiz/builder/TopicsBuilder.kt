package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.data.quiz.factory.creators.QuestionsInfo
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.utils.language.Language

class TopicsBuilder(ctx: Context)  : JSONComponentBuilder<Topic>(ctx) {
    override val fileName: String = "topics"
    override val listJsonObjName: String = Topic.listJsonObjName

    init {
       register(Language.ENGLISH, ({ buildJSON(buildTopicsEn()) }))
        register(Language.HEBREW, ({ buildJSON(buildTopicsHeb()) }))
    }

    // build topics in Hebrew
    private fun buildTopicsHeb() : List<Topic> {
        val topics = mutableListOf<Topic>()

        topics.add(
            Topic(
            QuestionsInfo.Categories.Arithmetic.Topics.ADDITION_ID,
            QuestionsInfo.Categories.Arithmetic.ID,
            "addition", "חיבור",
                "באריתמטיקה, חיבור היא פעולה יסודית שמשמעותה צירוף של שני אוספי פריטים לאוסף הכולל את שניהם.\n" +
                        "את החיבור מסמנים בעזרת הסימן \\(+\\) (פלוס). למספרים שמחברים קוראים \"מחוברים\" ולתוצאה קוראים \"סכום\".\n" +
                        "למשל: \\(2 + 3 = 5\\)\n" +
                        "אם נצרף 3 צורות מלמעלה ו-2 צורות מלמטה, נקבל ביחד 5 צורות.\n" +
                        "לפעולה קוראים \"פלוס\" או \"ועוד\" לכן את הביטוי ניתן לקרוא כ\"שתים ועוד שלוש\" או \"שתים פלוס שלוש\".",
                "https://he.wikipedia.org/wiki/%D7%97%D7%99%D7%91%D7%95%D7%A8",
            "addition", topics.size)
        )

        topics.add(
            Topic(
            QuestionsInfo.Categories.Arithmetic.Topics.SUBTRACTION_ID,
            QuestionsInfo.Categories.Arithmetic.ID,
            "subtraction", "חיסור",
                "באריתמטיקה, חיסור היא פעולה בינארית ההפוכה לחיבור. הפעולה במשמעותה הבסיסית מציינת הפחתה. את החיסור מסמנים בסימן \"-\".\n" +
                        "את הפעולה קוראים \"מינוס\" או \"פחות\".\n" +
                        "את המספר שממנו מחסרים מכנים \"מחוסר\". המספר שאותו מחסרים נקרא \"מחסר\", והתוצאה נקראת \"הפרש\".\n" +
                        "למשל: \\(5-2=3\\): אם ניקח 5, ונמחוק, נישאר עם 3. בדוגמה זו, 5 הוא המחוסר, 2 הוא המחסר, ו 3 הוא ההפרש.",
            "https://he.wikipedia.org/wiki/%D7%97%D7%99%D7%A1%D7%95%D7%A8",
            "subtraction", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MULTIPLICATION_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "multiplication", "כפל",
                "כֶּפֶל הוא פעולה בין מספרים, ובאופן כללי יותר פעולה בינארית על מבנים אלגבריים כלליים.\n" +
                        "כפל הוא אחד מארבע פעולות החשבון (יחד עם חיבור, חיסור, וחילוק).\n" +
                        "כפל של מספרים טבעיים הוא למעשה פעולת חיבור חוזרת, למשל: 4 כפול 3 הוא הסכום \\(3+3+3+3=12\\),\n" +
                        "ובאופן כללי \"a כפול b\" הוא a פעמים b, כלומר b ועוד b ועוד b וכן הלאה, a פעמים או הסכום של a קבוצות שגודל כל אחת מהן הוא b.\n",
                "https://he.wikipedia.org/wiki/%D7%9B%D7%A4%D7%9C",
                "multiplication", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MIX_ASM_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "max_asm", "ערבוב",
                imagePath = "add_sub_mult",
                orderIndex = topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.DIVISION_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "division", "חילוק",
                "באריתמטיקה, חילוק היא פעולה בינארית ההפוכה לכפל.\n" +
                        "בפעולת חילוק נתונים שני מספרים. הראשון שאותו מחלקים, נקרא \"מחולק\". והשני ,שבו מחלקים את הראשון, נקרא \"מחלק\".\n" +
                        "המספר המתקבל כתוצאה מהחילוק נקרא \"מנה\".\n" +
                        "את החילוק מייצגים פעמים רבות כשבר.\n" +
                        "פעולת החילוק מסומנת בסימן \\(\\div\\). באופן מעשי לרוב נהוג לכתוב חילוק באמצעות שבר, כאשר המחולק הוא המונה והמחלק הוא המכנה.\n" +
                        "למשל: \\({\\tfrac{6}{2} = 3}\\)",
                "https://he.wikipedia.org/wiki/%D7%97%D7%99%D7%9C%D7%95%D7%A7",
                "division", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MOD_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "mod", "שארית", "שארית חילוק זה החלק הנותר מחילוק 2 מספרים שלמים.\n" +
                        "למשל: החלק השלם של \\(5 \\div 2 \\)  הוא 2 ולכן נותר 1 על מנת להשלים ל-5. כלומר: \\( 5 = 2 \\times 2 + 1 \\). מסקנה: השארית היא 1.",
                null,
                "mod", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.POWER_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "power", "חזקה",
                "במתמטיקה, חֶזְקָה (או העלאה בחזקה) היא פעולה, המתבצעת בין שני מספרים: ה\"בסיס\" וה\"מעריך\".\n" +
                        "חזקה מסמנים בסימון \\(a^b\\) כאשר \\(a\\) הוא הבסיס ו-\\(b\\) המעריך.\n" +
                        "המשמעות היא הכפלת \\(a\\) בעצמו \\(b\\) פעמים.",
                "https://he.wikipedia.org/wiki/%D7%97%D7%96%D7%A7%D7%94_(%D7%9E%D7%AA%D7%9E%D7%98%D7%99%D7%A7%D7%94)",
                "power", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.SQRT_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "sqrt", "שורש ריבועי",
                "שורש ריבועי של מספר a כלשהו הוא מספר, שאם מכפילים אותו בעצמו מקבלים את a.\n" +
                        "הפעולה החישובית של מציאת השורש הריבועי נקראת הוצאת שורש ריבועי.",
                "https://he.wikipedia.org/wiki/%D7%A9%D7%95%D7%A8%D7%A9_%D7%A8%D7%99%D7%91%D7%95%D7%A2%D7%99",
                "sqrt", topics.size)
        )

        /*
        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.,
                QuestionsInfo.Categories.Arithmetic.ID,
                "", "", "",
                "",
                "?", topics.size)
        )*/



        return topics
    }

    // build topics in Hebrew
    private fun buildTopicsEn() : List<Topic> {
        val topics = mutableListOf<Topic>()

        topics.add(
            Topic(
            QuestionsInfo.Categories.Arithmetic.Topics.ADDITION_ID,
            QuestionsInfo.Categories.Arithmetic.ID,
            "addition", "Addition", "Addition (often signified by the plus symbol \"+\") is one of the four basic operations of arithmetic.\n" +
                    "The addition of two whole numbers is the total amount of those values combined.", "https://en.wikipedia.org/wiki/Addition",
            "addition", topics.size)
        )

        topics.add(
            Topic(
            QuestionsInfo.Categories.Arithmetic.Topics.SUBTRACTION_ID,
            QuestionsInfo.Categories.Arithmetic.ID,
            "subtraction", "Subtraction",
            "Subtraction is an arithmetic operation that represents the operation of removing objects from a collection.\n" +
                    "The result of a subtraction is called a difference.\n" +
                    "Subtraction is signified by the minus sign (−).",
            "https://en.wikipedia.org/wiki/Subtraction",
            "subtraction", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MULTIPLICATION_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "multiplication", "Multiplication", "Multiplication (often denoted by the cross symbol \"×\", by the dot \"⋅\", by juxtaposition, or, on computers, by an asterisk \"*\") is one of the four elementary mathematical operations of arithmetic, with the others being addition, subtraction and division.",
                "https://en.wikipedia.org/wiki/Multiplication",
                "multiplication", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MIX_ASM_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "max_asm", "Mix",
                imagePath = "add_sub_mult",
                orderIndex = topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.DIVISION_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "division", "Division", "Division is one of the four basic operations of arithmetic, the ways that numbers are combined to make new numbers.\n" +
                        "The other operations are addition, subtraction, and multiplication",
                "https://en.wikipedia.org/wiki/Division_(mathematics)",
                "division", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.MOD_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "mod", "Remainder", "In arithmetic, the remainder is the integer \"left over\" after dividing one integer by another to produce an integer quotient (integer division).<br>\n" +
                        "In the division of 43 by 5 we have:<br>\n" +
                        "\\( 43 = 8 \\times 5 + 3 \\)<br>\n" +
                        "so 3 is the least positive remainder.",
                "https://en.wikipedia.org/wiki/Remainder",
                "mod", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.POWER_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "power", "Power",
                "The power (or exponent) of a number says how many times to use the number in a multiplication.\n" +
                        "It is written as a small number to the right and above the base number.",
                "https://en.wikipedia.org/wiki/Exponentiation",
                "power", topics.size)
        )

        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.SQRT_ID,
                QuestionsInfo.Categories.Arithmetic.ID,
                "sqrt", "Square root",
                "In mathematics, a square root of a number \\(x\\) is a number \\(y\\) such that \\(y^2 = x\\).<br>\n" +
                        "in other words, a number \\(y\\) whose square is \\(x\\).",
                "https://en.wikipedia.org/wiki/Square_root",
                "sqrt", topics.size)
        )

        /*
        topics.add(
            Topic(
                QuestionsInfo.Categories.Arithmetic.Topics.,
                QuestionsInfo.Categories.Arithmetic.ID,
                "", "", "",
                "",
                "$TOPICS_IMG_PATH/??.png", topics.size)
        )*/

        return topics
    }
}