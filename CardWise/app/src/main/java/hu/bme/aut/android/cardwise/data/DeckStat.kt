package hu.bme.aut.android.cardwise.data

data class DeckStat(val id: Long,
                    val neverCount: Int,
                    val under20: Int,
                    val under50above20: Int,
                    val under80above50: Int,
                    val above80: Int) {
}