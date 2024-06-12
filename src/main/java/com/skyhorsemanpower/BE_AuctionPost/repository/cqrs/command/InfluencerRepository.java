package com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.Influencer;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {

	Optional<Influencer> findByUuid(String uuid);

	Optional<Influencer> findByName(String name);

	Optional<Influencer> findByPhoneNum(String phoneNum);

	Optional<Influencer> findByNameAndPhoneNumAndBirthDate(String name, String phoneNum,
		LocalDate birthDate);
}
