package com.eternal.demo.utils

import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.blankj.utilcode.util.LogUtils
import com.eternal.demo.app.Constant
import com.eternal.demo.app.GlobalConfiguration


object LocationUtils {
    private var locationClient: LocationClient? = null
    private var locationListener: MylocationListener? = null
    private var locationClientOption: LocationClientOption? = null
    private var onReceiveLocation: OnReceiveLocation? = null
    fun init(): LocationUtils {
        if (locationClientOption == null) {
            locationClientOption = LocationClientOption()
        }
        if (locationClient == null) {
            locationClient = LocationClient(GlobalConfiguration.getApplicationContext())
        }
        if (locationListener == null) {
            locationListener = MylocationListener()
        }
        locationClientOption?.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        locationClientOption?.coorType = "bd0911"
        locationClientOption?.setIsNeedAddress(true)
        locationClientOption?.scanSpan = 1000
        locationClient?.locOption = locationClientOption
        locationClient?.registerLocationListener(locationListener)
        locationListener?.setOnReceiveLocation(object : MylocationListener.OnReceiveLocation {
            override fun receiveLocation(location: BDLocation?) {
                if (location == null) return
                val addr = location.addrStr    //获取详细地址信息
                val country = location.country    //获取国家
                val province = location.province    //获取省份
                val city = location.city    //获取城市
                val district = location.district    //获取区县
                val street = location.street    //获取街道信息
                if (city != null && city.isNotEmpty()) {
                    Constant.CITY = city
                }
                if (province != null && province.isNotEmpty()) {
                    Constant.PROVINCE = province
                }
                if (district != null && district.isNotEmpty()) {
                    Constant.DISTRICT = district
                }
              //  LogUtils.e("${city}${district}")
                onReceiveLocation?.receiveLocation(location)

            }
        })
        return this
    }

    fun startLocation(): LocationUtils {
        locationClient?.start()
        return this
    }

    fun stopLocation() {
        locationClient?.stop()
    }

    fun setOnReceiveLocation(listener: OnReceiveLocation) {
        this.onReceiveLocation = listener
    }

    interface OnReceiveLocation {
        fun receiveLocation(location: BDLocation)
    }
}

class MylocationListener : BDAbstractLocationListener() {
    private var onReceiveLocation: OnReceiveLocation? = null
    override fun onReceiveLocation(location: BDLocation) {
        if (onReceiveLocation != null) {
            onReceiveLocation?.receiveLocation(location)
        }
    }

    fun setOnReceiveLocation(listener: OnReceiveLocation) {
        this.onReceiveLocation = listener
    }

    interface OnReceiveLocation {
        fun receiveLocation(location: BDLocation?)
    }
}