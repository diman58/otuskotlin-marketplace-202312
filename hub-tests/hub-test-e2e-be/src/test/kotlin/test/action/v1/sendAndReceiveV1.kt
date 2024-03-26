package test.action.v1

import Client
import apiV1RequestSerialize
import apiV1ResponseDeserialize
import co.touchlab.kermit.Logger
import com.otus.otuskotlin.hub.api.v1.models.IRequest
import com.otus.otuskotlin.hub.api.v1.models.IResponse

private val log = Logger

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    test.action.v1.log.i { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive("v1", path, requestBody)
    test.action.v1.log.i { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}
