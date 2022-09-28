package com.serhii.stasiuk.buttontoaction.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.serhii.stasiuk.buttontoaction.utils.Logger

abstract class BaseListAdapter<T, H : BaseListAdapter.BaseViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, H>(diffCallback) {

    protected var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): H {
        return createViewHolder(inflate(parent, viewType), viewType)
    }

    internal abstract fun createViewHolder(view: View, @LayoutRes viewType: Int): H

    @LayoutRes
    abstract override fun getItemViewType(position: Int): Int

    override fun onBindViewHolder(holder: H, position: Int) {
        holder.bind(position)
    }

    override fun onBindViewHolder(holder: H, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) holder.bind(position)
        else holder.bind(position, payloads)
    }

    protected fun BaseViewHolder.getItem(): T? {
        return if (bindingAdapterPosition != RecyclerView.NO_POSITION)
            getItem(bindingAdapterPosition)
        else null
    }

    protected fun getItemSafe(position: Int): T? {
        return if (position != RecyclerView.NO_POSITION && position < itemCount)
            this@BaseListAdapter.getItem(position)
        else null
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(pos: Int)
        open fun bind(pos: Int, payloads: MutableList<Any>) {}
        protected val context: Context by lazy { itemView.context }
    }

    abstract class BaseViewBindingHolder<B : ViewDataBinding>(val binding: B) :
        BaseViewHolder(binding.root) {

        protected open fun binding(block: B.() -> Unit = {}): B? {
            val tag = this@BaseViewBindingHolder::class.simpleName ?: ""
            return try {
                binding
            } catch (e: Exception) {
                Logger.w(tag, "failed to get binding", e)
                null
            }?.also {
                try {
                    binding.let(block)
                } catch (e: Exception) {
                    Logger.w(tag, "failed to use binding", e)
                }
            }
        }

        protected open fun <VB : ViewDataBinding> viewBinding(
            binding: ViewDataBinding?, block: VB.() -> Unit = {},
        ): VB? {
            return (binding as? VB)?.also(block)
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, @LayoutRes res: Int): View {
            return LayoutInflater.from(parent.context).inflate(res, parent, false)
        }
    }
}
