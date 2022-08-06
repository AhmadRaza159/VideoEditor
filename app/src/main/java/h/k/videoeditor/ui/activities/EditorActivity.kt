package h.k.videoeditor.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import h.k.videoeditor.R
import h.k.videoeditor.databinding.ActivityEditorBinding

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private var videoLink:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoLink=intent.getStringExtra("link")

        binding.videoView.setVideoPath(videoLink)
        binding.videoView.start()
    }
}