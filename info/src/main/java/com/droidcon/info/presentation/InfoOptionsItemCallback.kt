package com.droidcon.info.presentation

import androidx.recyclerview.widget.DiffUtil

object InfoOptionsItemCallback : DiffUtil.ItemCallback<InfoOption>() {

    override fun areItemsTheSame(oldItem: InfoOption, newItem: InfoOption): Boolean =
        when {
            oldItem is InfoOption.Row && newItem is InfoOption.Row -> oldItem.id == newItem.id
            oldItem is InfoOption.Map && newItem is InfoOption.Map -> true
            else -> false
        }

    override fun areContentsTheSame(oldItem: InfoOption, newItem: InfoOption): Boolean =
        when {
            oldItem is InfoOption.Row && newItem is InfoOption.Row -> oldItem == newItem
            oldItem is InfoOption.Map && newItem is InfoOption.Map -> true
            else -> false
        }
}