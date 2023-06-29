package com.st10083983.kronos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TimerActivity : AppCompatActivity() {
    private var pomodoroTimer: CountDownTimer? = null
    private var timerRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_timer)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = findViewById<Button>(R.id.timer_button)
        timer.setOnClickListener{
            if (!timerRunning) {
                startTimer()
                timerRunning = true
                timer.text = "Pause"
            }
            else {
                pomodoroTimer?.cancel()
                timerRunning = false
                timer.text = "Start"
            }
        }
        // Back Button
        val navBackHome = findViewById<Button>(R.id.timer_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun startTimer() {
        val timer = findViewById<Button>(R.id.timer_button)
        val timerText = findViewById<TextView>(R.id.timer_text_view)
        val timerLength : Long = 25 * 60 * 1000
        pomodoroTimer = object : CountDownTimer(timerLength,1000){
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timerText.text = "$minutes:$seconds"
            }
            override fun onFinish() {
                pomodoroTimer?.cancel()
                timerRunning = false
                // Display the dialog
                val builder = AlertDialog.Builder(this@TimerActivity)
                builder.setMessage("Start a 5-minute break timer?")
                    .setPositiveButton("Yes") { _, _ -> startBreakTimer() }
                    .setNegativeButton("No") { _, _ ->

                        startTimer()
                        timerRunning = true
                        timer.text = "Pause"
                    }
                    .show()
                // Create the notification
                showNotification()
            }
        }
        pomodoroTimer?.start()
    }
    private fun showNotification() {
        val builder = NotificationCompat.Builder(this@TimerActivity, "pomodoro_channel")
           // .setSmallIcon(R.drawable.CHRONOS)
            .setContentTitle("Pomodoro Timer")
            .setContentText("Your Pomodoro session is finished!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        //    .setSound(Uri.parse("https://firebasestorage.googleapis.com/v0/b/mynotes-8b6d5.appspot.com/o/mixkit-scanning-sci-fi-alarm-905.wav?alt=media&token=5bebfd2b-3bc3-45a4-8a2d-acb2f3f0e182"))

        // Display the notification
        val notificationManager = NotificationManagerCompat.from(this@TimerActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(0, builder.build())
    }
    private fun startBreakTimer() {
        val timer = findViewById<Button>(R.id.timer_button)
        val timerLength : Long = 5 * 60 * 1000
        pomodoroTimer = object : CountDownTimer(timerLength, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timer.text = "$minutes:$seconds"

            }
            override fun onFinish() {
                pomodoroTimer?.cancel()
                timerRunning = false
                val builder = AlertDialog.Builder(this@TimerActivity)
                builder.setMessage("Start a 25-minute Pomodoro timer?")
                    .setPositiveButton("Yes") { _, _ -> startTimer() }
                    .setNegativeButton("No") { _, _ ->
                        Toast.makeText(this@TimerActivity, "Thank you for using the Pomodoro timer!", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }
        pomodoroTimer?.start()
    }

}