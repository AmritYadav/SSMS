package com.sts.ssms.data.common

import com.sts.ssms.data.login.repository.AuthTokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

/**
 * An {@see Interceptor} that adds an AccessToken to the request if provided
 * , else proceeds as normal request
 */
class ClientAuthInterceptor(private val authTokenLocalDataSource: AuthTokenLocalDataSource) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (!authTokenLocalDataSource.authToken.isNullOrEmpty())
            requestBuilder.addHeader("access-token", authTokenLocalDataSource.authToken!!)
        return chain.proceed(requestBuilder.build())
    }
}