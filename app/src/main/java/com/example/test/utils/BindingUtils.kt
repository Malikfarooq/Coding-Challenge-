package com.example.test.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.example.MoviesResults
import com.example.test.R
import com.example.test.adapter.MoviesAdapter
import com.example.test.adapter.MoviesAdapter.Companion.BASE_IMAGE_URL
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("image")
@NonNull
fun loadImage(view: ImageView, url: String) {
    Picasso.get().load(BASE_IMAGE_URL + url).into(view)
}

@BindingAdapter("changeTextColor")
fun setColorYearMatch(textView: TextView, movieDate: String) {
    var current = Calendar.getInstance().get(Calendar.YEAR);
    if (current == getMovieYear(movieDate).toInt()) {
        makeYearTextBold(textView, movieDate)
    } else {
        makeYearTextNormal(textView, movieDate)
    }
}


private fun getMovieYear(movieDate: String): String {
    return try {
        val format = SimpleDateFormat(Common.DATE_FORMAT)
        val date = format.parse(movieDate)
        val df = SimpleDateFormat(Common.DATE_FORMAT_YEAR)
        df.format(date)
    }catch (eX: Exception)
    {
        "0"
    }
}

private fun makeYearTextBold(textView: TextView, text: String) {
    textView.setTextColor(textView.context.getColor(R.color.red))
    textView.text =
        HtmlCompat.fromHtml(
            "<b>" + text!! + "</b>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
}

private fun makeYearTextNormal(textView: TextView, text: String) {
    textView.setTextColor(textView.context!!.getColor(R.color.white))
    textView.text = text
}


fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, getItemViewType())
    }
    return this
}