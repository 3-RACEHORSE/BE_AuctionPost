package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocalNameEnum {
    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    GYEONGGI_DO("경기도"),
    GANGWON_DO("강원도"),
    CHUNGCHEONGNAM_DO("충청남도"),
    CHUNGCHEONGBUK_DO("충청북도"),
    JEOLLANAM_DO("전라남도"),
    JEOLLABUK_DO("전라북도"),
    GYEONGSANGNAM_DO("경상남도"),
    GYEONGSANGBUK_DO("경상북도"),
    JEJO_DO("제주도");

    private final String localName;
}
