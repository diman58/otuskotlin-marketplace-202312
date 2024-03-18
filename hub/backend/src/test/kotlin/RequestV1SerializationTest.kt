import com.otus.otuskotlin.hub.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = P2PExchangeOfferCreateRequest(
        debug = P2PExchangeOfferDebug(
            mode = P2PExchangeOfferRequestDebugMode.STUB,
            stub = P2PExchangeOfferRequestDebugStubs.BAD_TITLE
        ),
        p2PExchangeOffer = P2PExchangeOfferCreateObject(
            id = "p2PExchangeOffer id",
            title = "p2PExchangeOffer title",
            offerType = DealSide.DEMAND,
            offeredCurrency = "offered currency",
            desiredCurrency = "desired currency",
            amount = "amount",
            rate = "rate",
            expectedAmountInDesired = "expected amount",
            location = "location",
            visibility = P2PExchangeOfferVisibility.PUBLIC
        )

    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        assertContains(json, Regex("\"id\":\\s*\"p2PExchangeOffer id\""))
        assertContains(json, Regex("\"title\":\\s*\"p2PExchangeOffer title\""))
        assertContains(json, Regex("\"offerType\":\\s*\"demand\""))
        assertContains(json, Regex("\"offeredCurrency\":\\s*\"offered currency\""))
        assertContains(json, Regex("\"desiredCurrency\":\\s*\"desired currency\""))
        assertContains(json, Regex("\"amount\":\\s*\"amount\""))
        assertContains(json, Regex("\"rate\":\\s*\"rate\""))
        assertContains(json, Regex("\"expectedAmountInDesired\":\\s*\"expected amount\""))
        assertContains(json, Regex("\"location\":\\s*\"location\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as P2PExchangeOfferCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"P2PExchangeOffer": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, P2PExchangeOfferCreateRequest::class.java)

        assertEquals(null, obj.p2PExchangeOffer)
    }
}