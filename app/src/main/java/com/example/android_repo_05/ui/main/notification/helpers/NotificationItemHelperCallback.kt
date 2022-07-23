package com.example.android_repo_05.ui.main.notification.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_05.R
import com.example.android_repo_05.others.Utils

class NotificationItemHelperCallback(
    private val context: Context,
    private val swipeListener: (position: Int) -> Unit
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = viewHolder.itemView
            if (dX != 0F) {
                c.drawRect(
                    view.right.toFloat() + dX,
                    view.top.toFloat(),
                    view.right.toFloat(),
                    view.bottom.toFloat(),
                    Paint().apply {
                        color = ContextCompat.getColor(context, R.color.primary)
                    }
                )
            }
            val margin = Utils.dpToPx(context, 48)
            ContextCompat.getDrawable(context, R.drawable.ic_check)?.let {
                if (dX < -margin - it.intrinsicWidth) {
                    it.bounds = Rect(
                        view.right - margin - it.intrinsicWidth,
                        view.top + ((view.bottom - view.top - it.intrinsicHeight) / 2),
                        view.right - margin,
                        view.top + ((view.bottom - view.top + it.intrinsicHeight) / 2)
                    )
                    it.draw(c)
                }
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            swipeListener(viewHolder.layoutPosition)
        }
    }
}