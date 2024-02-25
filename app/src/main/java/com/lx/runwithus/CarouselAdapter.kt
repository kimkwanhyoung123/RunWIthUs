import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lx.runwithus.R

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private val items: List<CarouselItem> = listOf(
        CarouselItem(R.drawable.carouselbacka, "", "런위더스에 처음 오셨나요? 바로 가입하시고 시작하세요. 이미 사용하고 계신다면 로그인 또는 가입하시면 Run With Us와 함께 하실 수 있습니다."),
        CarouselItem(R.drawable.page1, "크루 추천", "런위더스에서 추천하는 크루에 가입해보세요. 자신의 운동스타일과 맞는 크루를 추천해드립니다."),
        CarouselItem(R.drawable.page2, "함께 러닝", "크루원들과 함께 달려보세요. 혼자가 아닌 같이 뛰는 런위더스"),
        CarouselItem(R.drawable.page3, "챌린지 도전", "맞춤형 러닝크루를 만나 미션에 도전해보세요 !"),
        CarouselItem(R.drawable.page4, "응급 상황 대처", "응급 상황 발생 시 가장 가까운 AED위치를 확인하세요.")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val textView2: TextView = itemView.findViewById(R.id.textView2)

        fun bind(item: CarouselItem, position: Int) {
            imageView.setImageResource(item.imageRes)
            textView.text = item.text
            textView2.text = item.text2

            // 첫 번째 이미지뷰 크기 조절
            if (position == 0) {
                imageView.layoutParams.width = 500
                imageView.layoutParams.height = 500

//                val paddingInPixels = convertDpToPixel(40f, imageView.context).toInt()
//                imageView.setPadding(0, paddingInPixels, 0, 0)
            } else {
                // 다른 이미지뷰는 기본 크기로 유지
                imageView.layoutParams.width = 900
                imageView.layoutParams.height = 900
                val paddingInPixels = convertDpToPixel(40f, imageView.context).toInt()
                imageView.setPadding(0, paddingInPixels, 0, 0)
                imageView.setPadding(0, 0, 0, 0)
            }


            imageView.requestLayout()
        }
    }

    // dp를 픽셀로 변환하는 함수
    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    data class CarouselItem(val imageRes: Int, val text: String, val text2: String)
}