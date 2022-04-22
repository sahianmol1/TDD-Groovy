package petros.efthymiou.groovy.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import petros.efthymiou.groovy.data.PlayList
import petros.efthymiou.groovy.databinding.ItemPlaylistBinding

class PlaylistRecyclerViewAdapter(
    private val values: List<PlayList>,
    private val onClick: (String) -> Unit,
) : RecyclerView.Adapter<PlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.root.setOnClickListener { onClick(item.id) }
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        val playlistName: TextView = binding.tvTitle
        val playListImage: ImageView = binding.imgPlaylist
        val category: TextView = binding.tvPlaylistCategory

        fun bind(item: PlayList) {
            playlistName.text = item.name
            category.text = item.category
            playListImage.setImageResource(item.image)
        }

    }

}