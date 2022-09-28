package com.serhii.stasiuk.buttontoaction.presentation.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.serhii.stasiuk.buttontoaction.R
import com.serhii.stasiuk.buttontoaction.databinding.ItemContactBinding
import com.serhii.stasiuk.buttontoaction.presentation.model.ContactAdapterItem
import com.serhii.stasiuk.buttontoaction.utils.extensions.getDrawableRes
import com.serhii.stasiuk.buttontoaction.utils.extensions.px
import com.serhii.stasiuk.buttontoaction.utils.extensions.setNamedAvatar
import com.serhii.stasiuk.buttontoaction.utils.recycler_view.DividerItemDecorator


class ContactsListAdapter(private val clickListener: ContactClickListener) :
    BaseListAdapter<ContactAdapterItem, BaseListAdapter.BaseViewHolder>(ITEMS_COMPARATOR) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.context.getDrawableRes(R.drawable.horizontal_divider_1dp)?.let {
            recyclerView.addItemDecoration(DividerItemDecorator(it, false))
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_contact
    override fun createViewHolder(view: View, viewType: Int) =
        ContactViewHolder(ItemContactBinding.bind(view))

    inner class ContactViewHolder(binding: ItemContactBinding) :
        BaseViewBindingHolder<ItemContactBinding>(binding) {

        override fun bind(pos: Int) {
            getItem(pos)?.apply {
                initAvatar(this)
                initFields(this)
                itemView.setOnClickListener {
                    getItem()?.let(clickListener::onContactClick)
                }
            }
        }

        override fun bind(pos: Int, payloads: MutableList<Any>) {
            if (payloads.isNotEmpty()) bind(pos)
        }

        private fun initAvatar(item: ContactAdapterItem) {
            binding.avatarImageView.apply {
                if (item.fullName.isNotBlank()) {
                    setBackgroundResource(0)
                    setNamedAvatar(item.fullName)
                    setPadding(0)
                } else {
                    setBackgroundResource(R.drawable.oval_blue)
                    setImageResource(R.drawable.ic_person_white)
                    setPadding(7.px)
                }
            }
        }

        private fun initFields(item: ContactAdapterItem) {
            binding {
                nameTextView.text = item.fullName.ifBlank { item.email }
                emailTextView.apply {
                    isVisible = item.fullName.isNotBlank() && item.email.isNotBlank()
                    text = item.email
                }
            }
        }
    }

    interface ContactClickListener {
        fun onContactClick(item: ContactAdapterItem)
    }

    companion object {
        private val ITEMS_COMPARATOR = object : DiffUtil.ItemCallback<ContactAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: ContactAdapterItem, newItem: ContactAdapterItem
            ): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: ContactAdapterItem, newItem: ContactAdapterItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    }
}