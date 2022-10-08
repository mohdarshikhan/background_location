package com.mak.backgroundlocation.data.db

import java.text.DateFormat
import java.util.*

data class LocationEntity(
    var id: UUID = UUID.randomUUID(),
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var foreground: Boolean = true,
    var recordedAt: Date = Date()
) {
    override fun toString(): String {
        val appState = if (foreground) {
            "in app"
        } else {
            "in BG"
        }

        return "$latitude, $longitude $appState on " +
                "${DateFormat.getDateTimeInstance().format(recordedAt)}.\n"
    }
}