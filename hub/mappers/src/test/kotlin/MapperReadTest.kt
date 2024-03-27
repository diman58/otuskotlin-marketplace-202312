import com.otus.otuskotlin.hub.api.v1.models.*
import kotlinx.datetime.Instant
import models.*
import stubs.HubStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperReadTest {

    @Test
    fun fromTransport() {
        val readRequest = OfferReadRequest(
            requestType = "read",
            debug = OfferDebug(
                mode = OfferRequestDebugMode.TEST,
                stub = OfferRequestDebugStubs.NOT_FOUND
            ),
            offer = OfferReadObject(
                offerId = "123"
            )
        )

        val context = HubContext()
        context.fromTransport(readRequest)

        assertEquals(HubCommand.READ, context.command)
        assertEquals(HubState.NONE, context.state)
        assertEquals(mutableSetOf(), context.errors)

        assertEquals(HubWorkMode.TEST, context.workMode)
        assertEquals(HubStubs.NOT_FOUND, context.stubCase)

        assertEquals(HubRequestId.NONE, context.requestId)
        assertEquals(Instant.NONE, context.timeStart)

        assertEquals(HubOfferId("123"), context.offerRequest.id)
        assertEquals("", context.offerRequest.title)
        assertEquals(HubDealSide.NONE, context.offerRequest.offerType)
        assertEquals(HubOfferCurrency.NONE, context.offerRequest.offeredCurrency)
        assertEquals(HubOfferCurrency.NONE, context.offerRequest.desiredCurrency)
        assertEquals(HubOfferAmount.NONE, context.offerRequest.amount)
        assertEquals(HubOfferRate.NONE, context.offerRequest.rate)
        assertEquals(HubOfferAmount.NONE, context.offerRequest.expectedAmount)
        assertEquals(HubOfferLocation.NONE, context.offerRequest.location)
        assertEquals(HubVisibility.NONE, context.offerRequest.visibility)

        assertEquals(HubOfferFilter(), context.searchFilter)

        assertEquals(HubOffer(), context.offerResponse)
        assertEquals(mutableSetOf(), context.offersResponse)
    }

    @Test
    fun toTransport() {
        val context = HubContext(
            command = HubCommand.READ,
            state = HubState.FINISHING,
            errors = mutableSetOf(
                HubError(
                    code = "error code",
                    group = "error group",
                    field = "error field",
                    message = "error message",
                )
            ),
            workMode = HubWorkMode.PROD,
            stubCase = HubStubs.SUCCESS,
            requestId = HubRequestId("1"),
            timeStart = Instant.NONE,
            offerRequest = HubOffer(),
            searchFilter = HubOfferFilter(),
            offerResponse = HubOffer(
                id = HubOfferId("Offer id"),
                title = "Offer title",
                offerType = HubDealSide.DEMAND,
                offeredCurrency = HubOfferCurrency("offered currency"),
                desiredCurrency = HubOfferCurrency("desired currency"),
                amount = HubOfferAmount("amount"),
                rate = HubOfferRate("rate"),
                expectedAmount = HubOfferAmount("expected amount"),
                location = HubOfferLocation("location"),
                ownerId = HubOfferUserId("11"),
                lock = HubOfferLock("Unlocked"),
                visibility = HubVisibility.VISIBLE_TO_OWNER,
                permissionsClient = mutableSetOf(HubOfferPermissionClient.DELETE, HubOfferPermissionClient.MAKE_VISIBLE_GROUP)
            ),
            offersResponse = mutableSetOf()
        )

        val readResponse = context.toTransport() as OfferReadResponse

        assertEquals(ResponseResult.SUCCESS, readResponse.result)

        assertEquals(listOf(OfferError("error code", "error group", "error field", "error message")),
            readResponse.errors)

        assertEquals("Offer id", readResponse.offer?.offerId)
        assertEquals("Offer title", readResponse.offer?.title)
        assertEquals(OfferDealSide.DEMAND, readResponse.offer?.offerType)
        assertEquals("offered currency", readResponse.offer?.offeredCurrency)
        assertEquals("desired currency", readResponse.offer?.desiredCurrency)
        assertEquals("amount", readResponse.offer?.amount)
        assertEquals("rate", readResponse.offer?.rate)
        assertEquals("expected amount", readResponse.offer?.expectedAmount)
        assertEquals("location", readResponse.offer?.location)
        assertEquals("11", readResponse.offer?.ownerId)
        assertEquals("Unlocked", readResponse.offer?.lock)
        assertEquals(OfferVisibility.OWNER_ONLY, readResponse.offer?.visibility)
        assertEquals(setOf(OfferPermissions.DELETE, OfferPermissions.MAKE_VISIBLE_GROUP),
            readResponse.offer?.permissions)
    }
}