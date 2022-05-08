package com.example.myapplicationtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.speech.tts.TextToSpeech
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var mTTS: TextToSpeech? = null
    private var inputValor: EditText? = null
    private var inputNumPessoas: EditText? = null
    private var inputResultado:TextView? = null
    private var buttonCompartilhar: Button? = null
    private var buttonFalar: Button? = null
    private var total: Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializaWidgets()

        /* Inicializando text to speech */
        mTTS = TextToSpeech(this, TextToSpeech.OnInitListener { i->
            buttonCompartilhar!!.isEnabled = false
            if (i == TextToSpeech.SUCCESS) {
                buttonCompartilhar!!.isEnabled = true
            }
        })

        /* Comportamento campo de texto do valor */
        inputValor!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (inputNumPessoas!!.text.isEmpty() || inputNumPessoas!!.text.toString().toInt() < 1)  {
                    if (s.isEmpty()) {
                        inputResultado!!.setText(R.string.value_empty)
                    } else {
                        total = s.toString().toDouble()
                        inputResultado!!.text = getString(R.string.value_nonempty, String.format("%.2f", total))
                    }
                } else  {
                    total = s.toString().toDouble() / inputNumPessoas!!.text.toString().toDouble()
                    inputResultado!!.text = getString(R.string.value_nonempty, String.format("%.2f", total))
                }
            }
        })

        /* Comportamento campo de número de pessoas */
        inputNumPessoas!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (inputNumPessoas!!.text.isEmpty() || inputNumPessoas!!.text.toString().toInt() < 1)  {
                    if (inputValor!!.text.isEmpty()) {
                        inputResultado!!.setText(R.string.value_empty)
                    } else {
                        total =  inputValor!!.text.toString().toDouble()
                        inputResultado!!.text = getString(R.string.value_nonempty, String.format("%.2f", total))
                    }
                } else  {
                    val total: Double = inputValor!!.text.toString().toDouble() / inputNumPessoas!!.text.toString().toDouble()
                    inputResultado!!.text = getString(R.string.value_nonempty, String.format("%.2f", total))
                }
            }
        })

        /* Comportamento do botão compartilhar */
        buttonCompartilhar!!.setOnClickListener {
            compartilharTexto()
        }

        /* Comportamento do botão Falar */
        buttonFalar!!.setOnClickListener {
            falarTexto()
        }
    }

    private fun compartilharTexto() {
        val texto = inputResultado!!.text
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, texto.toString())
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Compartilhar valor da conta")
        startActivity(shareIntent)
    }

    private fun falarTexto() {
        val texto = inputResultado!!.text.toString()
        mTTS!!.speak(texto, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun inicializaWidgets() {
        inputValor = findViewById(R.id.inputValor)
        inputNumPessoas = findViewById(R.id.inputNumPessoas)
        inputResultado = findViewById(R.id.outputResultado)
        buttonCompartilhar = findViewById(R.id.compartilhar)
        buttonFalar = findViewById(R.id.falar)
    }
}