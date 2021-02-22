package com.emarketing.ffmpegexample

interface FFmpegCallBack {
    fun onSuccess( cod:Int,result:String);
    fun onFail( cod:Int,result:String);
}