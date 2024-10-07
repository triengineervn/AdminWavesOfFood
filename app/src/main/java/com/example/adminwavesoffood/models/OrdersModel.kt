package com.example.adminwavesoffood.models

import CartModel
import android.os.Parcel
import android.os.Parcelable

class OrdersModel(
    var userId: String? = null,
    var userName: String? = null,
    var cartItem: MutableList<CartModel>? = null,
    var totalPrice: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var orderAccepted: Boolean = false,
    var paymentReceived: Boolean = false,
    var itemPushKey: String? = null,
    var currentTime: Long? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        userId = parcel.readString(),
        userName = parcel.readString(),
        cartItem = parcel.createTypedArrayList(CartModel.CREATOR),
        totalPrice = parcel.readString(),
        address = parcel.readString(),
        phone = parcel.readString(),
        orderAccepted = parcel.readByte() != 0.toByte(),
        paymentReceived = parcel.readByte() != 0.toByte(),
        itemPushKey = parcel.readString(),
        currentTime = parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeTypedList(cartItem)
        parcel.writeString(totalPrice)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeValue(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrdersModel> {
        override fun createFromParcel(parcel: Parcel): OrdersModel {
            return OrdersModel(parcel)
        }

        override fun newArray(size: Int): Array<OrdersModel?> {
            return arrayOfNulls(size)
        }
    }


}