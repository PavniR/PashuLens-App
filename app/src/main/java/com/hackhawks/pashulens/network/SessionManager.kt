package com.hackhawks.pashulens.network

// A simple singleton object to hold the current user's session token
object SessionManager {
    var authToken: String? = null
}