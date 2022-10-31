package h.k.videoeditor.ui.activities.ui.activities

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import VideoHandle.OnEditorListener
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import h.k.videoeditor.R
import h.k.videoeditor.databinding.ActivityEditorBinding
import h.k.videoeditor.ui.activities.adapters.OptionsAdapter
import h.k.videoeditor.ui.activities.business_logic.EditOptions
import h.k.videoeditor.ui.activities.business_logic.PathUtil
import h.k.videoeditor.ui.activities.business_logic.Utils
import h.k.videoeditor.ui.activities.interfaces.OptionClickInterface
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.UUID

class EditorActivity : AppCompatActivity() {

    companion object{
        var videoLink: String? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityEditorBinding
    }
    private var isDelete=true
    lateinit var handler: Handler
    val GALLERY_REQUEST_CODE=12
    lateinit var alert: Dialog
    private val CROPPING_CODE=332
    private var arrayData = arrayListOf<Int>(
        EditOptions.CANVAS,
        EditOptions.AUDIO,
        EditOptions.SPEED,
        EditOptions.VOLUME,
        EditOptions.ROTATION,
        EditOptions.CUT,
        EditOptions.CROP,
        EditOptions.SPLIT,
        EditOptions.REPLACE,
        EditOptions.DUPLICATE

    )

