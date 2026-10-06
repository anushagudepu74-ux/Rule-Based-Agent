package com.example.rulebasedagent

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var sendButton: Button

    private lateinit var perceptionText: TextView
    private lateinit var reasoningText: TextView
    private lateinit var actionText: TextView
    private lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        sendButton = findViewById(R.id.sendButton)

        perceptionText = findViewById(R.id.perceptionText)
        reasoningText = findViewById(R.id.reasoningText)
        actionText = findViewById(R.id.actionText)
        responseText = findViewById(R.id.responseText)

        sendButton.setOnClickListener {
            runAgent()
        }
    }

    private fun runAgent() {

        // 1. PERCEPTION
        val userInput = inputEditText.text.toString().trim().lowercase()

        perceptionText.text = "Perception: Received input = '$userInput'"

        if (userInput.isEmpty()) {
            reasoningText.text = "Reasoning: No input detected"
            actionText.text = "Action: Ask user to type something"
            responseText.text = "Please type a command."
            return
        }

        // 2. REASONING + 3. ACTION
        when {

            userInput.contains("hello") || userInput.contains("hi") -> {
                reasoningText.text = "Reasoning: User is greeting the agent"
                actionText.text = "Action: Send greeting response"
                responseText.text = "Hello! How can I help you today?"
            }

            userInput.contains("time") -> {
                val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

                reasoningText.text = "Reasoning: User wants current time"
                actionText.text = "Action: Fetch and display system time"
                responseText.text = "Current time is $currentTime"
            }

            userInput.contains("date") -> {
                val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                reasoningText.text = "Reasoning: User wants current date"
                actionText.text = "Action: Fetch and display system date"
                responseText.text = "Today's date is $currentDate"
            }

            userInput.contains("open settings") -> {
                reasoningText.text = "Reasoning: User wants to open device settings"
                actionText.text = "Action: Launch Android Settings activity"
                responseText.text = "Opening Settings..."

                startActivity(Intent(Settings.ACTION_SETTINGS))
            }

            userInput.contains("bye") || userInput.contains("goodbye") -> {
                reasoningText.text = "Reasoning: User wants to end conversation"
                actionText.text = "Action: Send goodbye message"
                responseText.text = "Goodbye! Have a great day."
            }

            else -> {
                reasoningText.text = "Reasoning: No matching rule found"
                actionText.text = "Action: Return fallback response"
                responseText.text = "Sorry, I don't understand that command."
            }
        }

        Toast.makeText(this, "Agent processed the request", Toast.LENGTH_SHORT).show()
    }
}