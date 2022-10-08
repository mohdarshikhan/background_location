package com.mak.backgroundlocation.data

import com.mak.backgroundlocation.data.db.LocationEntity
import com.mak.backgroundlocation.utils.BackgroundLocationManager
import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mak.backgroundlocation.R
import me.ibrahimsn.library.LiveSharedPreferences
import java.util.*
import java.util.concurrent.ExecutorService

class LocationRepository private constructor(
    private val locationManager: BackgroundLocationManager,
) {

    var locationsData: MutableLiveData<List<LocationEntity>> = MutableLiveData()

    /**
     * Returns all recorded locations from database.
     */
    fun getLocations(): LiveData<List<LocationEntity>> = locationsData

    /**
     * Adds list of locations to the database.
     */
    fun addLocations(myLocationEntities: List<LocationEntity>) {
//        executor.execute {
//            locationDao.addLocations(myLocationEntities)
//        }
        locationsData.value = myLocationEntities
    }

    // Location related fields/methods:
    /**
     * Status of whether the app is actively subscribed to location changes.
     */
    val receivingLocationUpdates: LiveData<Boolean> = locationManager.receivingLocationUpdates

    /**
     * Subscribes to location updates.
     */
    @MainThread
    fun startLocationUpdates() = locationManager.startLocationUpdates()

    /**
     * Un-subscribes from location updates.
     */
    @MainThread
    fun stopLocationUpdates() = locationManager.stopLocationUpdates()

    @MainThread
    fun requestLocationServices(activity: Activity, resolutionForResult: ActivityResultLauncher<IntentSenderRequest>) = locationManager.requestLocationServices(activity, resolutionForResult)

    companion object {
        @Volatile private var INSTANCE: LocationRepository? = null

        fun getInstance(context: Context, executor: ExecutorService): LocationRepository {
            val preferences = context.getSharedPreferences(context.getString(R.string.pref_key), Context.MODE_PRIVATE)
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepository(
                    BackgroundLocationManager.getInstance(context))
                    .also { INSTANCE = it }
            }
        }
    }
}