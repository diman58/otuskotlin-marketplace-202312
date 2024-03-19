package models

data class ExchangeOfferFilter(
    var searchString: String = "",
    var ownerId: ExchangeOfferUserId = ExchangeOfferUserId.NONE,
    var dealSide: HubDealSide = HubDealSide.NONE,
)
