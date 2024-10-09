package com.example.adminwavesoffood.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapters.OrdersAdapter
import com.example.adminwavesoffood.databinding.ActivityPendingOrdersBinding
import com.example.adminwavesoffood.models.OrdersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrdersActivity : AppCompatActivity(), OrdersAdapter.OnItemClickListener {

    private val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    private var listOfOrders: MutableList<OrdersModel> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var ordersRef: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        ordersRef = database.reference.child("users")
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.backBtn.setOnClickListener {
            finish()
        }

        retrievePendingOrders()
    }

    private fun retrievePendingOrders() {
        ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfOrders.clear() // Clear the list before adding new data

                for (orderSnapshot in snapshot.children) {
                    val ordersSnapshot = orderSnapshot.child("orders")
                    for (orderItemSnapshot in ordersSnapshot.children) {
                        val orderItem = orderItemSnapshot.getValue(OrdersModel::class.java)
                        orderItem?.let { listOfOrders.add(it) }
                    }
                }
                // Set the adapter after all data is retrieved
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@PendingOrdersActivity,
                    "Cancelled: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setAdapter() {
        val adapter = OrdersAdapter(
            listOfOrders,
            this@PendingOrdersActivity
        )
        binding.pendingOrdersRecyclerView.layoutManager =
            LinearLayoutManager(this@PendingOrdersActivity)
        binding.pendingOrdersRecyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this@PendingOrdersActivity, OrdersDetailActivity::class.java)
        val userOrderDetail = listOfOrders[position]
        intent.putExtra("userOrderDetail", userOrderDetail)
        startActivity(intent)
    }


    private fun updateOrderAcceptedStatus(position: Int) {
        val userIdOfClickItem = listOfOrders[position].userId
        val pushKeyOfClickItem = listOfOrders[position].itemPushKey
        if (userIdOfClickItem != null && pushKeyOfClickItem != null) {
            val reBuyRef =
                database.reference.child("users").child(userIdOfClickItem).child("history")
                    .child(pushKeyOfClickItem)
            reBuyRef.setValue(listOfOrders[position])
        }
    }

    override fun onItemAccept(position: Int) {
        listOfOrders[position].itemPushKey?.let { childItemPushKey ->
            val clickItemRef =
                database.reference.child("users/${listOfOrders[position].userId}/orders")
                    .child(childItemPushKey)

            clickItemRef.child("orderAccepted").setValue(true)
            updateOrderAcceptedStatus(position)
        }
    }

    override fun onItemDispatch(position: Int) {


        val dispatchItemPushKey = listOfOrders[position].itemPushKey!!
        val dispatchItemOrderRef =
            database.reference.child("users/${userId}/completedOrders")
                .child(dispatchItemPushKey)
        dispatchItemOrderRef.setValue(listOfOrders[position])
            .addOnSuccessListener {
                deleteThisItemFromOrders(dispatchItemPushKey)
            }


    }

    override fun deleteThisItemFromOrders(dispatchItemPushKey: String) {
        val orderDetailsRef =
            database.reference.child("users/${userId}/orders")
                .child(dispatchItemPushKey)
        orderDetailsRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order has been accepted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to accept order", Toast.LENGTH_SHORT).show()
            }
    }
}
