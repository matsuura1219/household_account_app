package jp.matsuura.household_accountandroid.util

object Const {

    private const val SPENDING_TYPE: Int = 0
    private const val INCOME_TYPE: Int = 1

    val DEFAULT_SPENDING_CATEGORIES: List<String> = listOf(
        "買い物",
        "食べ物",
        "電話",
        "娯楽",
        "教育",
        "美容",
        "運動",
        "社交",
        "交通",
        "衣類",
        "車",
        "酒",
        "タバコ",
        "電化製品",
        "トラベル",
        "健康",
        "その他",
    )

    val DEFAULT_INCOME_CATEGORIES: List<String> = listOf(
        "給料",
        "投資",
        "副業",
        "賞与",
        "その他",
    )

}