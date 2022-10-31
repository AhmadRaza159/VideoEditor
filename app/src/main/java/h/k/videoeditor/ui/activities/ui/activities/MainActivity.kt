package h.k.videoeditor.ui.activities.ui.activities

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import h.k.videoeditor.R
import h.k.videoeditor.databinding.ActivityMainBinding
import h.k.videoeditor.ui.activities.business_logic.PathUtil
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var storagePermission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    @RequiresApi(Build.VERSION_CODES.M)
    fun reqStroragePermission(code:Int) {
        Log.e("logKey", "request permission")
        requestPermissions(storagePermission, code)
    }
    fun checkStoragePermission(): Boolean {
        var a = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        var b = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return a && b
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkStoragePermission()) {
            if(requestCode==110){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                intent.type = "video/*"
                startActivityForResult(
                    intent,
                    234
                )            }
            else{
                startActivity(Intent(this,SavedVideoActivity::class.java))

            }
        }

    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        val customLayout: View = getLayoutInflater().inflate(R.layout.dalog_exit, null)
        alertDialog.setView(customLayout)
        val alert = alertDialog.create()
        alert.setCancelable(false)
        alert.setCanceledOnTouchOutside(true)
        val yesBtn: TextView = customLayout.findViewById(R.id.btn_yes)
        val noBtn: TextView = customLayout.findViewById(R.id.btn_no)

        noBtn.setOnClickListener {
            alert.dismiss()
        }
        yesBtn.setOnClickListener {
            finishAffinity()
        }

        alert.show()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animateNavigationDrawer()
        menuPoping()
        binding.btnGallery.setOnClickListener {
            if (checkStoragePermission()){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                intent.type = "video/*"
                startActivityForResult(
                    intent,
                    234
                )
            }
            else{
                reqStroragePermission(110)
            }

        }
        binding.btnSaved.setOnClickListener {
            if (checkStoragePermission()){
            startActivity(Intent(this,SavedVideoActivity::class.java))
            }
            else{
                reqStroragePermission(111)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 234) {
//            val videoPath = data?.data?.let { PathUtil.getPath(requireContext(), it) }
            val source= data?.data?.let { PathUtil.getPath(this, it) }
            var resultPath=
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),"VideoEditor")
            if (!resultPath.exists())
                resultPath.mkdir()
            resultPath= File(resultPath, UUID.randomUUID().toString()+"audio.mp4")
            Files.copy(File(source).toPath(),resultPath.toPath(),StandardCopyOption.REPLACE_EXISTING)
            startActivity(Intent(this, EditorActivity::class.java).putExtra("link",resultPath.absolutePath))

        }
    }


    private fun animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset = slideOffset * (1 - 0.7f)
                val offsetScale = 1 - diffScaledOffset
                binding.mainMen.scaleX = offsetScale
                binding.mainMen.scaleY = offsetScale

                // Translate the View, accounting for the scaled width
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff =binding.mainMen.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                binding.mainMen.translationX = xTranslation
            }
        })
    }
    private fun menuPoping() {
        binding.btnMenu.setOnClickListener {
//            Toast.makeText(this,"TTT",Toast.LENGTH_SHORT).show()
            if (binding.drawerLayout.isDrawerVisible(
                    GravityCompat.START
                )
            ) binding.drawerLayout.closeDrawer(GravityCompat.START) else binding.drawerLayout.openDrawer(
                GravityCompat.START
            )
            /////////////////////////////////
            binding.navView.bringToFront()

            binding.navView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        binding.drawerLayout.closeDrawers()
                        true
                    }
                    R.id.more_apps -> {
                        val uri =
                            Uri.parse("https://play.google.com/store/apps/developer?id=Westminster+Pro+Apps")
                        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(
                            Intent.FLAG_ACTIVITY_NO_HISTORY or
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        )
                        try {
                            startActivity(goToMarket)
                        } catch (e: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/developer?id=Westminster+Pro+Apps")
                                )
                            )
                        }
                        binding.drawerLayout.closeDrawers()
                        true
                    }
                    R.id.share -> {
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Hey check out this app at: https://play.google.com/store/apps/details?id=" + applicationContext.packageName
                        )
                        sendIntent.type = "text/plain"
                        startActivity(sendIntent)
                        binding.drawerLayout.closeDrawers()
                        true
                    }
                    R.id.rate -> {
                        val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
                        val goToMarket = Intent(Intent.ACTION_VIEW, uri)

                        goToMarket.addFlags(
                            Intent.FLAG_ACTIVITY_NO_HISTORY or
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        )
                        try {
                            startActivity(goToMarket)
                        } catch (e: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                                )
                            )
                        }
                        binding.drawerLayout.closeDrawers()
                        true
                    }
                    R.id.policy -> {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                        startActivity(browserIntent)
                        true
                    }
                    R.id.exit->{
                        binding.drawerLayout.closeDrawers()
                       onBackPressed()
                        true
                    }

                    else -> {
                        false
                    }
                }

            }
        }
    }



}