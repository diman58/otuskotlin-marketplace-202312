import com.otus.otuskotlin.hub.api.v1.models.OfferDealSide
import com.otus.otuskotlin.hub.api.v1.models.OfferSearchFilter
import com.otus.otuskotlin.hub.api.v1.models.OfferUpdateObject
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import test.action.v1.*

fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {
        test("Create Ad ok") {
            client.createOffer()
        }

        test("Read Ad ok") {
            val created = client.createOffer()
            client.readOffer(created.offerId).asClue {
                it shouldBe created
            }
        }

        test("Update Ad ok") {
            val created = client.createOffer()
            val updateAd = OfferUpdateObject(
                offerId = created.offerId,
                lock = created.lock,
                title = "Selling CNY",
                offerType = created.offerType,
                visibility = created.visibility,
            )
            client.updateAd(updateAd)
        }

        test("Delete Ad ok") {
            val created = client.createOffer()
            client.deleteOffer(created)
//            client.readAd(created.id) {
//                 it should haveError("not-found")
//            }
        }

        test("Search Ad ok") {
            val created1 = client.createOffer(someCreateOffer.copy(title = "Selling Bolt"))
            val created2 = client.createOffer(someCreateOffer.copy(title = "Selling Nut"))

            withClue("Search Selling") {
                val results = client.searchOffer(search = OfferSearchFilter(searchString = "Selling"))
                results shouldHaveSize 2
                results shouldExist { it.title == created1.title }
                results shouldExist { it.title == created2.title }
            }

            withClue("Search Bolt") {
                client.searchOffer(search = OfferSearchFilter(searchString = "Bolt"))
                    .shouldExistInOrder({ it.title == created1.title })
            }
        }

        test("Offer Ad ok") {
            val supply = client.createOffer(someCreateOffer.copy(title = "Some Bolt", offerType = OfferDealSide.SUPPLY))
            val demand = client.createOffer(someCreateOffer.copy(title = "Some Bolt", offerType = OfferDealSide.DEMAND))

            withClue("Find offer for supply") {
                val res1 = client.offersFeed(supply.offerId)
                /*res1.offer?.o shouldBe supply.adType
                res1.ads?.shouldExistInOrder({ it.adType == demand.adType }) ?: fail("Empty ads")*/
            }
        }
    }

}
