package com.sts.ssms.utils

import com.sts.ssms.data.common.Result

import java.io.IOException
import java.net.ConnectException

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (connectionException: ConnectException) {
        Result.Error(
            IOException(
                "Connection failed. Please check your connection and try again",
                connectionException
            )
        )
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Result.Error(IOException(errorMessage, e))
    }
}