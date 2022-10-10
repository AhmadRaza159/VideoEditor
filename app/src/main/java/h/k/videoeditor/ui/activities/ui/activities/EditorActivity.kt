package h.k.videoeditor.ui.activities.ui.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import h.k.videoeditor.R
import h.k.videoeditor.databinding.ActivityEditorBinding
import h.k.videoeditor.ui.activities.adapters.OptionsAdapter
import h.k.videoeditor.ui.activities.business_logic.EditOptions
import h.k.videoeditor.ui.activities.interfaces.OptionClickInterface

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private var videoLink: String? = null
    lateinit var handler: Handler
    private var arrayData = arrayListOf<Int>(
        EditOptions.CANVAS,
        EditOptions.AUDIO,
        EditOptions.SPEED,
        EditOptions.VOLUME,
        EditOptions.ROTATION,
        EditOptions.FILTER,
        EditOptions.CUT,
        EditOptions.CROP,
        EditOptions.SPLIT,
        EditOptions.REPLACE,
        EditOptions.DUPLICATE,
        EditOptions.FREEZ,
        EditOptions.TEXT,
        EditOptions.STICKER,
        EditOptions.HISTORY,
        EditOptions.UNDO,
        EditOptions.REDO

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoLink = intent.getStringExtra("link")
        binding.videoView.setVideoPath(videoLink)
        binding.videoView.start()

        btnClickListeners()
        factory()
    }

    private fun factory() {
        handler = Handler()
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = false
            mp.setOnVideoSizeChangedListener { mp, width, height ->
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        var division = mp.currentPosition.toFloat() / mp.duration.toFloat()
                        binding.recordProgress.progress = (division * 100F).toInt()
                        handler.postDelayed(this, 1000)

                    }
                }, 1000)


            }
        })
        binding.videoView.setOnCompletionListener {
            binding.videoPlayPauseTogler.setImageDrawable(resources.getDrawable(R.drawable.ic_round_play_arrow_24))
        }

        binding.recyclerviewOptions.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewOptions.adapter =
            OptionsAdapter(arrayData, this, object : OptionClickInterface {
                override fun onClick(model: Int) {
                    Toast.makeText(this@EditorActivity, model.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun btnClickListeners() {
        binding.videoPlayPauseTogler.setOnClickListener {
            if (binding.videoView.isPlaying) {
                binding.videoView.pause()
                binding.videoPlayPauseTogler.setImageDrawable(resources.getDrawable(R.drawable.ic_round_play_arrow_24))
            } else {
                binding.videoView.start()
                binding.videoPlayPauseTogler.setImageDrawable(resources.getDrawable(R.drawable.ic_round_pause_24))
            }
        }
    }
}