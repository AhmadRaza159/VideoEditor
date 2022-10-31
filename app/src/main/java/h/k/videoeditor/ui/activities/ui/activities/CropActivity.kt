package h.k.videoeditor.ui.activities.ui.activities

import android.annotation.SuppressLint
import android.graphics.Rect
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.bumptech.glide.Glide
import h.k.videoeditor.R
import h.k.videoeditor.databinding.ActivityCropBinding
import java.io.File

class CropActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCropBinding
    private val scalePoint=4
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boundingBox.setRect(Rect(0,0,400,400))
        binding.img.setVideoPath(intent.getStringExtra("link"))
        binding.img.start()
        binding.img.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.setVolume(0f, 0f)
            mp.isLooping = true
        })

        binding.btnBottom.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_MOVE) {
                binding.boundingBox.setRect(Rect(
                    binding.boundingBox.getRect()?.left?:0,
                    binding.boundingBox.getRect()?.top?.plus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.right?:0,
                    binding.boundingBox.getRect()?.bottom?.plus(scalePoint)?:0
                ))
                binding.boundingBox.invalidate()
            }
            return@setOnTouchListener true
        }

        binding.btnTop.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_MOVE) {
                binding.boundingBox.setRect(Rect(
                    binding.boundingBox.getRect()?.left?:0,
                    binding.boundingBox.getRect()?.top?.minus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.right?:0,
                    binding.boundingBox.getRect()?.bottom?.minus(scalePoint)?:0
                ))
                binding.boundingBox.invalidate()
            }
            return@setOnTouchListener true
        }

        binding.btnLeft.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_MOVE) {
                binding.boundingBox.setRect(Rect(
                    binding.boundingBox.getRect()?.left?.minus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.top?:0,
                    binding.boundingBox.getRect()?.right?.minus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.bottom?:0
                ))
                binding.boundingBox.invalidate()
            }
            return@setOnTouchListener true
        }

        binding.btnRight.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_MOVE) {
                binding.boundingBox.setRect(Rect(
                    binding.boundingBox.getRect()?.left?.plus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.top?:0,
                    binding.boundingBox.getRect()?.right?.plus(scalePoint)?:0,
                    binding.boundingBox.getRect()?.bottom?:0
                ))
                binding.boundingBox.invalidate()
            }
            return@setOnTouchListener true
        }


        binding.btnCrop.setOnClickListener {
            val intnt=intent
            intnt.putExtra("w",binding.boundingBox.getRect()?.width())
            intnt.putExtra("h",binding.boundingBox.getRect()?.height())
            intnt.putExtra("x",binding.boundingBox.getRect()?.left)
            intnt.putExtra("y",binding.boundingBox.getRect()?.top)
            setResult(RESULT_OK,intnt)
            finish()
        }

    }
}