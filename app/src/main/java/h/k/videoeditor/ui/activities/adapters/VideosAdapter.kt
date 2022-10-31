package h.k.videoeditor.ui.activities.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import h.k.videoeditor.R
import h.k.videoeditor.ui.activities.business_logic.EditOptions
import h.k.videoeditor.ui.activities.interfaces.FileInterface
import h.k.videoeditor.ui.activities.interfaces.OptionClickInterface
import java.io.File

class VideosAdapter (
    var myList: ArrayList<File>,
    var cntx: Context,
    val fileInterface: FileInterface
) :
    RecyclerView.Adapter<VideosAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        return ViewHolderClass( LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        Glide.with(cntx)
            .load(myList[position].absolutePath) // or URI/path
            .into(holder.itemView.findViewById<ImageView>(R.id.img_thumb))
        val popup =
            PopupMenu(cntx, holder.itemView.findViewById<ImageView>(R.id.btn_opt))
        popup.inflate(R.menu.opt_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.opt_share -> {
                    fileInterface.onShare(myList[position])
                    true
                }
                else -> {
                    fileInterface.onDelete(myList[position])
                    true
                }
            }
        }
        holder.itemView.findViewById<ImageView>(R.id.btn_opt).setOnClickListener {
            popup.show()
        }
        holder.itemView.setOnClickListener {
            fileInterface.onClick(myList[position])
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyMe(lst:ArrayList<File>){
        this.myList=lst
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
//        Toast.makeText(cntx,myList.size.toString(),Toast.LENGTH_SHORT).show()
        return myList.size
    }

}