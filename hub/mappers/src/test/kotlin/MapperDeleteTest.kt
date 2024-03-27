import com.otus.otuskotlin.hub.api.v1.models.*
import kotlinx.datetime.Instant
import models.*
import stubs.HubStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperDeleteTest {

    @Test
    fun fromTransport() {
        val deleteRequest = OfferDeleteRequest(
            requestType = "delete",
            debug = OfferDebug(
                mode = OfferRequestDebugMode.PROD,
                stub = OfferRequestDebugStubs.CANNOT_DELETE
            ),
            offer = OfferDeleteObject(
                offerId = "14221",
                lock = "lock"
            )
        )

        val context = HubContext()
        context.fromTransport(deleteRequest)

        assertEquals(HubCommand.DELETE, context.command)
        assertEquals(HubState.NONE, context.state)
        assertEquals(mutableSetOf(), context.errors)

        assertEquals(HubWorkMode.PROD, context.workMode)
        assertEquals(HubStubs.CANNOT_DELETE, context.stubCase)

        assertEquals(HubRequestId.NONE, context.requestId)
        assertEquals(Instant.NONE, context.timeStart)

        assertEquals(HubOfferId("14221"), context.offerRequest.id)
        assertEquals("", context.offerRequest.title)
        assertEquals(HubDealSide.NONE, context.offerRequest.offerType)
        assertEquals(HubOfferCurrency.NONE, context.offerRequest.offeredCurrency)
        assertEquals(HubOfferCurrency.NONE, context.offerRequest.desiredCurrency)
        assertEquals(HubOfferAmount.NONE, context.offerRequest.amount)
        assertEquals(HubOfferRate.NONE, context.offerRequest.rate)
        assertEquals(HubOfferAmount.NONE, context.offerRequest.expectedAmount)
        assertEquals(HubOfferLocation.NONE, context.offerRequest.location)
        assertEquals(HubOfferLock("lock"), context.offerRequest.lock)
        assertEquals(HubVisibility.NONE, context.offerRequest.visibility)

        assertEquals(HubOfferFilter(), context.searchFilter)

        assertEquals(HubOffer(), context.offerResponse)
        assertEquals(mutableSetOf(), context.offersResponse)
    }

    @Test
    fun toTransport() {
        val context = HubContext(
            command = HubCommand.DELETE,
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
                visibility = HubVisibility.VISIBLE_TO_OWNER,
                permissionsClient = mutableSetOf(
                    HubOfferPermissionClient.DELETE,
                    HubOfferPermissionClient.MAKE_VISIBLE_GROUP
                )
            ),
            offersResponse = mutableSetOf()
        )

        val deleteResponse = context.toTransport() as OfferDeleteResponse

        assertEquals(ResponseResult.SUCCESS, deleteResponse.result)

        assertEquals(
            listOf(OfferError("error code", "error group", "error field", "error message")),
            deleteResponse.errors
        )

        assertEquals("144", deleteResponse.offer?.offerId)
        assertEquals("Offer title", deleteResponse.offer?.title)
        assertEquals(OfferDealSide.DEMAND, deleteResponse.offer?.offerType)
        assertEquals("USD", deleteResponse.offer?.offeredCurrency)
        assertEquals("CNY", deleteResponse.offer?.desiredCurrency)
        assertEquals("10000", deleteResponse.offer?.amount)
        assertEquals("7", deleteResponse.offer?.rate)
        assertEquals("70000", deleteResponse.offer?.expectedAmount)
        assertEquals("Turkey", deleteResponse.offer?.location)
        assertEquals("11", deleteResponse.offer?.ownerId)
        assertEquals("Unlocked", deleteResponse.offer?.lock)
        assertEquals(OfferVisibility.OWNER_ONLY, deleteResponse.offer?.visibility)
        assertEquals(
            setOf(OfferPermissions.DELETE, OfferPermissions.MAKE_VISIBLE_GROUP),
            deleteResponse.offer?.permissions
        )
    }
}
