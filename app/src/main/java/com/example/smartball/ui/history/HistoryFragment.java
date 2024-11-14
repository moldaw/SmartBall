package com.example.smartball.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartball.DatabaseHelper;
import com.example.smartball.R;
import com.example.smartball.databinding.FragmentHistoryBinding;
import com.example.smartball.ui.Treatment;

import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = view.findViewById(R.id.history_display);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        List<Treatment> treatments = dbHelper.getAllTreatments();

        for (Treatment treatment : treatments) {
            int treatmentId = treatment.id;
            String treatmentArea = treatment.areaOption;
            String treatmentTime = treatment.timeOption;
            String treatmentTimestamp = treatment.getFormattedTimestamp();

            LinearLayout treatmentLayout = new LinearLayout(getContext());
            treatmentLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView textView = new TextView(getContext());
            textView.setText(treatmentArea + " - " + treatmentTime + " - " + treatmentTimestamp);
            textView.setTextSize(18);
            treatmentLayout.addView(textView);

            Button deleteButton = new Button(getContext());
            deleteButton.setText("Poista");
            deleteButton.setOnClickListener(v -> {
                boolean deleted = dbHelper.deleteTreatment(treatmentId);
                if (deleted) {
                    Toast.makeText(getContext(), "Kirjaus poistettu", Toast.LENGTH_SHORT).show();
                    linearLayout.removeView(treatmentLayout);
                } else {
                    Toast.makeText(getContext(), "Poisto epäonnistui", Toast.LENGTH_SHORT).show();
                }
            });
            treatmentLayout.addView(deleteButton);
            linearLayout.addView(treatmentLayout);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}