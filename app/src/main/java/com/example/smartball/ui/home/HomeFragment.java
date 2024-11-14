package com.example.smartball.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartball.DatabaseHelper;
import com.example.smartball.R;
import com.example.smartball.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner areaSpinner = view.findViewById(R.id.area_spinner);
        ArrayAdapter<CharSequence> area_adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.area_options,
                android.R.layout.simple_spinner_item
        );
        area_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(area_adapter);

        Spinner timeSpinner = view.findViewById(R.id.time_spinner);
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.time_options,
                android.R.layout.simple_spinner_item
        );
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(time_adapter);

        Button beginButton = view.findViewById(R.id.begin_button);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        beginButton.setOnClickListener(v -> {
            String areaOption = areaSpinner.getSelectedItem().toString();
            String timeOption = timeSpinner.getSelectedItem().toString();
            boolean isInserted = dbHelper.addTreatment(areaOption, timeOption);

            if (isInserted) {
                Toast.makeText(getContext(), "Kirjaus lisätty onnistuneesti", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Virhe lisättäessä kirjausta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}