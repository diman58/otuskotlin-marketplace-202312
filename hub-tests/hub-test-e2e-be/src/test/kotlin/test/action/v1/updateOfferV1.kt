import com.otus.otuskotlin.hub.api.v1.models.OfferResponseObject
import com.otus.otuskotlin.hub.api.v1.models.OfferUpdateObject
import com.otus.otuskotlin.hub.api.v1.models.OfferUpdateRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferUpdateResponse
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import test.action.beValidOfferId
import test.action.beValidOfferLock
import test.action.v1.debug
import test.action.v1.haveSuccessResult
import test.action.v1.sendAndReceive

suspend fun Client.updateAd(offer: OfferUpdateObject): OfferResponseObject =
    updateOffer(offer) {
        it should haveSuccessResult
        it.offer shouldNotBe null
        it.offer?.apply {
            if (offer.title != null)
                title shouldBe offer.title
            if (offer.offerType != null)
                offerType shouldBe offer.offerType
            if (offer.visibility != null)
                visibility shouldBe offer.visibility
        }
        it.offer!!
    }

suspend fun <T> Client.updateOffer(offer: OfferUpdateObject, block: (OfferUpdateResponse) -> T): T {
    val id = offer.offerId
    val lock = offer.lock
    return withClue("updatedOfferV1: $id, lock: $lock, set: $offer") {
        id should beValidOfferId
        lock should beValidOfferLock

        val response = sendAndReceive(
            "ad/update", OfferUpdateRequest(
                debug = debug,
                offer = offer.copy(offerId = id, lock = lock)
            )
        ) as OfferUpdateResponse

        response.asClue(block)
    }
}
