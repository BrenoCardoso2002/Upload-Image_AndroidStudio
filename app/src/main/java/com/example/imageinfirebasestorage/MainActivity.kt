package com.example.imageinfirebasestorage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val pickImageRequest = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var imagePreview: ImageView
    private lateinit var btnChoose: Button
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa as variaveis de campos:
        btnChoose = findViewById(R.id.bt_choice_image)
        btnSend = findViewById(R.id.bt_send_image)
        imagePreview = findViewById(R.id.img_image_selected)

        // Inicializa as variaveis do storage
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        btnChoose.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImageRequest)
        }

        btnSend.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (filePath != null){
            val ref = storageReference?.child("images/" + UUID.randomUUID().toString())
            ref?.putFile(filePath!!)
            Toast.makeText(this, "Imagem enviada com sucesso!!!", Toast.LENGTH_SHORT).show()
            imagePreview.setImageResource(android.R.color.transparent)
            filePath = null
        }else{
            Toast.makeText(this, "Escolha uma imagem", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImageRequest){
            filePath = data?.data
            imagePreview.setImageURI(filePath)
        }
    }


}