package vn.edu.greenwich.cw_1_sample.ui.trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Trip;

public class TripCreateConfirmFragment extends DialogFragment {
    protected ResimaDAO db;
    protected Trip trip;
    protected Button fmTripConfirmButtonConfirm, fmTripConfirmButtonCancel;
    protected TextView fmTripNameDetail, fmTripDestinationDetail, fmDateTripDetail, fmDescriptionConfirmDetail, fmImportanceNoteTxt, fmBudgetRangeConfirmTxt, fmRequiresRiskAssessmentTxt;

    public TripCreateConfirmFragment() {
        trip = new Trip();
    }

    public TripCreateConfirmFragment(Trip trip) {
        this.trip = trip;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new ResimaDAO(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_create_confirm, container, false);

        String tripName = getString(R.string.error_no_info);
        String destination = getString(R.string.error_no_info);
        String tripDate = getString(R.string.error_no_info);
        String description = getString(R.string.error_no_info);
        String importanceNote = getString(R.string.error_no_info);
        String budgetRange = getString(R.string.error_no_info);
        String requireRiskAssessment = getString(R.string.error_no_info);

        fmTripNameDetail = view.findViewById(R.id.fmTripNameDetail);
        fmTripDestinationDetail = view.findViewById(R.id.fmTripDestinationDetail);
        fmDateTripDetail = view.findViewById(R.id.fmDateTripDetail);
        fmDescriptionConfirmDetail = view.findViewById(R.id.fmDescriptionConfirmDetail);
        fmImportanceNoteTxt = view.findViewById(R.id.fmImportanceNoteTxt);
        fmBudgetRangeConfirmTxt = view.findViewById(R.id.fmBudgetRangeConfirmTxt);
        fmRequiresRiskAssessmentTxt = view.findViewById(R.id.fmRequiresRiskAssessmentTxt);
        fmTripConfirmButtonCancel = view.findViewById(R.id.fmTripConfirmButtonCancel);
        fmTripConfirmButtonConfirm = view.findViewById(R.id.fmTripConfirmButtonConfirm);

        if (trip.getRequiresRiskAssessment() != -1) {
            requireRiskAssessment = trip.getRequiresRiskAssessment() == 1 ? getString(R.string.label_require_risk_assessment) : getString(R.string.label_unrequire_risk_assessment);
        }

        if (trip.getTripName() != null && !trip.getTripName().trim().isEmpty()) {
            tripName = trip.getTripName();
        }

        if (trip.getTripDate() != null && !trip.getTripName().trim().isEmpty()) {
            tripDate = trip.getTripDate();
        }

        if (trip.getDestination() != null && !trip.getDestination().trim().isEmpty()) {
            destination = trip.getDestination();
        }

        if (trip.getDescription() != null && !trip.getDescription().trim().isEmpty()) {
            description = trip.getDescription();
        }

        if (trip.getImportanceNote() != null && !trip.getImportanceNote().trim().isEmpty()) {
            importanceNote = trip.getImportanceNote();
        }

        if (trip.getBudgetRange() != null && !trip.getBudgetRange().trim().isEmpty()) {
            budgetRange = trip.getBudgetRange();
        }

        fmTripNameDetail.setText(tripName);
        fmTripDestinationDetail.setText(destination);
        fmDateTripDetail.setText(tripDate);
        fmDescriptionConfirmDetail.setText(description);
        fmImportanceNoteTxt.setText(importanceNote);
        fmBudgetRangeConfirmTxt.setText(budgetRange);
        fmRequiresRiskAssessmentTxt.setText(requireRiskAssessment);
        fmTripConfirmButtonCancel = view.findViewById(R.id.fmTripConfirmButtonCancel);
        fmTripConfirmButtonConfirm = view.findViewById(R.id.fmTripConfirmButtonConfirm);

        fmTripConfirmButtonCancel.setOnClickListener(v -> dismiss());
        fmTripConfirmButtonConfirm.setOnClickListener(v -> confirm());

        return view;
    }

    protected void confirm() {
        long status = db.insertTrip(trip);

        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromTripCreateConfirmFragment(status);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromTripCreateConfirmFragment(long status);
    }
}