package com.mobile.mobile_components.fragments


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.mobile.mobile_components.R


class AboutFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_about, container, false)

        val videoView = rootView.findViewById<View>(R.id.videoView) as VideoView
        val mediaController = MediaController(context)
        registerForContextMenu(videoView)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.garage_sale_tips))
        videoView.requestFocus()
        videoView.start()

        return rootView
    }

}