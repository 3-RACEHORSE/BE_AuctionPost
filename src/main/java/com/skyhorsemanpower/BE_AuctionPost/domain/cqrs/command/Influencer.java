package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "influencer_id")
    private Long id;
    @Column(name = "influencer_uuid", nullable = false)
    private String uuid;
    @Column(name = "name", nullable = false, length = 40)
    private String name;
    @Column(name = "profile_image", nullable = false, length = 1000)
    private String profileImage;
    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "description", length = 1000)
    private String description;

    @Builder
    public Influencer(Long id, String uuid, String name, String profileImage, String phoneNum,
        LocalDate birthDate, String description) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.profileImage = profileImage;
        this.phoneNum = phoneNum;
        this.birthDate = birthDate;
        this.description = description;
    }
}
