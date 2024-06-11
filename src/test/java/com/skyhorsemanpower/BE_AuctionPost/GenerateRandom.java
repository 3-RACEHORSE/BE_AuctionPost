package com.skyhorsemanpower.BE_AuctionPost;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class GenerateRandom {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    private static String string(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private static String createUuid() {
        String character = "0123456789";
        StringBuilder uuid = new StringBuilder("");
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            uuid.append(character.charAt(random.nextInt(character.length())));
        }
        return uuid.toString();
    }

    public static String auctionUuid() {
        // 현재 날짜와 시간을 "yyyyMMddHHmm" 형식으로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);

        // UUID 생성 후 앞부분의 10자리 문자열 추출
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        // 날짜 시간과 UUID의 앞부분을 합쳐 UUID 생성
        return dateTime + "-" + uuid;
    }

    public static String paymentUuid() {
        return createUuid();
    }

    public static String memberUuid() {
        return UUID.randomUUID().toString();
    }

    public static String sellerUuid() {
        return UUID.randomUUID().toString();
    }

    public static String sellerHandle() {
        return String.format("@user-%s", string(9));
    }
}