    override fun onBackPressed() {
        if (isDelete){
            File(videoLink).delete()
        }
        super.onBackPressed()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoLink = intent.getStringExtra("link")
        binding.videoView.setVideoPath(videoLink)
        binding.videoView.start()

        btnClickListeners()
        factory()
        binding.btnMenu.setOnClickListener{
            onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            isDelete=false
            Toast.makeText(this,"File saved at ${videoLink}",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun factory() {
        handler = Handler()
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = false
            mp.setOnVideoSizeChangedListener { mp, width, height ->
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        try{
                            var division = mp.currentPosition.toFloat() / mp.duration.toFloat()
                            binding.recordProgress.progress = (division * 100F).toInt()
                            handler.postDelayed(this, 1000)
                        }catch (e:java.lang.Exception){
                            e.printStackTrace()
                        }


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
                    when(model){
                        EditOptions.CANVAS->{
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                            intent.type = "video/*"
                            startActivityForResult(
                                intent,
                                235
                            )
                        }
                        EditOptions.AUDIO->{
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(
                                Intent.createChooser(intent, "Select Audio"),
                                GALLERY_REQUEST_CODE
                            )
                        }
                        EditOptions.SPEED->{
                            val alert = Dialog(this@EditorActivity)
                            val customLayout: View =
                                layoutInflater.inflate(R.layout.dailog_audio_opts, null)
                            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            alert.setCancelable(true)
                            alert.setCanceledOnTouchOutside(true)
                            alert.setContentView(customLayout)

                            val btnSlow=customLayout.findViewById<ConstraintLayout>(R.id.btn_slow)
                            val btnFast=customLayout.findViewById<ConstraintLayout>(R.id.btn_fast)

                            btnSlow.setOnClickListener {
                                alert.dismiss()
                                Utils.slowMoLab(File(videoLink),this@EditorActivity)
                            }

                            btnFast.setOnClickListener {
                                alert.dismiss()
                                Utils.timeLapsLab(File(videoLink),this@EditorActivity)

                            }

                            alert.show()

                        }
                        EditOptions.VOLUME->{
                            val alert = Dialog(this@EditorActivity)
                            val customLayout: View =
                                layoutInflater.inflate(R.layout.dailog_volume, null)
                            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            alert.setCancelable(true)
                            alert.setCanceledOnTouchOutside(true)
                            alert.setContentView(customLayout)

                            val btnVolDown=customLayout.findViewById<ConstraintLayout>(R.id.btn_vol_down)
                            val btnVolUp=customLayout.findViewById<ConstraintLayout>(R.id.btn_vol_up)

                            btnVolDown.setOnClickListener {
                                alert.dismiss()
                                Utils.volDownMoLab(File(videoLink),this@EditorActivity)
                            }

                            btnVolUp.setOnClickListener {
                                alert.dismiss()
                                Utils.volUpMoLab(File(videoLink),this@EditorActivity)

                            }

                            alert.show()
                        }
                        EditOptions.ROTATION->{
                            Utils.rotationMoLab(File(videoLink),this@EditorActivity)

                        }

                        EditOptions.CUT->{
                            val alert = Dialog(this@EditorActivity)
                            val customLayout: View =
                                layoutInflater.inflate(R.layout.dailog_cutting, null)
                            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            alert.setCancelable(true)
                            alert.setCanceledOnTouchOutside(true)
                            alert.setContentView(customLayout)

                            val btnCut=customLayout.findViewById<ConstraintLayout>(R.id.btn_cut)
                            val txtStart=customLayout.findViewById<EditText>(R.id.editText)
                            val txtDuration=customLayout.findViewById<EditText>(R.id.editText1)

                            btnCut.setOnClickListener {
                                if (txtStart.text.toString().isNotEmpty()&&
                                    txtDuration.text.toString().isNotEmpty()){
                                    alert.dismiss()
                                    Utils.cuttingMoLab(
                                        txtStart.text.toString().toFloat(),
                                        txtDuration.text.toString().toFloat(),
                                        File(videoLink),this@EditorActivity)
                                }
                            }

                            alert.show()


                        }
                        EditOptions.CROP->{
                            startActivityForResult(Intent(this@EditorActivity,CropActivity::class.java).putExtra("link",
                                videoLink),CROPPING_CODE)
                          }
                        EditOptions.SPLIT->{

                            Utils.splitttingMoLab(File(videoLink),this@EditorActivity,binding.videoView.duration)
                          }
                        EditOptions.REPLACE->{
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI)
                            intent.type = "video/*"
                            startActivityForResult(
                                intent,
                                236
                            )
                          }
                        EditOptions.DUPLICATE->{
                            Utils.mergeTwoFiles(File(videoLink),File(videoLink),this@EditorActivity)
                          }

                    }

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


    private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            contentResolver?.query(contentUri, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            val audioPath = data?.data?.let { getRealPathFromURI(this, it) }
            var resultPath=File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),"VideoEditor")
            if (!resultPath.exists())
                resultPath.mkdir()
            resultPath= File(resultPath,UUID.randomUUID().toString()+"audio.mp4")
            Log.e("mypath", "videoLik: $videoLink")
            Log.e("mypath", "audio: $audioPath")
            Log.e("mypath", "result: ${resultPath.absolutePath}")
            alert = Dialog(this)
            val customLayout: View =
                layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()
            EpEditor.music(videoLink,audioPath,resultPath.absolutePath,0f,1f,object :OnEditorListener{
                override fun onSuccess() {
                    Log.e("mypath", "onSuccess")
                    runOnUiThread {
                        File(videoLink).delete()
                        videoLink=resultPath.absolutePath
                        alert.dismiss()
                        binding.videoView.setVideoPath(videoLink)
                        binding.videoView.start()

                    }
                }

                override fun onFailure() {
                    Log.e("mypath", "onFailure")
                    runOnUiThread {
                        alert.dismiss()
                        resultPath.delete()
                    }

                }

                override fun onProgress(progress: Float) {
                    Log.e("mypath", "progress: $progress")
                    runOnUiThread {
                        processing.text="Processing: ${String.format("%.2f",progress)}%"
                    }

                }

            })





        }
        if (resultCode == RESULT_OK && requestCode == CROPPING_CODE){
            Log.d("logkey",data?.getIntExtra("w",0).toString())
            Log.d("logkey",data?.getIntExtra("h",0).toString())
            Log.d("logkey",data?.getIntExtra("x",0).toString())
            Log.d("logkey",data?.getIntExtra("y",0).toString())
            Utils.cropingMoLab(data?.getIntExtra("w",0)?.toFloat(),
                data?.getIntExtra("h",0)?.toFloat(),
                data?.getIntExtra("x",0)?.toFloat(),
                data?.getIntExtra("y",0)?.toFloat(),
                File(videoLink),this@EditorActivity)

        }
        if (resultCode == RESULT_OK && requestCode == 235){
            val source= data?.data?.let { PathUtil.getPath(this, it) }
            Utils.mergeTwoFiles(File(videoLink),File(source),this@EditorActivity)

        }

        if (resultCode == RESULT_OK && requestCode == 236){
            val source= data?.data?.let { PathUtil.getPath(this, it) }
            File(videoLink).delete()
            var resultPath=
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),"VideoEditor")
            if (!resultPath.exists())
                resultPath.mkdir()
            resultPath= File(resultPath, UUID.randomUUID().toString()+"audio.mp4")
            Files.copy(File(source).toPath(),resultPath.toPath(), StandardCopyOption.REPLACE_EXISTING)
            videoLink=resultPath.absolutePath
            binding.videoView.setVideoPath(videoLink)
            binding.videoView.start()

        }
    }
}