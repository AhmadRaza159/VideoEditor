package h.k.videoeditor.ui.activities.ui.activities

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import h.k.videoeditor.databinding.ActivityMainBinding
import h.k.videoeditor.ui.activities.business_logic.PathUtil
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
            intent.type = "video/*"
            startActivityForResult(
                intent,
                234
            )
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
}