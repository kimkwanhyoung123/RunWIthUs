package com.lx.miniproject

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.fragment.OnFragmentRunningListener
import com.lx.runwithus.RunningFragmentType
import com.lx.runwithus.databinding.FragmentRunningCountDownBinding
import com.lx.runwithus.databinding.FragmentRunningCountBinding



class RunningCountDownFragment : Fragment() {

    var listener : OnFragmentRunningListener? = null
    var _binding: FragmentRunningCountDownBinding? = null
    val binding get() = _binding!!

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentRunningListener) {
            listener = activity
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentRunningCountDownBinding.inflate(inflater, container, false)



        Handler(Looper.getMainLooper()).postDelayed({
            listener?.onFragmentChanged(RunningFragmentType.RUNNINGCOUNT)
        }, 5000)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}