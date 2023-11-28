package hu.bme.aut.android.cardwise.data

/**
 * A class for defining the stats of the user in terms of all practiced questions and successfully
 * practiced ones.
 */
class UserStatsData(val userId: Long, val total: Long, val success: Long) {
}