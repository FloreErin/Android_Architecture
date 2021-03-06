package com.example.architecture_tutorial.adapter.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.architecture_tutorial.Model.Item
import com.example.architecture_tutorial.R
import com.example.architecture_tutorial.adapter.Presenter.ItemViewHolderPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemAdapter(val context : Context, var items : List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var itemviewHolderPresenter = ItemViewHolderPresenter()
        private var btn_text_message = itemView.findViewById<Button>(R.id.btn_text)

        fun bind(item : Item){
            btn_text_message.text = "Input Text (${item.id}) : ${item.message}"

            btn_text_message.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(context, R.style.CustomMaterialDialog)
                builder.run {
                    setTitle("삭제 메세지")
                    setMessage("해당 메세지를 삭제하겠습니까? (Item id = ${item.id})")
                    setPositiveButton("확인") { dialog, width ->
                        items = itemviewHolderPresenter.deleteDB(context,  item.id, item.message)
                        notifyDataSetChanged()
                        Toast.makeText(context, "삭제가 완료되었습니다", Toast.LENGTH_LONG).show()
                    }
                    setNegativeButton("취소") { dialog, width -> }
                    show()
                }
            }
        }
    }

}