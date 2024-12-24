package com.hicham.airbnb_clone_back.listing.repository;

import com.hicham.airbnb_clone_back.listing.domain.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing,Long> {
}
