import com.otus.otuskotlin.hub.api.v1.models.*
import kotlinx.datetime.Instant
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
                offeredCurrency = "USD",
                desiredCurrency = "CNY",
                amount = "10000",
                rate = "7",
                expectedAmount = "70000",
                location = "Turkey",
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
        assertEquals(HubOfferCurrency("USD"), context.offerRequest.offeredCurrency)
        assertEquals(HubOfferCurrency("CNY"), context.offerRequest.desiredCurrency)
        assertEquals(HubOfferAmount("10000"), context.offerRequest.amount)
        assertEquals(HubOfferRate("7"), context.offerRequest.rate)
        assertEquals(HubOfferAmount("70000"), context.offerRequest.expectedAmount)
        assertEquals(HubOfferLocation("Turkey"), context.offerRequest.location)
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
                id = HubOfferId("144"),
                title = "Offer title",
                offerType = HubDealSide.DEMAND,
                offeredCurrency = HubOfferCurrency("USD"),
                desiredCurrency = HubOfferCurrency("CNY"),
                amount = HubOfferAmount("10000"),
                rate = HubOfferRate("7"),
                expectedAmount = HubOfferAmount("70000"),
                location = HubOfferLocation("Turkey"),
                ownerId = HubOfferUserId("11"),
                lock = HubOfferLock("Unlocked"),
                visibility = HubVisibility.VISIBLE_PUBLIC,
                permissionsClient = mutableSetOf(
                    HubOfferPermissionClient.READ,
                    HubOfferPermissionClient.UPDATE
                )
            ),
            offersResponse = mutableSetOf()
        )

        val createResponse = context.toTransport() as OfferCreateResponse

        assertEquals(ResponseResult.SUCCESS, createResponse.result)

        assertEquals(
            listOf(OfferError("error code", "error group", "error field", "error message")),
            createResponse.errors
        )

        assertEquals("144", createResponse.offer?.offerId)
        assertEquals("Offer title", createResponse.offer?.title)
        assertEquals(OfferDealSide.DEMAND, createResponse.offer?.offerType)
        assertEquals("USD", createResponse.offer?.offeredCurrency)
        assertEquals("CNY", createResponse.offer?.desiredCurrency)
        assertEquals("10000", createResponse.offer?.amount)
        assertEquals("7", createResponse.offer?.rate)
        assertEquals("70000", createResponse.offer?.expectedAmount)
        assertEquals("Turkey", createResponse.offer?.location)
        assertEquals("11", createResponse.offer?.ownerId)
        assertEquals("Unlocked", createResponse.offer?.lock)
        assertEquals(OfferVisibility.PUBLIC, createResponse.offer?.visibility)
        assertEquals(
            setOf(OfferPermissions.READ, OfferPermissions.UPDATE),
            createResponse.offer?.permissions
        )
    }
}