package h.k.videoeditor.ui.activities.business_logic

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import VideoHandle.OnEditorListener
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import h.k.videoeditor.R
import h.k.videoeditor.ui.activities.ui.activities.EditorActivity
import h.k.videoeditor.ui.activities.ui.activities.EditorActivity.Companion.binding
import h.k.videoeditor.ui.activities.ui.activities.EditorActivity.Companion.videoLink
import java.io.File
import java.util.*

class Utils {
    companion object {
        lateinit var alert: Dialog

        fun timeLapsLab(path: File, cntxt: Activity) {
            val parentDest = path
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_timeLapse.mp4")
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()
            EpEditor.execCmd("-i $parentDest -filter_complex setpts=PTS/6[v] -map [v] -b:v 2097k -r 60 -vcodec mpeg4 $childDest",
                EpVideo(path.absolutePath).clipDuration.toLong(),
                object : OnEditorListener {
                    override fun onSuccess() {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(cntxt, "Time Lapsed video saved", Toast.LENGTH_SHORT)
                                .show()
                            parentDest.delete()
                            videoLink = childDest.absolutePath
                            alert.dismiss()
                            binding.videoView.setVideoPath(videoLink)
                            binding.videoView.start()
                        }

                        path.delete()
                    }

                    override fun onFailure() {
                        Handler(Looper.getMainLooper()).post {
                            alert.dismiss()
                            childDest.delete()
                        }
                    }

                    override fun onProgress(progress: Float) {
                        Handler(Looper.getMainLooper()).post {
                            processing.text = "Processing: ${String.format("%.2f", progress)}%"
                        }
                    }

                }
            )
        }

        fun slowMoLab(path: File, cntxt: Activity) {
            /////
            val parentDest = path
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_slowMo.mp4")
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()
            EpEditor.execCmd("-i $parentDest -vf setpts=4*PTS $childDest",
                EpVideo(path.absolutePath).clipDuration.toLong(),
                object : OnEditorListener {
                    override fun onSuccess() {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(cntxt, "Time Lapsed video saved", Toast.LENGTH_SHORT)
                                .show()
                            parentDest.delete()
                            videoLink = childDest.absolutePath
                            alert.dismiss()
                            binding.videoView.setVideoPath(videoLink)
                            binding.videoView.start()
                        }

                        path.delete()
                    }

                    override fun onFailure() {
                        Handler(Looper.getMainLooper()).post {
                            alert.dismiss()
                            childDest.delete()
                        }
                    }

                    override fun onProgress(progress: Float) {
                        Handler(Looper.getMainLooper()).post {
                            processing.text = "Processing: ${String.format("%.2f", progress)}%"
                        }
                    }

                }
            )

