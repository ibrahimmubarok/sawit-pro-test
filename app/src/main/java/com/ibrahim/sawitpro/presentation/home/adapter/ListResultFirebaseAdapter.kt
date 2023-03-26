package com.ibrahim.sawitpro.presentation.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.ibrahim.sawitpro.R
import com.ibrahim.sawitpro.data.model.request.FormDataRequest
import com.ibrahim.sawitpro.databinding.ItemTextResultBinding

class ListResultFirebaseAdapter(
    options: FirebaseRecyclerOptions<FormDataRequest>
) : FirebaseRecyclerAdapter<FormDataRequest, ListResultFirebaseAdapter.ListResultViewHolder>(options) {

    inner class ListResultViewHolder(
        private val binding: ItemTextResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormDataRequest) {
            binding.apply {
                val imgUri = Uri.parse(item.imageUri)
                imgPhoto.setImageURI(imgUri)
                tvDate.text = item.date
                tvResult.text = item.textResult
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListResultViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_text_result, parent, false)
        val binding = ItemTextResultBinding.bind(view)
        return ListResultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListResultViewHolder,
        position: Int,
        model: FormDataRequest
    ) {
        holder.bind(model)
    }
}