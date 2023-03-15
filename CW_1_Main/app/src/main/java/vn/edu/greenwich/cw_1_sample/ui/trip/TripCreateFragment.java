package vn.edu.greenwich.cw_1_sample.ui.trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Trip;
import vn.edu.greenwich.cw_1_sample.ui.dialog.CalendarFragment;

public class TripCreateFragment extends Fragment
        implements TripCreateConfirmFragment.FragmentListener, CalendarFragment.FragmentListener {
    public static final String ARG_PARAM_TRIP = "trip";

    protected EditText fmTripName, fmTripDestination, fmTripDate, fmTripDescription, fmTripImportanceNote, fmBudgetRange;
    protected LinearLayout fmTripRegisterLinearLayout;
    protected SwitchMaterial fmRequireRiskAssessment;
    protected TextView fmTripRegisterError;
    protected Button fmTripRegisterButton;

    protected ResimaDAO db;

    public TripCreateFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_create, container, false);

        fmTripRegisterError = view.findViewById(R.id.fmTripError);
        fmTripName = view.findViewById(R.id.fmTripName);
        fmTripDestination = view.findViewById(R.id.fmTripDestination);
        fmTripDate = view.findViewById(R.id.fmTripDate);
        fmTripDescription = view.findViewById(R.id.fmTripDescription);
        fmTripImportanceNote = view.findViewById(R.id.fmTripImportanceNote);
        fmBudgetRange = view.findViewById(R.id.fmBudgetRange);
        fmRequireRiskAssessment = view.findViewById(R.id.fmRequireRiskAssessment);
        fmTripRegisterButton = view.findViewById(R.id.fmTripRegisterButton);
        fmTripRegisterLinearLayout = view.findViewById(R.id.fmTripLinearLayout);

        // Show Calendar for choosing a date.
        fmTripDate.setOnTouchListener((v, motionEvent) -> showCalendar(motionEvent));

        // Update current trip.
        if (getArguments() != null) {
            Trip trip = (Trip) getArguments().getSerializable(ARG_PARAM_TRIP);

            fmTripName.setText(trip.getTripName());
            fmTripDestination.setText(trip.getDestination());
            fmRequireRiskAssessment.setChecked(trip.getRequiresRiskAssessment() == 1 ? true : false);
            fmTripDate.setText(trip.getTripDate());
            fmTripDescription.setText(trip.getDescription());
            fmTripImportanceNote.setText(trip.getImportanceNote());
            fmBudgetRange.setText(trip.getBudgetRange());
            fmTripRegisterButton.setText(R.string.label_update);
            fmTripRegisterButton.setOnClickListener(v -> update(trip.getTripId()));

            return view;
        }

        // Create new trip.
        fmTripRegisterButton.setOnClickListener(v -> create());

        return view;
    }

    protected void create() {
        if (isValidForm()) {
            Trip trip = getTripFromInput(-1);

            new TripCreateConfirmFragment(trip).show(getChildFragmentManager(), null);

            return;
        }

        moveButton();
    }

    protected void update(long id) {
        if (isValidForm()) {
            Trip trip = getTripFromInput(id);

            long status = db.updateTrip(trip);

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromTripCreateFragment(status);

            return;
        }

        moveButton();
    }

    protected boolean showCalendar(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    protected Trip getTripFromInput(long id) {
        String name = fmTripName.getText().toString();
        String destination = fmTripDestination.getText().toString();
        String tripDate = fmTripDate.getText().toString();
        String tripDescription = fmTripDescription.getText().toString();
        String tripImportanceNote = fmTripImportanceNote.getText().toString();
        String tripBudgetRange = fmBudgetRange.getText().toString();
        int isRequireRiskAssessment = fmRequireRiskAssessment.isChecked() ? 1 : 0;

        return new Trip(id, name, destination, tripDate, isRequireRiskAssessment, tripDescription, tripImportanceNote, tripBudgetRange);
    }

    protected boolean isValidForm() {
        boolean isValid = true;

        String error = "";
        String name = fmTripName.getText().toString();
        String destination = fmTripDestination.getText().toString();
        String tripDate = fmTripDate.getText().toString();
        String tripDescription = fmTripDescription.getText().toString();
        String tripImportanceNote = fmTripImportanceNote.getText().toString();
        String tripBudgetRange = fmBudgetRange.getText().toString();
        int isRequireRiskAssessment = fmRequireRiskAssessment.isChecked() ? 1 : 0;

        if (name == null || name.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_name) + "\n";
            isValid = false;
        }

        if (destination == null || destination.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_destination) + "\n";
            isValid = false;
        }

        if (tripDate == null || tripDate.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_trip_date) + "\n";
            isValid = false;
        }
        if (tripDescription == null || tripDescription.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_name) + "\n";
            isValid = false;
        }

        if (tripImportanceNote == null || tripImportanceNote.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_destination) + "\n";
            isValid = false;
        }

        if (tripBudgetRange == null || tripBudgetRange.trim().isEmpty()) {
            error += "* " + getString(R.string.error_blank_trip_date) + "\n";
            isValid = false;
        }

        fmTripRegisterError.setText(error);

        return isValid;
    }

    protected void moveButton() {
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) fmTripRegisterButton.getLayoutParams();

        int linearLayoutPaddingLeft = fmTripRegisterLinearLayout.getPaddingLeft();
        int linearLayoutPaddingRight = fmTripRegisterLinearLayout.getPaddingRight();
        int linearLayoutWidth = fmTripRegisterLinearLayout.getWidth() - linearLayoutPaddingLeft - linearLayoutPaddingRight;

        btnParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        btnParams.topMargin += fmTripRegisterButton.getHeight();
        btnParams.leftMargin = btnParams.leftMargin == 0 ? linearLayoutWidth - fmTripRegisterButton.getWidth() : 0;

        fmTripRegisterButton.setLayoutParams(btnParams);
    }

    @Override
    public void sendFromTripCreateConfirmFragment(long status) {
        switch ((int) status) {
            case -1:
                Toast.makeText(getContext(), R.string.notification_create_fail, Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), R.string.notification_create_success, Toast.LENGTH_SHORT).show();

                fmTripName.setText("");
                fmTripDate.setText("");

                fmTripName.requestFocus();
        }
    }

    @Override
    public void sendFromCalendarFragment(String date) {
        fmTripDate.setText(date);
    }

    public interface FragmentListener {
        void sendFromTripCreateFragment(long status);
    }
}