            ////


        }

        fun volDownMoLab(path: File, cntxt: Activity) {
            /////
            val parentDest = path
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_vol_down.mp4")
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()
            EpEditor.execCmd("-i $parentDest -filter:a volume=0.1 $childDest",
                EpVideo(path.absolutePath).clipDuration.toLong(),
                object : OnEditorListener {
                    override fun onSuccess() {
                        Log.d("logkey","Success")

                        Handler(Looper.getMainLooper()).post {
                            parentDest.delete()
                            videoLink = childDest.absolutePath
                            alert.dismiss()
                            binding.videoView.setVideoPath(videoLink)
                            binding.videoView.start()
                        }

                        path.delete()
                    }

                    override fun onFailure() {
                        Log.d("logkey","Failed")
                        Handler(Looper.getMainLooper()).post {
                            alert.dismiss()
                            childDest.delete()
                        }
                    }

                    override fun onProgress(progress: Float) {
                        Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                        Handler(Looper.getMainLooper()).post {
                            processing.text = "Processing: ${String.format("%.2f", progress)}%"
                        }
                    }

                }
            )

            ////


        }
        fun volUpMoLab(path: File, cntxt: Activity) {
            /////
            val parentDest = path
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_vol_up.mp4")
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()
            EpEditor.execCmd("-i $parentDest -filter:a volume=1.9 $childDest",
                EpVideo(path.absolutePath).clipDuration.toLong(),
                object : OnEditorListener {
                    override fun onSuccess() {
                        Log.d("logkey","Success")

                        Handler(Looper.getMainLooper()).post {
                            parentDest.delete()
                            videoLink = childDest.absolutePath
                            alert.dismiss()
                            binding.videoView.setVideoPath(videoLink)
                            binding.videoView.start()
                        }

                        path.delete()
                    }

                    override fun onFailure() {
                        Log.d("logkey","Failed")
                        Handler(Looper.getMainLooper()).post {
                            alert.dismiss()
                            childDest.delete()
                        }
                    }

                    override fun onProgress(progress: Float) {
                        Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                        Handler(Looper.getMainLooper()).post {
                            processing.text = "Processing: ${String.format("%.2f", progress)}%"
                        }
                    }

                }
            )

            ////


        }

        fun rotationMoLab(path: File, cntxt: Activity) {
            /////
            val parentDest = path

            val epVideo=EpVideo(videoLink)
            epVideo.rotation(90,true)
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_rotate.mp4")
            val outPutOptions=EpEditor.OutputOption(childDest.absolutePath)
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()

            EpEditor.exec(epVideo,outPutOptions,object:OnEditorListener{
                override fun onSuccess() {
                    Log.d("logkey","Success")

                    Handler(Looper.getMainLooper()).post {
                        parentDest.delete()
                        videoLink = childDest.absolutePath
                        alert.dismiss()
                        binding.videoView.setVideoPath(videoLink)
                        binding.videoView.start()
                    }

                    path.delete()
                }

                override fun onFailure() {
                    Log.d("logkey","Failed")
                    Handler(Looper.getMainLooper()).post {
                        alert.dismiss()
                        childDest.delete()
                    }
                }

                override fun onProgress(progress: Float) {
                    Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                    Handler(Looper.getMainLooper()).post {
                        processing.text = "Processing: ${String.format("%.2f", progress)}%"
                    }
                }
            })

            ////


        }
        fun cuttingMoLab(mStart:Float,mDuration:Float,path: File, cntxt: Activity) {
            /////
            val parentDest = path

            val epVideo=EpVideo(videoLink)
            epVideo.clip(mStart,mDuration)
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_rotate.mp4")
            val outPutOptions=EpEditor.OutputOption(childDest.absolutePath)
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()

            EpEditor.exec(epVideo,outPutOptions,object:OnEditorListener{
                override fun onSuccess() {
                    Log.d("logkey","Success")

                    Handler(Looper.getMainLooper()).post {
                        parentDest.delete()
                        videoLink = childDest.absolutePath
                        alert.dismiss()
                        binding.videoView.setVideoPath(videoLink)
                        binding.videoView.start()
                    }

                    path.delete()
                }

                override fun onFailure() {
                    Log.d("logkey","Failed")
                    Handler(Looper.getMainLooper()).post {
                        alert.dismiss()
                        childDest.delete()
                    }
                }

                override fun onProgress(progress: Float) {
                    Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                    Handler(Looper.getMainLooper()).post {
                        processing.text = "Processing: ${String.format("%.2f", progress)}%"
                    }
                }
            })

            ////


        }
        fun cropingMoLab(widthV:Float?,heightV:Float?,xV:Float?,yV:Float?,path: File, cntxt: Activity)
        {
            /////
            val parentDest = path

            val epVideo=EpVideo(videoLink)
            if (widthV != null&&
                heightV != null&&
                xV != null&&
                yV != null) {
                epVideo.crop(widthV,heightV,xV,yV)
            }
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_crop.mp4")
            val outPutOptions=EpEditor.OutputOption(childDest.absolutePath)
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()

            EpEditor.exec(epVideo,outPutOptions,object:OnEditorListener{
                override fun onSuccess() {
                    Log.d("logkey","Success")

                    Handler(Looper.getMainLooper()).post {
                        parentDest.delete()
                        videoLink = childDest.absolutePath
                        alert.dismiss()
                        binding.videoView.setVideoPath(videoLink)
                        binding.videoView.start()
                    }

                    path.delete()
                }

                override fun onFailure() {
                    Log.d("logkey","Failed")
                    Handler(Looper.getMainLooper()).post {
                        alert.dismiss()
                        childDest.delete()
                    }
                }

                override fun onProgress(progress: Float) {
                    Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                    Handler(Looper.getMainLooper()).post {
                        processing.text = "Processing: ${String.format("%.2f", progress)}%"
                    }
                }
            })

            ////


        }
        fun splitttingMoLab(path: File, cntxt: Activity,duration:Int) {
            /////
            val parentDest = path

            val actualDurtion=(duration/1000).toFloat()
            val epVideo=EpVideo(videoLink)
            Log.d("logkey",(duration).toString())
            epVideo.clip(0f,actualDurtion/2)
            val epVideo2=EpVideo(videoLink)
            epVideo2.clip(actualDurtion/2,actualDurtion)
            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            val childDest1 = File(childDest, UUID.randomUUID().toString() + "_split1.mp4")
            val childDest2 = File(childDest, UUID.randomUUID().toString() + "_split2.mp4")
            val outPutOptions=EpEditor.OutputOption(childDest1.absolutePath)
            val outPutOptions2=EpEditor.OutputOption(childDest2.absolutePath)
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()

            EpEditor.exec(epVideo,outPutOptions,object:OnEditorListener{
                override fun onSuccess() {
                    Log.d("logkey","Success")
                    Handler(Looper.getMainLooper()).post {
                        EpEditor.exec(epVideo2,outPutOptions2,object:OnEditorListener{
                            override fun onSuccess() {
                                Log.d("logkey","Success")

                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(
                                        cntxt,
                                        "Both Files saved at ${File(
                                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                                            "VideoEditor"
                                        )}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    parentDest.delete()
                                    videoLink = childDest2.absolutePath
                                    alert.dismiss()
                                    binding.videoView.setVideoPath(videoLink)
                                    binding.videoView.start()
                                }

                                path.delete()
                            }

                            override fun onFailure() {
                                Log.d("logkey","Failed")
                                Handler(Looper.getMainLooper()).post {
                                    alert.dismiss()
                                    childDest2.delete()
                                }
                            }

                            override fun onProgress(progress: Float) {
                                Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                                Handler(Looper.getMainLooper()).post {
                                    processing.text = "Processing: ${String.format("%.2f", progress)}%"
                                }
                            }
                        })

                    }
                }

                override fun onFailure() {
                    Log.d("logkey","Failed")
                    Handler(Looper.getMainLooper()).post {
                        alert.dismiss()
                        childDest1.delete()
                    }
                }

                override fun onProgress(progress: Float) {
                    Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                    Handler(Looper.getMainLooper()).post {
                        processing.text = "Processing: ${String.format("%.2f", progress/2)}%"
                    }
                }
            })

            ////
        }


        fun mergeTwoFiles(path1:File,path2:File,cntxt:Activity){
            val epvid1=EpVideo(path1.absolutePath)
            val epvid2=EpVideo(path2.absolutePath)

            val lst= mutableListOf<EpVideo>(epvid1,epvid2)

            var childDest = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "VideoEditor"
            )
            if (!childDest.exists())
                childDest.mkdir()
            childDest = File(childDest, UUID.randomUUID().toString() + "_split1.mp4")
            val outPutOptions=EpEditor.OutputOption(childDest.absolutePath)
            alert = Dialog(cntxt)
            val customLayout: View =
                cntxt.layoutInflater.inflate(R.layout.dailog_processing, null)
            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alert.setCancelable(false)
            alert.setCanceledOnTouchOutside(false)
            alert.setContentView(customLayout)
            val processing: TextView = customLayout.findViewById<TextView>(R.id.process)
            alert.show()

            EpEditor.merge(lst,outPutOptions,object :OnEditorListener{
                override fun onSuccess() {
                    Log.d("logkey","Success")

                    Handler(Looper.getMainLooper()).post {
                        videoLink = childDest.absolutePath
                        alert.dismiss()
                        binding.videoView.setVideoPath(videoLink)
                        binding.videoView.start()
                    }

                }

                override fun onFailure() {
                    Log.d("logkey","Failed")
                    Handler(Looper.getMainLooper()).post {
                        alert.dismiss()
                        childDest.delete()
                    }
                }

                override fun onProgress(progress: Float) {
                    Log.d("logkey","Processing: ${String.format("%.2f", progress)}%")
                    Handler(Looper.getMainLooper()).post {
                        processing.text = "Processing: ${String.format("%.2f", progress)}%"
                    }
                }
            })
        }

    }
}