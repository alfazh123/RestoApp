package com.example.restoapp.presentation.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.example.core.helper.AlarmReceiver
import com.example.restoapp.databinding.FragmentSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var alarmReceiver: AlarmReceiver

    private val settingViewModel: SettingViewModel by viewModel()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
//                Toast.makeText(requireActivity(), "Notifications permission granted", Toast.LENGTH_SHORT).show()
                Log.d("SettingFragment", "onViewCreated: Notifications permission granted")
            } else {
//                Toast.makeText(requireActivity(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
                Log.d("SettingFragment", "onViewCreated: Notifications permission rejected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().actionBar?.title = "Setting"

        alarmReceiver = AlarmReceiver()

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        settingViewModel.getDailyReminderSetting().observe(viewLifecycleOwner) { dailyReminderSetting ->
            if (dailyReminderSetting) {
                binding.switchNotification.isChecked = true
                binding.switchNotification.setOnClickListener {
                    alarmReceiver.cancelAlarm(requireActivity(), AlarmReceiver.TYPE_REPEATING)
                    settingViewModel.saveDailyReminderSetting(false)
                }
            } else {
                binding.switchNotification.isChecked = false
                binding.switchNotification.setOnClickListener {
                    alarmReceiver.setRepeatingAlarm(requireActivity(), AlarmReceiver.TYPE_REPEATING, "10:00", "Let's find favorite restaurant today!")
                    settingViewModel.saveDailyReminderSetting(true)
                }
            }
        }

    }
}