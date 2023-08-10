package com.example.admobads_jetpackcompose.util

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.isVisible
import com.example.admobads_jetpackcompose.databinding.AdmobNativeAdBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions


@Composable
fun AdmobNativeAd(
    adId: String, modifier: Modifier = Modifier
) {
    AndroidViewBinding(factory = AdmobNativeAdBinding::inflate) {

        val adView = root.also { adView ->
            adView.bodyView = this.adBody
            adView.callToActionView = this.adCallToAction
            adView.headlineView = this.adBody
            adView.iconView = this.adAppIcon
            adView.storeView = this.adStore
            adView.starRatingView = this.adStars
            adView.priceView = this.adPrice
            adView.advertiserView = this.adAdvertiser
            adView.mediaView = this.adMedia
        }

        val adContainer = this.adContainerLayout

        val adLoader = AdLoader.Builder(adView.context, adId).forNativeAd { nativeAd ->
            nativeAd.body.let { body ->
                this.adBody.text = body
            }
            nativeAd.callToAction.let { callToAction ->
                this.adCallToAction.text = callToAction
            }
            nativeAd.headline.let { headline ->
                this.adHeadline.text = headline
            }
            nativeAd.icon.let { icon ->
                if (icon != null) {
                    this.adAppIcon.setImageDrawable(icon.drawable)
                }
            }
            nativeAd.store.let { store ->
                this.adStore.text = store
            }
            nativeAd.starRating.let { starRate ->
                if (starRate != null) {
                    this.adStars.numStars = starRate.toInt()
                }
            }
            nativeAd.price.let { price ->
                this.adPrice.text = price
            }
            nativeAd.advertiser.let { advertiser ->
                this.adAdvertiser.text = advertiser
            }
            nativeAd.mediaContent.let { mediaAd ->
                this.adMedia.mediaContent = mediaAd
            }
            adView.setNativeAd(nativeAd)
        }.withAdListener(object : AdListener() {

            override fun onAdLoaded() {
                adContainer.isVisible = true
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                adContainer.isVisible = false
                super.onAdFailedToLoad(p0)
            }

        }).withNativeAdOptions(
            NativeAdOptions.Builder().setAdChoicesPlacement(
                NativeAdOptions.ADCHOICES_TOP_RIGHT
            ).build()
        ).build()
        adContainer.isVisible = true

        adLoader.loadAd(AdRequest.Builder().build())


    }
}


@Composable
fun AdmobBannerAd(adId: String) {

    AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = adId
            loadAd(AdRequest.Builder().build())
        }
    })

}







