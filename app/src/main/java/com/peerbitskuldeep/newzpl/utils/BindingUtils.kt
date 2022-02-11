package com.peerbitskuldeep.newzpl.utils

import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.peerbitskuldeep.newzpl.R

object BindingUtils {

    @BindingAdapter("imageUrl", "isCircle", requireAll = false)
    @JvmStatic
    fun setImageUrl(
        imageView: ImageView,
        url: String?,
        circle: Boolean
    ) {
        val context = imageView.context

        val reqOption = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .centerCrop()
            .override(70, 70)
            .dontAnimate()
            .dontTransform()
            .placeholder(android.R.color.transparent)
            .priority(Priority.IMMEDIATE)
            .encodeFormat(Bitmap.CompressFormat.PNG)
            .format(DecodeFormat.DEFAULT)

        val options = RequestOptions().apply {
            if (circle) {
                circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_breaking_news)
            } else {
                diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_breaking_news)
                    .error(R.drawable.ic_launcher_background)
                    .signature(ObjectKey(System.currentTimeMillis().toString()))
            }
        }
        if (!TextUtils.isEmpty(url) && url!!.startsWith("http")) {
            Glide.with(context).load(url).apply(reqOption).into(imageView)
        }
    }


}