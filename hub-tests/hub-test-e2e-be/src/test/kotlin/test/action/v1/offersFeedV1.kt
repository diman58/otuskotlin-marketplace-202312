package test.action.v1

import Client
import com.otus.otuskotlin.hub.api.v1.models.OfferFeedRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferFeedResponse
import com.otus.otuskotlin.hub.api.v1.models.OfferReadObject
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
suspend fun Client.offersFeed(id: String?): OfferFeedResponse = offersFeed(id) {
    it should test.action.v1.haveSuccessResult
    it
}

suspend fun <T> Client.offersFeed(id: String?, block: (OfferFeedResponse) -> T): T =
    withClue("searchOfferV1: $id") {
        val response = sendAndReceive(
            "ad/offers",
            OfferFeedRequest(
                debug = debug,
                offer = OfferReadObject(offerId = id),
            )
        ) as OfferFeedResponse

        response.asClue(block)
    }
