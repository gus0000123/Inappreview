package com.jonghyun.review

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewManagerFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv).setOnClickListener {
            Toast.makeText(this@MainActivity, "리뷰 팝업 클릭", Toast.LENGTH_SHORT).show()
            val manager = ReviewManagerFactory.create(this)
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val reviewInfo = task.result!!
                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener { task1 ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@MainActivity, "리뷰 노출 성공::", Toast.LENGTH_SHORT)
                                .show()
                            //성공 이후 프로세스 노출(toast등)
                        } else {
                            Toast.makeText(this@MainActivity, "리뷰 노출 실패 task1:: ${task1.exception.toString()}", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "리뷰 노출 실패 task:: ${task.exception.toString()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}