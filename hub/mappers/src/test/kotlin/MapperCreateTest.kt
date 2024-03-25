import com.otus.otuskotlin.hub.api.v1.models.*
import kotlinx.datetime.Instant
import models.*
import stubs.HubStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperCreateTest {

    @Test
    fun fromTransport() {
        val createRequest = OfferCreateRequest(
            requestType = "create",
            debug = OfferDebug(
                mode = OfferRequestDebugMode.PROD,
                stub = OfferRequestDebugStubs.BAD_ID
            ),
            offer = OfferCreateObject(
                title = "Offer title",
                offerType = OfferDealSide.DEMAND,
                offeredCurrency = "offered currency",
                desiredCurrency = "desired currency",
                amount = "amount",
                rate = "rate",
                expectedAmount = "expected amount",
                location = "location",
                visibility = OfferVisibility.REGISTERED_ONLY
            )
        )

        val context = HubContext()
        context.fromTransport(createRequest)

        assertEquals(HubCommand.CREATE, context.command)
        assertEquals(HubState.NONE, context.state)
        assertEquals(mutableSetOf(), context.errors)

        assertEquals(HubWorkMode.PROD, context.workMode)
        assertEquals(HubStubs.BAD_ID, context.stubCase)

        assertEquals(HubRequestId.NONE, context.requestId)
        assertEquals(Instant.NONE, context.timeStart)

        assertEquals(HubOfferId.NONE, context.offerRequest.id)
        assertEquals("Offer title", context.offerRequest.title)
        assertEquals(HubDealSide.DEMAND, context.offerRequest.offerType)
        assertEquals(HubOfferCurrency("offered currency"), context.offerRequest.offeredCurrency)
        assertEquals(HubOfferCurrency("desired currency"), context.offerRequest.desiredCurrency)
        assertEquals(HubOfferAmount("amount"),  context.offerRequest.amount)
        assertEquals(HubOfferRate("rate"),  context.offerRequest.rate)
        assertEquals(HubOfferAmount("expected amount"),  context.offerRequest.expectedAmount)
        assertEquals(HubOfferLocation("location"),  context.offerRequest.location)
        assertEquals(HubVisibility.VISIBLE_TO_GROUP, context.offerRequest.visibility)

        assertEquals(HubOfferFilter(), context.searchFilter)

        assertEquals(HubOffer(), context.offerResponse)
        assertEquals(mutableSetOf(), context.offersResponse)
    }

    @Test
    fun toTransport() {
        val context = HubContext(
            command = HubCommand.CREATE,
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
                visibility = HubVisibility.VISIBLE_PUBLIC,
                permissionsClient = mutableSetOf(HubOfferPermissionClient.READ, HubOfferPermissionClient.UPDATE)
            ),
            offersResponse = mutableSetOf()
        )

        val createResponse = context.toTransport() as OfferCreateResponse

        assertEquals(ResponseResult.SUCCESS, createResponse.result)

        assertEquals(listOf(OfferError("error code", "error group", "error field", "error message")),
            createResponse.errors)

        assertEquals("Offer id", createResponse.offer?.offerId)
        assertEquals("Offer title", createResponse.offer?.title)
        assertEquals(OfferDealSide.DEMAND, createResponse.offer?.offerType)
        assertEquals("offered currency", createResponse.offer?.offeredCurrency)
        assertEquals("desired currency", createResponse.offer?.desiredCurrency)
        assertEquals("amount", createResponse.offer?.amount)
        assertEquals("rate", createResponse.offer?.rate)
        assertEquals("expected amount", createResponse.offer?.expectedAmount)
        assertEquals("location", createResponse.offer?.location)
        assertEquals("11", createResponse.offer?.ownerId)
        assertEquals("Unlocked", createResponse.offer?.lock)
        assertEquals(OfferVisibility.PUBLIC, createResponse.offer?.visibility)
        assertEquals(setOf(OfferPermissions.READ, OfferPermissions.UPDATE),
            createResponse.offer?.permissions)
    }
}