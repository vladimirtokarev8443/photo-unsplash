package com.example.data.repository

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE

    const val CLIENT_ID = "I3kftaNQJGygEY5kYVCRB3RCIOxf1ZXKhmS6r1LV18A"
    const val CLIENT_SECRET = "BrX7ThCTUu8GVHAkGRntDT0oARn_yRV5n94rHyUfYfU"
    const val CALLBACK_URL = "inspiration://com.example.inspiration/callback"


    const val SCOPE_PUBLIC = "public "+
            "read_user "+
            "write_user "+
            "read_photos "+
            "write_photos "+
            "write_likes "+
            "write_followers "+
            "read_collections "+
            "write_collections"
}