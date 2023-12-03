package br.com.leandro.training.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.training.R

/**
 * A [Class] that is responsible for detecting the horizontal
 * movement of an item and returning whether the movement has
 * exceeded the limit to call the action
 */
class ItemSwipeHandle(
    private val context: Context,
    private val onTouchListener: OnTouchListener
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    companion object {
        private const val LIMIT_SWIPE_LENGTH = 1
    }

    private var icon: Drawable? = null
    private var background: Drawable? = null

    interface OnTouchListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onTouchListener.onSwiped(viewHolder, direction)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.33f // 33% to apply the action
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
        var mDX = dX

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        val midWidth = c.width / 3
        val absCurrentX = StrictMath.abs(viewHolder.itemView.translationX)
        if (absCurrentX < midWidth && StrictMath.abs(dX) >= midWidth)
            context.vibrate(50)

        icon = getDrawable(context, R.drawable.ic_delete_item)
        background = getBackground(mDX)

        when {
            (mDX < 0) -> {
                val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight) / 2
                val iconTop: Int = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
                val iconBottom: Int = iconTop + icon!!.intrinsicHeight
                val iconLeft: Int = itemView.right - iconMargin - icon!!.intrinsicWidth
                val iconRight: Int = itemView.right - iconMargin
                icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background!!.setBounds(
                    itemView.right + mDX.toInt() - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }
            (mDX > 0) -> {
                val iconMargin: Int = (itemView.left + icon!!.intrinsicHeight) / 2
                val iconTop: Int = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
                val iconBottom: Int = iconTop + icon!!.intrinsicHeight
                val iconLeft: Int = itemView.left + iconMargin
                val iconRight: Int = itemView.left + iconMargin + icon!!.intrinsicWidth
                icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background!!.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.right + mDX.toInt() - backgroundCornerOffset,
                    itemView.bottom
                )
            }
        }
        background!!.draw(c)
        icon!!.draw(c)

        mDX /= LIMIT_SWIPE_LENGTH

        super.onChildDraw(
            c, recyclerView, viewHolder, mDX,
            dY, actionState, isCurrentlyActive
        )
    }

    private fun getBackground(mDX: Float): Drawable? {
        return when {
            mDX < 0 -> getDrawable(context, R.drawable.bg_swipe_left)
            mDX > 0 -> getDrawable(context, R.drawable.bg_swipe_right)
            else -> getDrawable(context, R.drawable.ic_transparent)
        }
    }
}