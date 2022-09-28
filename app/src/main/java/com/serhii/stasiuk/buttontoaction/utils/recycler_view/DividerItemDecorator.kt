package com.serhii.stasiuk.buttontoaction.utils.recycler_view

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorator(
    private val divider: Drawable,
    private val usePadding: Boolean,
    private val topMargin: Int = 0,
    private val showAtPosition: (Int) -> Boolean = { true },
) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach { child ->
            val position = parent.getChildAdapterPosition(child)
            if (showAtPosition(position)) {
                addDivider(child, parent, canvas)
            }
        }
    }

    private fun addDivider(child: View, parent: RecyclerView, canvas: Canvas) {
        (child.layoutParams as? RecyclerView.LayoutParams)?.let { parentParams ->
            val dividerBottom = child.bottom + parentParams.bottomMargin + topMargin
            val dividerTop = dividerBottom - divider.intrinsicHeight
            val dividerLeft = if (usePadding) child.paddingStart else 0
            val dividerRight =
                parent.width - parent.paddingEnd - (if (usePadding) child.paddingEnd else 0)
            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(canvas)
        }
    }
}