package com.emarketing.ffmpegexample

import android.annotation.SuppressLint
import android.util.Log
import com.arthenica.mobileffmpeg.FFmpeg
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class SplitVedio (private val orginalRecordedVideo:String, private val videoPathCoped:String, val  image:String,val start:Int, val end:Int,val output:String, private val fFmpegCallBack: FFmpegCallBack) {
    companion object{
        var isRun=false
    }
    @SuppressLint("CheckResult")
    fun rx(){
        isRun=false
        Observable.fromCallable<Any> {
            try {
                val correctVideo:String = "-y  -i "+videoPathCoped+" -ss 00:00:00.$start -t "+(end)+" -acodec copy -preset veryfast "+videoPathCoped.replace(".mp4","trimmed.mp4")

                var scalingVedio = ""
                if(image.isNotEmpty()) {
                    scalingVedio =
                        "-y -framerate 30 -loop 1 -i " + image + "  -t "+end+" -pix_fmt yuv420p -vf crop=in_w/2:in_h:in_w/4:0 -preset veryfast " +
                                output
                }


                if (scalingVedio.trim().isNotEmpty())
                   FFmpeg.execute(scalingVedio)


                "FFmpeg.getLastCommandOutput()"

            } catch (e: IOException) {
                Log.e("Network request", "Failure", e)
            }

        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isRun=false
                fFmpegCallBack.onSuccess(0,"")

            }
    }
}
