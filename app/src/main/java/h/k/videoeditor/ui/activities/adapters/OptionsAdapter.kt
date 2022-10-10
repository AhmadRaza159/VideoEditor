package h.k.videoeditor.ui.activities.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import h.k.videoeditor.R
import h.k.videoeditor.ui.activities.business_logic.EditOptions
import h.k.videoeditor.ui.activities.interfaces.OptionClickInterface

class OptionsAdapter(
    var myList: ArrayList<Int>,
    var cntx: Context,
    val clickInterface: OptionClickInterface
) :
    RecyclerView.Adapter<OptionsAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        return ViewHolderClass( LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        when(myList[position]){
            EditOptions.CANVAS->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Canvas"
            }
            EditOptions.AUDIO->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Audio"
            }
            EditOptions.SPEED->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Speed"
            }
            EditOptions.VOLUME->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Volume"
            }
            EditOptions.ROTATION->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Rotation"
            }
            EditOptions.FILTER->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Filter"
            }
            EditOptions.CUT->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Cut"
            }
            EditOptions.CROP->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Crop"
            }
            EditOptions.SPLIT->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Split"
            }
            EditOptions.REPLACE->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Replace"
            }
            EditOptions.DUPLICATE->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Duplicate"
            }
            EditOptions.FREEZ->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Freez"
            }
            EditOptions.TEXT->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Text"
            }
            EditOptions.STICKER->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Sticker"
            }
            EditOptions.HISTORY->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "History"
            }
            EditOptions.UNDO->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Undo"
            }
            EditOptions.REDO->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Redo"
            }
        }
            holder.itemView.findViewById<ImageView>(R.id.imageView).setImageDrawable(cntx.resources
                .getDrawable(R.drawable.ic_launcher_background))
            holder.itemView.setOnClickListener {
                clickInterface.onClick(myList[position])
            }

        }



    override fun getItemCount(): Int {
//        Toast.makeText(cntx,myList.size.toString(),Toast.LENGTH_SHORT).show()
        return myList.size
    }
}