package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ersinberkealemdaroglu.arackaskodegerlistesi.InsureApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("products")

class DataStoreManager {

    private val vehicleDatastore: DataStore<Preferences> = InsureApplication.appContext.datastore

    companion object {
        val VEHICLE_DATA = stringPreferencesKey("VEHICLE_DATA")
        val IS_FIRST_OPEN = booleanPreferencesKey("IS_FIRST_OPEN")
        val LOW_PRICE_VEHICLE_DATA = stringPreferencesKey("LOW_PRICE_VEHICLE_DATA")
    }

    suspend fun storeVehicleData(
        vehicleData: String,
    ) {
        vehicleDatastore.edit { preferences ->
            preferences[VEHICLE_DATA] = vehicleData
        }
    }

    suspend fun storeLowPriceVehicleData(
        lowPriceVehicleData: String,
    ) {
        vehicleDatastore.edit { preferences ->
            preferences[LOW_PRICE_VEHICLE_DATA] = lowPriceVehicleData
        }
    }

    suspend fun readIsNeedDataRequest(): Boolean = vehicleDatastore.data.map { preferences ->
        preferences[IS_FIRST_OPEN] ?: true
    }.first()

    suspend fun updateIsNeedDataRequest(value: Boolean) {
        vehicleDatastore.edit { settings ->
            settings[IS_FIRST_OPEN] = value
        }
    }

    suspend fun readLowPriceVehicleData(): String = vehicleDatastore.data.map { preferences ->
        preferences[LOW_PRICE_VEHICLE_DATA] ?: ""
    }.first()

    suspend fun readVehicleData(): String = vehicleDatastore.data.map { preferences ->
        preferences[VEHICLE_DATA] ?: ""
    }.first()

    suspend fun clearDataStore() {
        vehicleDatastore.edit {
            it.clear()
        }
    }

}