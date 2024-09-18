package com.example.ai

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class lungcancer : AppCompatActivity() {
    @SuppressLint("DefaultLocal")

    lateinit var SpinnerGender: Spinner
    lateinit var SpinnerSmoking: Spinner
    lateinit var SpinnerAllergy: Spinner
    lateinit var SpinnerFatigue: Spinner
    lateinit var SpinnerCoughing: Spinner
    lateinit var SpinnerChro: Spinner
    lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lungcancer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        SpinnerGender = findViewById(R.id.spinnerGender)
        SpinnerSmoking = findViewById(R.id.spinnerSmoking)
        SpinnerAllergy = findViewById(R.id.spinnerAllergy)
        SpinnerFatigue = findViewById(R.id.spinnerFatigue)
        SpinnerCoughing = findViewById(R.id.spinnerCoughing)
        SpinnerChro = findViewById(R.id.spinnerChro)
        editTextAge = findViewById(R.id.editTextAge)
        val btngo = findViewById<Button>(R.id.btngo)

        btngo.setOnClickListener {
            val url: String = getString(R.string.root_url)

            val okHttpClient = OkHttpClient()
            val formBody: FormBody = FormBody.Builder()
                .add("gender", SpinnerGender.selectedItemId.toString())
                .add("smoking", SpinnerSmoking.selectedItemId.toString())
                .add("allegery", SpinnerAllergy.selectedItemId.toString())
                .add("fatigue", SpinnerFatigue.selectedItemId.toString())
                .add("coughing", SpinnerCoughing.selectedItemId.toString())
                .add("chro", SpinnerChro.selectedItemId.toString())
                .add("age", editTextAge.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val data = JSONObject(response.body!!.string())
                if (data.length() > 0) {
                    val prediction = data.getString( "Prediction")
                    val message = "ผลการตรวจเช็ค:  $prediction"
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("ผลการตรวจเช็ค")
                    builder.setMessage(message)
                    builder.setNeutralButton("ตกลง", clearText())
                    val alert = builder.create()
                    alert.show()
                }
            } else {
                Toast.makeText(this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
            }
        }


        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)

        val adapterGender = ArrayAdapter.createFromResource(
            this,
            R.array.gender,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender

        val spinnerSmoking = findViewById<Spinner>(R.id.spinnerSmoking)

        val adapterSmoking = ArrayAdapter.createFromResource(
            this,
            R.array.smoking,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterSmoking.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSmoking.adapter = adapterSmoking

        val spinnerAllergy = findViewById<Spinner>(R.id.spinnerAllergy)

        val adapterAllergy = ArrayAdapter.createFromResource(
            this,
            R.array.allergy,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterAllergy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAllergy.adapter = adapterAllergy

        val spinnerFatigue = findViewById<Spinner>(R.id.spinnerFatigue)

        val adapterFatigue = ArrayAdapter.createFromResource(
            this,
            R.array.fatigue,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterFatigue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFatigue.adapter = adapterFatigue

        val spinnerCoughing = findViewById<Spinner>(R.id.spinnerCoughing)

        val adapterCoughing = ArrayAdapter.createFromResource(
            this,
            R.array.coughing,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterCoughing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCoughing.adapter = adapterCoughing

        val spinnerChro = findViewById<Spinner>(R.id.spinnerChro)

        val adapterChro = ArrayAdapter.createFromResource(
            this,
            R.array.chro,  // ใส่ชื่อของ array ที่ต้องการใช้
            android.R.layout.simple_spinner_item
        )

        adapterChro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerChro.adapter = adapterChro


    }

    private fun clearText(): DialogInterface.OnClickListener? {
        return DialogInterface.OnClickListener { dialog, which ->
            editTextAge.text.clear()
            SpinnerGender.setSelection(0)
            SpinnerSmoking.setSelection(0)
            SpinnerAllergy.setSelection(0)
            SpinnerFatigue.setSelection(0)
            SpinnerCoughing.setSelection(0)
            SpinnerChro.setSelection(0)


        }
    }
}
