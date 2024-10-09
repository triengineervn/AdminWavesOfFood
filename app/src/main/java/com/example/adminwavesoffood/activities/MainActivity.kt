package com.example.adminwavesoffood.activities

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityMainBinding
import com.example.adminwavesoffood.models.OrdersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.btnAddMenu.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnAllItemMenu.setOnClickListener {
            val intent = Intent(this, ListItemActivity::class.java)
            startActivity(intent)
        }
        binding.btnOrderDispatch.setOnClickListener {
            val intent = Intent(this, OrderDispatchActivity::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        binding.pendingView.setOnClickListener {
            val intent = Intent(this, PendingOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.completedOrders.setOnClickListener {
            val intent = Intent(this, CompletedOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogOut.setOnClickListener {
            showWarningDialog("Are you sure you want to log out?")
        }

        setTextViewOrderInfo()
    }


    private fun showWarningDialog(title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setPositiveButton("Yes") { _, _ ->
            logout()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    private fun setTextViewOrderInfo() {
        setOrderPendingText()
        setOrderCompletedText()
        setTotalPriceText()
    }

    private fun setOrderPendingText() {
        val orderPendingRef = database.reference.child("users/${userId}/orders")
        var orderPendingCount = 0
        orderPendingRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderPendingCount = snapshot.childrenCount.toInt()
                binding.orderPendingText.text = orderPendingCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.orderPendingText.text = orderPendingCount.toString()
                Toast.makeText(this@MainActivity, "Cannot load pending order", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setOrderCompletedText() {
        val orderCompletedRef = database.reference.child("users/${userId}/completedOrders")
        var orderCompletedCount = 0
        orderCompletedRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderCompletedCount = snapshot.childrenCount.toInt()
                binding.orderCompletedText.text = orderCompletedCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.orderCompletedText.text = orderCompletedCount.toString()
                Toast.makeText(this@MainActivity, "Cannot load completed order", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setTotalPriceText() {
        val orderCompletedRef = database.reference.child("users/${userId}/completedOrders")
        var orderCompletedPaid = mutableListOf<Int>()
        orderCompletedRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    var completedOrder = orderSnapshot.getValue(OrdersModel::class.java)
                    completedOrder?.totalPrice?.toIntOrNull().let { i ->
                        orderCompletedPaid.add(i!!)
                    }
                }
                binding.totalPriceText.text = orderCompletedPaid.sum().toString() + "$"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Cannot load completed order", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}