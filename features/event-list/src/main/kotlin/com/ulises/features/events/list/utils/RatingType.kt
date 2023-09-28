package com.ulises.features.events.list.utils

import androidx.annotation.DrawableRes
import com.ulises.features.events.list.R

sealed class RatingType(@DrawableRes val icon: Int, val value: Int) {
    data object Bad: RatingType(R.drawable.img_sad_rate, -1)
    data object Neutral: RatingType(R.drawable.img_plain_rate, 0)
    data object Good: RatingType(R.drawable.img_smile_rate, 1)

    companion object {
        fun fromIntRating(rating: Int): RatingType {
            return when (rating) {
                -1 -> Bad
                0 -> Neutral
                else -> Good
            }
        }
    }
}