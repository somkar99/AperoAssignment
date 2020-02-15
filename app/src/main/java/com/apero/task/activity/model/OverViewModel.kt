package com.apero.task.activity.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OverViewModel {
    @SerializedName("rating")
    @Expose
     val rating: Int? = null
    @SerializedName("review_count")
    @Expose
     val reviewCount: Int? = null
    @SerializedName("restaurant_name")
    @Expose
     val restaurantName: String? = null
    @SerializedName("restaurant_image")
    @Expose
     val restaurantImage: String? = null
    @SerializedName("cuisines")
    @Expose
     val cuisines: String? = null
    @SerializedName("latitude")
    @Expose
     val latitude: String? = null
    @SerializedName("longitude")
    @Expose
     val longitude: String? = null
    @SerializedName("locality")
    @Expose
     val locality: String? = null
    @SerializedName("phone_numbers")
    @Expose
     val phoneNumbers: String? = null
    @SerializedName("address")
    @Expose
     val address: String? = null
    @SerializedName("overview")
    @Expose
     val overview: Overview? = null
    @SerializedName("photo")
    @Expose
     val photo: List<Photo>? =
        null
    @SerializedName("review")
    @Expose
     val review: List<Review>? =
        null

    inner class Overview {
        @SerializedName("website")
        @Expose
         val website: String? = null
        @SerializedName("phone_numbers")
        @Expose
         val phoneNumbers: String? = null
        @SerializedName("email")
        @Expose
         val email: String? = null
        @SerializedName("latitude")
        @Expose
         val latitude: String? = null
        @SerializedName("longitude")
        @Expose
         val longitude: String? = null
    }

    inner class Photo {
        @SerializedName("photo")
        @Expose
         val photo: String? = null
        @SerializedName("name")
        @Expose
         val name: String? = null
        @SerializedName("profile")
        @Expose
         val profile: String? = null
        @SerializedName("user_id")
        @Expose
         val userId: Int? = null
        @SerializedName("created_at")
        @Expose
         val createdAt: String? = null
    }

    inner class Review {
        @SerializedName("id")
        @Expose
         val id: Int? = null
        @SerializedName("user_id")
        @Expose
         val userId: Int? = null
        @SerializedName("restaurant_id")
        @Expose
         val restaurantId: Int? = null
        @SerializedName("review")
        @Expose
         val review: String? = null
        @SerializedName("rating")
        @Expose
         val rating: Int? = null
        @SerializedName("created_at")
        @Expose
         val createdAt: String? = null
        @SerializedName("is_inappropriate")
        @Expose
         val isInappropriate: Int? = null
        @SerializedName("like_count")
        @Expose
         val likeCount: Int? = null
        @SerializedName("helpful_count")
        @Expose
         val helpfulCount: Int? = null
        @SerializedName("is_like")
        @Expose
         val isLike: Int? = null
        @SerializedName("is_helpful")
        @Expose
         val isHelpful: Int? = null
        @SerializedName("restaurant_name")
        @Expose
         val restaurantName: String? = null
        @SerializedName("restaurant_thumbnail")
        @Expose
         val restaurantThumbnail: String? = null
        @SerializedName("name")
        @Expose
         val name: String? = null
        @SerializedName("profile")
        @Expose
         val profile: String? = null
        @SerializedName("photos")
        @Expose
         val photos: List<Photo>? =
            null
    }
}