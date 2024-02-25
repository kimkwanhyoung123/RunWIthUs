package layout

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemSpacingDecoration(private val spacingInPixels: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = spacingInPixels // 오른쪽 간격 설정
    }
}
