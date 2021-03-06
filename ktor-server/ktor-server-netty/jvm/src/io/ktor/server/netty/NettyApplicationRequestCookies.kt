/*
 * Copyright 2014-2019 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.server.netty

import io.ktor.request.*
import io.netty.handler.codec.http.cookie.*
import java.util.*

internal class NettyApplicationRequestCookies(request: ApplicationRequest) : RequestCookies(request) {
    override fun fetchCookies(): Map<String, String> {
        val cookieHeaders = request.headers.getAll("Cookie") ?: return emptyMap()
        val map = HashMap<String, String>(cookieHeaders.size)
        for (cookieHeader in cookieHeaders) {
            val cookies = ServerCookieDecoder.LAX.decode(cookieHeader).associateBy({ it.name() }, { it.value() })
            map.putAll(cookies)
        }
        return map
    }
}
