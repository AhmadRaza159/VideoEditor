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
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Add"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.ic_baseline_add_24))
            }
            EditOptions.AUDIO->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Audio"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_audio))
            }
            EditOptions.SPEED->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Speed"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_speed))
            }
            EditOptions.VOLUME->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Volume"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_volume))
            }
            EditOptions.ROTATION->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Rotation"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_rotation))
            }

            EditOptions.CUT->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Cut"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_cut))
            }
            EditOptions.CROP->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Crop"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_crop))
            }
            EditOptions.SPLIT->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Split"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_split))
            }
            EditOptions.REPLACE->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Replace"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_exchange))
            }
            EditOptions.DUPLICATE->{
                holder.itemView.findViewById<TextView>(R.id.textView).text = "Duplicate"
                holder.itemView.findViewById<ImageView>(R.id.option_image)
                    .setImageDrawable(cntx.resources.getDrawable(R.drawable.png_duplicate))
            }

        }

            holder.itemView.setOnClickListener {
                clickInterface.onClick(myList[position])
            }

        }



    override fun getItemCount(): Int {
//        Toast.makeText(cntx,myList.size.toString(),Toast.LENGTH_SHORT).show()
        return myList.size
    }
}