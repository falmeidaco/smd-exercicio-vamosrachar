package com.example.myapplicationtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputValor: EditText = findViewById(R.id.inputValor)
        val inputNumPessoas: EditText = findViewById(R.id.inputNumPessoas)
        val inputResultado:TextView = findViewById(R.id.outputResultado)
        val buttonCompartilhar: Button = findViewById(R.id.compartilhar)
        /* Comportamento campo de texto do valor */
        inputValor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (inputNumPessoas.text.isEmpty() || inputNumPessoas.text.toString().toInt() < 1)  {
                    if (s.isEmpty()) {
                        inputResultado.setText(R.string.value_empty)
                    } else {
                        inputResultado.text = getString(R.string.value_nonempty, String.format("%.2f", s.toString().toDouble()))
                    }
                } else  {
                    val calculo: Double = s.toString().toDouble() / inputNumPessoas.text.toString().toDouble()
                    inputResultado.text = getString(R.string.value_nonempty, String.format("%.2f", calculo))
                }
            }
        })
        /* Comportamento campo de número de pessoas */
        inputNumPessoas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (inputNumPessoas.text.isEmpty() || inputNumPessoas.text.toString().toInt() < 1)  {
                    if (inputValor.text.isEmpty()) {
                        inputResultado.setText(R.string.value_empty)
                    } else {
                        inputResultado.text = getString(R.string.value_nonempty, String.format("%.2f", inputValor.text.toString().toDouble()))
                    }
                } else  {
                    val calculo: Double = inputValor.text.toString().toDouble() / inputNumPessoas.text.toString().toDouble()
                    inputResultado.text = getString(R.string.value_nonempty, String.format("%.2f", calculo))
                }
            }
        })
        /* Comportamento do botão compartilhar */
        buttonCompartilhar.setOnClickListener {
            val texto = inputResultado.text
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, texto.toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Compartilhar valor da conta")
            startActivity(shareIntent)
        }
        /* Comportamento do botão Falar */
        buttonCompartilhar.setOnClickListener {
            val texto = inputResultado.text
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, texto.toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Compartilhar valor da conta")
            startActivity(shareIntent)
        }
    }
}