package h.k.videoeditor.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import h.k.videoeditor.databinding.ActivityMainBinding

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 234) {
//            val videoPath = data?.data?.let { PathUtil.getPath(requireContext(), it) }
            val videoPath = data?.data
            Log.e("mymypath", videoPath.toString())
            startActivity(Intent(this,EditorActivity::class.java).putExtra("link",data?.data.toString()))

        }
    }
}