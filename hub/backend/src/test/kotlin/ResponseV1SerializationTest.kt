import com.otus.otuskotlin.hub.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = OfferCreateResponse(
        responseType = "create",
        result = ResponseResult.SUCCESS,
        errors = listOf(OfferError("error code", "error group", "error field", "error message")),
        offer = OfferResponseObject(
            title = "Offer title",
            offerType = OfferDealSide.SUPPLY,
            offeredCurrency = "offered currency",
            desiredCurrency = "desired currency",
            amount = "amount",
            rate = "rate",
            expectedAmount = "expected amount",
            location = "location",
            visibility = OfferVisibility.OWNER_ONLY,
            offerId = "offer id",
            ownerId = "owner id",
            lock = "lock",
            permissions = setOf(OfferPermissions.READ, OfferPermissions.DELETE, OfferPermissions.UPDATE)
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"responseType\":\\s*\"create\""))

        assertContains(json, Regex("\"result\":\\s*\"success\""))

        assertContains(json, Regex("\"errors\":\\s*\\[\\{[\\S\\s]*}]"))
        assertContains(json, Regex("\"code\":\\s*\"error code\""))
        assertContains(json, Regex("\"group\":\\s*\"error group\""))
        assertContains(json, Regex("\"field\":\\s*\"error field\""))
        assertContains(json, Regex("\"message\":\\s*\"error message\""))

        assertContains(json, Regex("\"title\":\\s*\"Offer title\""))
        assertContains(json, Regex("\"offerType\":\\s*\"supply\""))
        assertContains(json, Regex("\"offeredCurrency\":\\s*\"offered currency\""))
        assertContains(json, Regex("\"desiredCurrency\":\\s*\"desired currency\""))
        assertContains(json, Regex("\"amount\":\\s*\"amount\""))
        assertContains(json, Regex("\"rate\":\\s*\"rate\""))
        assertContains(json, Regex("\"expectedAmount\":\\s*\"expected amount\""))
        assertContains(json, Regex("\"location\":\\s*\"location\""))
        assertContains(json, Regex("\"visibility\":\\s*\"ownerOnly\""))
        assertContains(json, Regex("\"offerId\":\\s*\"offer id\""))
        assertContains(json, Regex("\"ownerId\":\\s*\"owner id\""))
        assertContains(json, Regex("\"lock\":\\s*\"lock\""))
        assertContains(json, Regex("\"permissions\":\\s*\\[\"read\",\"delete\",\"update\"]"))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as OfferCreateResponse

        assertEquals(response, obj)
    }
}