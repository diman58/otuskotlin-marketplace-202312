package models

data class HubOfferFilter(
    var searchString: String = "",
    var ownerId: HubOfferUserId = HubOfferUserId.NONE,
    var dealSide: HubDealSide = HubDealSide.NONE,
)
