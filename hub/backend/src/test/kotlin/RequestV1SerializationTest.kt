import com.otus.otuskotlin.hub.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = OfferCreateRequest(
        requestType = "create",
        debug = OfferDebug(
            mode = OfferRequestDebugMode.STUB,
            stub = OfferRequestDebugStubs.BAD_TITLE
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
            visibility = OfferVisibility.PUBLIC
        )

    ).also { println(it) }

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"requestType\":\\s*\"create\""))

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))

        assertContains(json, Regex("\"title\":\\s*\"Offer title\""))
        assertContains(json, Regex("\"offerType\":\\s*\"demand\""))
        assertContains(json, Regex("\"offeredCurrency\":\\s*\"offered currency\""))
        assertContains(json, Regex("\"desiredCurrency\":\\s*\"desired currency\""))
        assertContains(json, Regex("\"amount\":\\s*\"amount\""))
        assertContains(json, Regex("\"rate\":\\s*\"rate\""))
        assertContains(json, Regex("\"expectedAmount\":\\s*\"expected amount\""))
        assertContains(json, Regex("\"location\":\\s*\"location\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as OfferCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"offer": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, OfferCreateRequest::class.java)

        assertEquals(null, obj.offer)
    }
}