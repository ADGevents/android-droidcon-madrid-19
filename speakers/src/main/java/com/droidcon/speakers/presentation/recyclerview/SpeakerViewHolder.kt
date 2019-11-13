package com.droidcon.speakers.presentation.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.SpeakerRowModel

class SpeakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(speaker: SpeakerRowModel) {
        itemView.findViewById<TextView>(R.id.speakerTitle).text = speaker.title
        itemView.findViewById<TextView>(R.id.speakerSubtitle).text = speaker.subtitle
        val avatarView = itemView.findViewById<ImageView>(R.id.speakerAvatar)
        Glide.with(itemView)
            .load(speaker.avatar.rawUrl)
            .transform(CircleCrop())
            .into(avatarView)
    }
}