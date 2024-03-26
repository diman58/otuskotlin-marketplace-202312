package test.action.v1

import Client
import com.otus.otuskotlin.hub.api.v1.models.OfferDeleteObject
import com.otus.otuskotlin.hub.api.v1.models.OfferDeleteRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferDeleteResponse
import com.otus.otuskotlin.hub.api.v1.models.OfferResponseObject
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import test.action.beValidOfferId
import test.action.beValidOfferLock

suspend fun Client.deleteOffer(offer: OfferResponseObject) {
    val id = offer.offerId
    val lock = offer.lock
    withClue("deleteOfferV1: $id, lock: $lock") {
        id should beValidOfferId
        lock should beValidOfferLock

        val response = sendAndReceive(
            "offer/delete",
            OfferDeleteRequest(
                debug = debug,
                offer = OfferDeleteObject(offerId = id, lock = lock)
            )
        ) as OfferDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.offer shouldBe offer
            response.offer?.offerId shouldBe offer
        }
    }
}
