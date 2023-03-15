package com.example.testpixabay.wallpaper

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.testpixabay.R
import com.example.testpixabay.databinding.FragmentWallpaperBinding


class FragmentWallpaper : Fragment() {

    private var _binding: FragmentWallpaperBinding? = null
    private val binding get() = _binding!!
    private val imageURL by lazy {
        arguments?.getString("image")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallpaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImage()
        binding.buttonWallpaper.setOnClickListener {
            setWallpaper()
        }
        binding.errorView.buttonReload.setOnClickListener {
            setImage()
        }
    }

    private fun setImage() {
        showLoad()
        Glide.with(this)
            .load(imageURL)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    showError()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    showImage()
                    return false
                }
            })
            .into(binding.ivWallpaper)
    }

    private fun setWallpaper() {
        val drawable: Drawable? = binding.ivWallpaper.drawable
        if (drawable != null) {
            val bitmapDrawable = drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            WallpaperManager.getInstance(requireContext()).setBitmap(bitmap)
            showMessageSuccess()
        } else {
            showMessageError()
        }
    }


    private fun showMessageSuccess() {
        Toast.makeText(requireContext(), getString(R.string.message_wallpaper_success), Toast.LENGTH_SHORT).show()
    }

    private fun showMessageError() {
        Toast.makeText(requireContext(),getString(R.string.message_wallpaper_error), Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        binding.ivWallpaper.isVisible = false
        binding.progressBarWallpaper.isVisible = false
        binding.buttonWallpaper.isVisible = false
        binding.errorView.root.isVisible = true
    }

    private fun showLoad() {
        binding.errorView.root.isVisible = false
        binding.progressBarWallpaper.isVisible = true
    }

    private fun showImage() {
        binding.ivWallpaper.isVisible = true
        binding.buttonWallpaper.isVisible = true
        binding.errorView.root.isVisible = false
        binding.progressBarWallpaper.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}