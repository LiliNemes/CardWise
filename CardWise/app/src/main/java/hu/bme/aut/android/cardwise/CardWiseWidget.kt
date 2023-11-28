package hu.bme.aut.android.cardwise

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import hu.bme.aut.android.cardwise.data.UserDataRepository
import java.sql.Date
import java.time.LocalDate
import kotlin.random.Random

/**
 * Implementation of App Widget functionality.
 */
class CardWiseWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        /*
        userDataRepository = UserDataRepository(context, userID)
        var usage = userDataRepository.getDailyUsage()
        var day = LocalDate.now()
        var stop = false
        var streak = 0
        while (!stop) {
            if (usage.contains(day)) {
                streak++
                day = day.minusDays(1)
            }
            else
                stop = true
        }
        */
        val streak = 1
        val user = "you_ser"

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, streak, user)
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    streak: Int,
    user: String
) {
    val widgetText = "Best streak: \n" +
            "$user: $streak"
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.card_wise_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}