package com.example.miapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.io.File

class MainActivity : Activity() {
    private lateinit var visor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val capa = LinearLayout(this)
        capa.orientation = LinearLayout.VERTICAL
        capa.setPadding(32, 96, 32, 32)

        val btnPermiso = Button(this)
        btnPermiso.text = "1. Dar permiso de notificaciones"
        btnPermiso.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        val btnActualizar = Button(this)
        btnActualizar.text = "2. Actualizar lista"
        btnActualizar.setOnClickListener { mostrar() }

        val btnBorrar = Button(this)
        btnBorrar.text = "Borrar registro"
        btnBorrar.setOnClickListener {
            File(filesDir, "registro.txt").delete()
            mostrar()
        }

        visor = TextView(this)
        visor.textSize = 15f
        visor.setTextIsSelectable(true)

        val scroll = ScrollView(this)
        scroll.addView(visor)

        capa.addView(btnPermiso)
        capa.addView(btnActualizar)
        capa.addView(btnBorrar)
        capa.addView(
            scroll,
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
        )
        setContentView(capa)
    }

    override fun onResume() {
        super.onResume()
        mostrar()
    }

    private fun mostrar() {
        val archivo = File(filesDir, "registro.txt")
        if (!archivo.exists()) {
            visor.text = "Aún no hay mensajes guardados."
            return
        }
        val lineas = archivo.readLines().filter { it.isNotBlank() }.reversed()
        visor.text = lineas.joinToString("\n\n")
    }
}
