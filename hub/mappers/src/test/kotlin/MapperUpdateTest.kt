import com.otus.otuskotlin.hub.api.v1.models.*
import kotlinx.datetime.Instant
import models.*
import stubs.HubStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperUpdateTest {

    @Test
    fun fromTransport() {
        val updateRequest = OfferUpdateRequest(
            requestType = "update",
            debug = OfferDebug(
                mode = OfferRequestDebugMode.TEST,
                stub = OfferRequestDebugStubs.BAD_SEARCH_STRING
            ),
            offer = OfferUpdateObject(
                title = "Offer title",
                offerType = OfferDealSide.SUPPLY,
                offeredCurrency = "offered currency",
                desiredCurrency = "desired currency",
                amount = "amount",
                rate = "rate",
                expectedAmount = "expected amount",
                location = "location",
                visibility = OfferVisibility.REGISTERED_ONLY,
                offerId = "offer id",
                lock = "lock"
            )
        )

        val context = HubContext()
        context.fromTransport(updateRequest)

        assertEquals(HubCommand.UPDATE, context.command)
        assertEquals(HubState.NONE, context.state)
        assertEquals(mutableSetOf(), context.errors)

        assertEquals(HubWorkMode.TEST, context.workMode)
        assertEquals(HubStubs.BAD_SEARCH_STRING, context.stubCase)

        assertEquals(HubRequestId.NONE, context.requestId)
        assertEquals(Instant.NONE, context.timeStart)

        assertEquals(HubOfferId("offer id"), context.offerRequest.id)
        assertEquals("Offer title", context.offerRequest.title)
        assertEquals(HubDealSide.SUPPLY, context.offerRequest.offerType)
        assertEquals(HubOfferCurrency("offered currency"), context.offerRequest.offeredCurrency)
        assertEquals(HubOfferCurrency("desired currency"), context.offerRequest.desiredCurrency)
        assertEquals(HubOfferAmount("amount"),  context.offerRequest.amount)
        assertEquals(HubOfferRate("rate"),  context.offerRequest.rate)
        assertEquals(HubOfferAmount("expected amount"),  context.offerRequest.expectedAmount)
        assertEquals(HubOfferLocation("location"),  context.offerRequest.location)
        assertEquals(HubOfferLock("lock"), context.offerRequest.lock)
        assertEquals(HubVisibility.VISIBLE_TO_GROUP, context.offerRequest.visibility)

        assertEquals(HubOfferFilter(), context.searchFilter)

        assertEquals(HubOffer(), context.offerResponse)
        assertEquals(mutableSetOf(), context.offersResponse)
    }

    @Test
    fun toTransport() {
        val context = HubContext(
            command = HubCommand.UPDATE,
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

        val readResponse = context.toTransport() as OfferUpdateResponse

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