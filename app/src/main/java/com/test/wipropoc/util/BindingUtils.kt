package com.test.wipropoc.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.test.wipropoc.R
import com.test.wipropoc.application.WiproPocApplication


class BindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun showImage(imageView: ImageView, url: String) {
            if (!url.isNullOrEmpty()) {
                imageView.visibility = View.GONE
                Picasso.with(imageView.context)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            imageView.visibility = View.VISIBLE
                        }

                        override fun onError() {
                            Logger.e(R.string.error, WiproPocApplication.applicationContext().getString(R.string.image_error))
                        }

                    })
            } else {
                imageView.visibility = View.GONE
            }

        }
    }

}