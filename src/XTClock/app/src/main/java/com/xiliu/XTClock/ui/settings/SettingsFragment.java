package com.xiliu.XTClock.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.xiliu.XTClock.databinding.FragmentSettingsBinding;
import com.xiliu.XTClock.FloatingClockService;
import com.xiliu.XTClock.FloatWindowPermissionUtil;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.buttonStart.setOnClickListener(v -> onClickButtonStart());
        binding.buttonStop.setOnClickListener(v -> onClickButtonStop());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FloatWindowPermissionUtil.hasPermission(requireContext())) {
            Toast.makeText(requireContext(),
                    "已获得悬浮窗显示授权",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动悬浮时钟服务
     */
    private void onClickButtonStart() {
        if (!FloatWindowPermissionUtil.hasPermission(requireContext())) {
            Toast.makeText(requireContext(), "请开启悬浮窗权限以使用该功能", Toast.LENGTH_SHORT).show();
            FloatWindowPermissionUtil.requestPermission(requireContext());
            return;
        }

        // 启动服务
        Intent intent = new Intent(requireActivity(), FloatingClockService.class);
        requireActivity().startService(intent);
        Toast.makeText(requireContext(), "悬浮时钟已启动", Toast.LENGTH_SHORT).show();
    }

    /**
     * 停止悬浮时钟服务
     */
    private void onClickButtonStop() {
        Intent intent = new Intent(requireActivity(), FloatingClockService.class);
        requireActivity().stopService(intent);
        Toast.makeText(requireContext(), "悬浮时钟已停止", Toast.LENGTH_SHORT).show();
    }
}