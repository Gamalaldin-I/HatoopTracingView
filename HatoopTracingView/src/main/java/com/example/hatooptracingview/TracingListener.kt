package com.example.hatooptracingview
interface TracingListener {
    fun onCompleted()
    fun onCompletedPathsChange(completedPaths:Int)
    fun onProgressChange(percentage:Int)
    fun onReset()
}