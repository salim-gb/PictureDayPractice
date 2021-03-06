package com.example.pictureoftheday.ui.space

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.MoonFragmentBinding
import com.example.pictureoftheday.repository.MoonDataSource
import com.example.pictureoftheday.ui.FullscreenImageFragmentDirections

class Moon : Fragment(R.layout.moon_fragment) {

    private var _binding: MoonFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SpaceSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MoonFragmentBinding.bind(view)

        val moonPicturesList = MoonDataSource.getMoonPicture()

        binding.moonRecyclerView.adapter = MoonAdapter(moonPicturesList) {
            it.let {
                val action =
                    FullscreenImageFragmentDirections.actionGlobalFullscreenImage(moonData = it)
                findNavController().navigate(
                    action,
                    navOptions {
                        anim {
                            enter = android.R.animator.fade_in
                            exit = android.R.animator.fade_out
                            popEnter = android.R.animator.fade_in
                            popExit = android.R.animator.fade_out
                        }
                    })
            }
        }

        binding.moonRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            onScrollChange()
        }
    }

    private fun onScrollChange() {
        viewModel.onScrollStateChange(binding.moonRecyclerView.canScrollVertically(-1))
    }

    override fun onResume() {
        super.onResume()
        viewModel.onScrollStateChange(binding.moonRecyclerView.canScrollVertically(-1))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = Moon()
    }
}