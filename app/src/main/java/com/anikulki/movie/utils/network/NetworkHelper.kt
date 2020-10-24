package com.anikulki.movie.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anikulki.movie.utils.common.Constants.TAG
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import javax.inject.Singleton

@Singleton
class NetworkHelper constructor(private val context: Context): ConnectivityManager.NetworkCallback(){

    private val networkAvailability: MutableLiveData<Boolean> = MutableLiveData()

    private var isConnected = false

    fun isNetworkConnected(): LiveData<Boolean>{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        connectivityManager.allNetworks.forEach {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(it)
            networkCapabilities?.let {networkCapability ->
                if(networkCapability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isConnected = true
                    return@forEach
                }
            }
        }

        networkAvailability.postValue(isConnected)

        return networkAvailability
    }


    fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, 0)
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response()?.errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Log.e(TAG, e.toString())
        }
        return defaultNetworkError
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkAvailability.postValue(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        networkAvailability.postValue(false)
    }
}