import RoomsAndAddOns.PricingComponent;
import RoomsAndAddOns.*;

import java.util.Map;

public class PricingRegistry {
    public static final Map<Integer, PricingComponent> ROOM_PRICES = Map.of(
            LegacyRoomTypes.SINGLE, new SingleRoomPricing(),
            LegacyRoomTypes.DOUBLE, new DoubleRoomPricing(),
            LegacyRoomTypes.TRIPLE, new TripleRoomPricing(),
            LegacyRoomTypes.DELUXE, new DeluxeRoomPricing()
    );

    public static final Map<AddOn, PricingComponent> ADDON_PRICES = Map.of(
            AddOn.MESS, new MessPricing(),
            AddOn.LAUNDRY, new LaundryPricing(),
            AddOn.GYM, new GymPricing()
    );
}
