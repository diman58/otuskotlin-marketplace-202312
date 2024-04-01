package test.action.v1

import Client
import com.otus.otuskotlin.hub.api.v1.models.OfferReadObject
import com.otus.otuskotlin.hub.api.v1.models.OfferReadRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferReadResponse
import com.otus.otuskotlin.hub.api.v1.models.OfferResponseObject
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import test.action.beValidOfferId

suspend fun Client.readOffer(id: String?): OfferResponseObject = readOffer(id) {
    it should haveSuccessResult
    it.offer shouldNotBe null
    it.offer!!
}

suspend fun <T> Client.readOffer(id: String?, block: (OfferReadResponse) -> T): T =
    withClue("readOfferV1: $id") {
        id should beValidOfferId

        val response = sendAndReceive(
            "offer/read",
            OfferReadRequest(
                requestType = "read",
                debug = debug,
                offer = OfferReadObject(offerId = id)
            )
        ) as OfferReadResponse

        response.asClue(block)
    }
