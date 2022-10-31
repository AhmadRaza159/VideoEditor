package h.k.videoeditor.ui.activities.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import h.k.videoeditor.BuildConfig
import h.k.videoeditor.databinding.ActivitySavedVideoBinding
import h.k.videoeditor.ui.activities.adapters.VideosAdapter
import h.k.videoeditor.ui.activities.interfaces.FileInterface
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class SavedVideoActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySavedVideoBinding
    lateinit var adapter:VideosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySavedVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMenu.setOnClickListener{
            onBackPressed()
        }
        binding.reccycler.layoutManager=GridLayoutManager(this,2)
        adapter=VideosAdapter(getFiles(),this,object :FileInterface{
            override fun onClick(model: File) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(model.absolutePath))
                intent.setDataAndType(Uri.parse(model.absolutePath), "video/mp4")
                startActivity(intent)
            }

            override fun onShare(model: File) {
                val intent = Intent("android.intent.action.SEND")
                intent.type = "video/*"
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "This is edited by:-\nhttps://play.google.com/store/apps/details?id=" + applicationContext?.packageName
                )
                intent.putExtra(
                    "android.intent.extra.STREAM",
                    Objects.requireNonNull(this@SavedVideoActivity)?.let {
                        FileProvider.getUriForFile(
                            it,
                            BuildConfig.APPLICATION_ID + ".provider", model
                        )
                    }
                )
                try {
                    startActivity(
                        Intent.createChooser(
                            intent,
                            "Share File "
                        )
                    )
                } catch (unused: ActivityNotFoundException) {
                    Toast.makeText(
                        this@SavedVideoActivity,
                        "No app to read File",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onDelete(model: File) {
                model.delete()
                adapter.notifyMe(getFiles())
            }

        })

        binding.reccycler.adapter=adapter

    }

    private fun getFiles(): ArrayList<File> {
        val myList=ArrayList<File>()
        var dest = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
            "VideoEditor"
        )
        if (!dest.exists())
            dest.mkdir()
        for (i in dest.listFiles()){
            myList.add(i)
        }
        myList.reverse()
        return myList
    }


}