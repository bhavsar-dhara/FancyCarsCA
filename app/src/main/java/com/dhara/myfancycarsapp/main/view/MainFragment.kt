package com.dhara.myfancycarsapp.main.view

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.ProgressBar
import com.dhara.myfancycarsapp.fancycars.view.FancyCarsActivity
import com.dhara.myfancycarsapp.R
import com.dhara.myfancycarsapp.main.viewmodel.MainViewModel

//import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

//        val progressBar: ProgressBar = this.progress_bar

        val mHandler = Handler()
        mHandler.postDelayed({
            val intent = Intent(requireActivity(), FancyCarsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }, 3000)
    }

}
