package com.skyhorsemanpower.BE_AuctionPost.common;

import com.skyhorsemanpower.BE_AuctionPost.status.ResponseStatus;
import java.math.BigDecimal;

public class StringToBigDecimalConverter {

    public static BigDecimal convert(String str) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        if (str != null) {
            try {
                return new BigDecimal(str);
            } catch (NumberFormatException e) {
                throw new CustomException(ResponseStatus.INVALID_VALUE_TO_CONVERT_DECIMAL);
            }
        }
        return bigDecimal;
    }

}
