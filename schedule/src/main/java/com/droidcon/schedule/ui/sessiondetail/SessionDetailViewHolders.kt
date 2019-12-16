package com.droidcon.schedule.ui.sessiondetail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.schedule.R

sealed class SessionDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class SubtitleViewHolder(itemView: View) : SessionDetailViewHolder(itemView) {

    fun bind(row: SessionDetailRow.Subtitle) {
        itemView.findViewById<TextView>(R.id.sessionDetailDuration).text = row.duration
        itemView.findViewById<TextView>(R.id.sessionDetailRoom).text = itemView.context.getString(R.string.session_detail_room, row.room)
    }
}

class DescriptionViewHolder(itemView: View) : SessionDetailViewHolder(itemView) {

    fun bind(row: SessionDetailRow.Description) {
        itemView.findViewById<TextView>(R.id.sessionDetailDescription).text = row.description
    }
}

class SpeakersLabelViewHolder(itemView: View) : SessionDetailViewHolder(itemView)

class SpeakerViewHolder(itemView: View) : SessionDetailViewHolder(itemView) {

    fun bind(row: SessionDetailRow.Speaker) {
        itemView.findViewById<TextView>(R.id.speakerName).text = row.name
        itemView.findViewById<TextView>(R.id.speakerDescription).text = row.description
        val speakerAvatar = itemView.findViewById<ImageView>(R.id.speakerAvatar)
        Glide.with(itemView)
            .load(row.avatar)
            .placeholder(R.drawable.ic_default_avatar)
            .transform(CircleCrop())
            .into(speakerAvatar)
        itemView.setOnClickListener {
            row.onSpeakerClicked(row.id)
        }
    }
}

