package se.stylianosgakis.mviexample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import se.stylianosgakis.mviexample.R
import se.stylianosgakis.mviexample.model.BlogPost

class MainRecyclerAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK =
        object : DiffUtil.ItemCallback<BlogPost>() {

            override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
                return oldItem.pk == newItem.pk
            }

            override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
                return oldItem == newItem
            }

        }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_blog_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogPostViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<BlogPost>) {
        differ.submitList(list)
    }

    private class BlogPostViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BlogPost) = with(itemView) {
            //Where the actual code happens on what is loaded on the view
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.blog_title.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .into(itemView.blog_image)
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BlogPost)
    }
}
