package test.action.v1

import Client
import com.otus.otuskotlin.hub.api.v1.models.OfferCreateObject
import com.otus.otuskotlin.hub.api.v1.models.OfferCreateRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferCreateResponse
import com.otus.otuskotlin.hub.api.v1.models.OfferResponseObject
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

suspend fun Client.createOffer(offer: OfferCreateObject = someCreateOffer): OfferResponseObject = createOffer(offer) {
    it should haveSuccessResult
    it.offer shouldNotBe null
    it.offer?.apply {
        title shouldBe offer.title
        offerType shouldBe offer.offerType
        offeredCurrency shouldBe offer.offeredCurrency
        desiredCurrency shouldBe offer.desiredCurrency
        amount shouldBe offer.amount
        rate shouldBe offer.rate
        expectedAmount shouldBe offer.expectedAmount
        location shouldBe offer.location
        visibility shouldBe offer.visibility
    }
    it.offer!!
}

suspend fun <T> Client.createOffer(offer: OfferCreateObject = someCreateOffer, block: (OfferCreateResponse) -> T): T =
    withClue("createOfferV1: $offer") {
        val response = sendAndReceive(
            "offer/create", OfferCreateRequest(
                requestType = "create",
                debug = debug,
                offer = offer
            )
        ) as OfferCreateResponse

        response.asClue(block)
    }
