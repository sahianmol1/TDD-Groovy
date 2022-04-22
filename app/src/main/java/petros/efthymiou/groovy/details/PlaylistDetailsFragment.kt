package petros.efthymiou.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.databinding.FragmentPlaylistDetailBinding
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistDetailBinding
    private lateinit var viewModel: PlaylistDetailsViewModel
    @Inject lateinit var viewModelFactory: PlaylistDetailsViewModelFactory
    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val id = args.playlistId

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.getPlayListDetails(id)
        }

        viewModel.loader.observe(viewLifecycleOwner){ isLoading ->
            when(isLoading) {
                true -> binding.detailsLoader.visibility = View.VISIBLE
                else -> binding.detailsLoader.visibility = View.GONE
            }
        }

        viewModel.playListDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                binding.tvPlaylistName.text = playlistDetails.getOrNull()!!.name
                binding.tvDetails.text = playlistDetails.getOrNull()!!.details
            } else {
                Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
            }

        }

        return view
    }

}