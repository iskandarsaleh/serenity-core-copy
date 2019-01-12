package net.serenitybdd.reports.model

import net.thucydides.core.model.TestOutcome
import java.time.Duration

fun maxDurationOf(outcomes: List<TestOutcome>): Duration = Duration.ofMillis(
        if (outcomes.isEmpty()) 0 else outcomes.map { outcome -> outcome.duration }.max()!!
)

fun averageDurationOf(outcomes: List<TestOutcome>): Duration = Duration.ofMillis(
        if (outcomes.isEmpty()) 0 else outcomes.map { outcome -> outcome.duration }.average().toLong()
)

fun formattedDuration(duration: Duration): String {

    val days = duration.toDays()
    val hours = duration.toHours() - (days * 24)
    val minutes = duration.toMinutes() - (days * 24 * 60) - (hours * 60)
    val seconds = duration.seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - minutes * 60
    val durationInMilliseconds = "" + duration.toMillis() + "ms"

    val durationForSlowTests =
            (if (days > 0) "" + days + "d " else "") +
            (if (hours > 0) " " + hours + "h " else "") +
            (if (minutes > 0) " " + minutes + "m " else "") +
            (if (seconds > 0) " " + seconds + "s " else "0s ").trim()

    return if (duration.toMillis() < 1000L) durationInMilliseconds else durationForSlowTests
}
