package com.magni.coursesdemo.presentation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest


class FillWidthTransformation : BitmapTransformation() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val scale = outWidth.toFloat() / toTransform.width
        val scaledHeight = (scale * toTransform.height).toInt()

        val result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888)
        result.setHasAlpha(true)

        val canvas = Canvas(result)
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        canvas.drawBitmap(toTransform, matrix, Paint(Paint.FILTER_BITMAP_FLAG))

        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun equals(o: Any?): Boolean {
        return o is FillWidthTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    companion object {
        private const val ID = "com.example.FillWidthTransformation"
        private val ID_BYTES = ID.toByteArray()
    }
}