package com.hicham.airbnb_clone_back.listing.domain;

import com.hicham.airbnb_clone_back.sharedKernel.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "listing")
public class Listing extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listing_generator")
    @SequenceGenerator(name = "listing_generator", sequenceName = "airbnb_clone.listing_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "guests")
    private int guests;
    @Column(name = "bedrooms")
    private int bedrooms;
    @Column(name = "beds")
    private int beds;
    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "price")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private BookingCategory bookingCategory;

    @Column(name = "location")
    private String location;

    @Column(name = "landlord_public_id")
    private UUID landlordPublicId;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE)
    private Set<ListingPicture> pictures = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BookingCategory getBookingCategory() {
        return bookingCategory;
    }

    public void setBookingCategory(BookingCategory bookingCategory) {
        this.bookingCategory = bookingCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getLandlordPublicId() {
        return landlordPublicId;
    }

    public void setLandlordPublicId(UUID landlordPublicId) {
        this.landlordPublicId = landlordPublicId;
    }

    public Set<ListingPicture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<ListingPicture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return guests == listing.guests && bedrooms == listing.bedrooms && beds == listing.beds && bathrooms == listing.bathrooms && price == listing.price && Objects.equals(id, listing.id) && Objects.equals(publicId, listing.publicId) && Objects.equals(title, listing.title) && Objects.equals(description, listing.description) && bookingCategory == listing.bookingCategory && Objects.equals(location, listing.location) && Objects.equals(landlordPublicId, listing.landlordPublicId) && Objects.equals(pictures, listing.pictures);
    }

    @Override
    public int hashCode() {
        return Objects.hash( publicId, title, description, guests, bedrooms, beds, bathrooms, price, bookingCategory, location, landlordPublicId, pictures);
    }
}
