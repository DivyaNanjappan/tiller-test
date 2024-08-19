package com.tillertest.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return NETWORK_ERROR
    }

    override val message: String?
        get() = NETWORK_ERROR

    companion object {
        const val NETWORK_ERROR = "Network Error";
    }
}
