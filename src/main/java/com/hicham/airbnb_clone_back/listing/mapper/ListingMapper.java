package com.hicham.airbnb_clone_back.listing.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = ListingMapper.class )
public interface ListingMapper {
}
