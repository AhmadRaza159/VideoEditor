package h.k.videoeditor.ui.activities.interfaces

import java.io.File

interface FileInterface {
    fun onClick(model:File)
    fun onShare(model:File)
    fun onDelete(model:File)
}