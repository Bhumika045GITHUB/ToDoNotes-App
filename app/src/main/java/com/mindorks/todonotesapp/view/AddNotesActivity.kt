package com.mindorks.todonotesapp.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.CaseMap
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.mindorks.todonotesapp.BuildConfig
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.utils.AppConstant
import java.io.File
import java.lang.Exception
import java.util.*

class AddNotesActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var submitButton: Button
    lateinit var imageViewAdd: ImageView
    val MY_PERMISSION_CODE = 124
    val REQUEST_CODE_GALLERY = 2
    val REQUEST_CODE_CAMERA = 1
    var picturePath = ""
    lateinit var imageLocation : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
        clickListener()
    }

    private fun clickListener() {
        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra(AppConstant.TITLE, editTextTitle.text.toString())
                intent.putExtra(AppConstant.DESCRIPTION, editTextDescription.text.toString())
                intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })

        imageViewAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(checkAndRequestPermission()) {
                setupDialog()
            }
        }
        })
    }

    private fun checkAndRequestPermission(): Boolean {
            val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val listPermissionNeeded = ArrayList<String>()
            if(storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if(storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.CAMERA)
        }

        if(listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray<String>(), MY_PERMISSION_CODE)
            return false
        }
        else
            return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            MY_PERMISSION_CODE ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setupDialog()
            }
        }
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dailog_selector, null)
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()
        dialog.show()

        textViewCamera.setOnClickListener(object  : View. OnClickListener{
           @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(takePictureIntent.resolveActivity(packageManager) != null) {
                    var photoFile : File? = null

                   try {
                       photoFile = createImageFile()
                   } catch (e: Exception) {

                   }
                    if (photoFile != null) {
                        val photoUri = FileProvider.getUriForFile(this@AddNotesActivity,
                                BuildConfig.APPLICATION_ID + ".provider",
                                photoFile)
                        imageLocation = photoFile
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        dialog.hide()
                        startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                    }
                }
            }
        })
        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View?) {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.hide()
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG" + timeStamp + "_"
        val StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", StorageDir)
    }


    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.buttonSubmit)
        imageViewAdd = findViewById(R.id.imageViewAdd)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_CAMERA->{
                    picturePath = imageLocation.path.toString()
                    Glide.with(this).load(imageLocation.absoluteFile).into(imageViewAdd)
                }
                REQUEST_CODE_GALLERY->{
                    val selectedImage = data?.data
                    picturePath = selectedImage?.path.toString()
                    Glide.with(this).load(selectedImage?.path).into(imageViewAdd)
                }
            }
        }
    }
}