package com.sts.ssms.data.helpdesk

interface RatingRequest {
    val id: Int
    val rating: Int
    val comment: String
}