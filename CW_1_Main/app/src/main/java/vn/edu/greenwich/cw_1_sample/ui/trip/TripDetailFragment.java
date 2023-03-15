package vn.edu.greenwich.cw_1_sample.ui.trip;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Expense;
import vn.edu.greenwich.cw_1_sample.models.Trip;
import vn.edu.greenwich.cw_1_sample.ui.dialog.DeleteConfirmFragment;
import vn.edu.greenwich.cw_1_sample.ui.expense.ExpenseCreateFragment;
import vn.edu.greenwich.cw_1_sample.ui.expense.list.ExpenseListFragment;

public class TripDetailFragment extends Fragment
        implements DeleteConfirmFragment.FragmentListener, ExpenseCreateFragment.FragmentListener {
    public static final String ARG_PARAM_TRIP = "trip";

    protected ResimaDAO db;
    protected Trip trip;
    protected Button fmTripDetailExpenseButton;
    protected BottomAppBar fmTripDetailBottomAppBar;
    protected FragmentContainerView fmTripDetailExpenseList;
    protected TextView fmTripNameDetail, fmTripDate, fmTripDescription, fmRequireRiskAssessment, fmBudgetRange, fmTripImportanceNote, fmDescriptionDetail;

    public TripDetailFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = new ResimaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        fmTripNameDetail = view.findViewById(R.id.fmTripNameDetail);
        fmTripDate = view.findViewById(R.id.fmDateTripDetail);
        fmTripDescription = view.findViewById(R.id.fmTripDestinationDetail);
        fmTripDetailBottomAppBar = view.findViewById(R.id.fmTripDetailBottomAppBar);
        fmTripDetailExpenseButton = view.findViewById(R.id.fmTripDetailExpenseButton);
        fmTripDetailExpenseList = view.findViewById(R.id.fmTripDetailExpenseList);
        fmTripImportanceNote = view.findViewById(R.id.fmImportanceNoteTxt);
        fmBudgetRange = view.findViewById(R.id.fmBudgetRangeConfirmTxt);
        fmRequireRiskAssessment = view.findViewById(R.id.fmRequiresRiskAssessmentTxt);
        fmDescriptionDetail = view.findViewById(R.id.fmDescriptionConfirmDetail);
        fmTripDetailBottomAppBar.setOnMenuItemClickListener(item -> menuItemSelected(item));
        fmTripDetailExpenseButton.setOnClickListener(v -> showAddExpenseFragment());

        showDetails();
        showExpenseList();

        return view;
    }

    protected void showDetails() {
        String name = getString(R.string.error_not_found);
        String tripDate = getString(R.string.error_not_found);
        String destination = getString(R.string.error_not_found);
        String importanceNote = getString(R.string.error_not_found);
        String budgetRange = getString(R.string.error_not_found);
        String description = getString(R.string.error_not_found);

        String isRequireRiskAssessment = getString(R.string.error_not_found);

        if (getArguments() != null) {
            trip = (Trip) getArguments().getSerializable(ARG_PARAM_TRIP);
            trip = db.getTripById(trip.getTripId()); // Retrieve data from Database.

            name = trip.getTripName();
            tripDate = trip.getTripDate();
            destination = trip.getDestination();
            importanceNote = trip.getImportanceNote();
            budgetRange = trip.getBudgetRange();
            description = trip.getDescription();
            isRequireRiskAssessment = trip.getRequiresRiskAssessment() == 1 ? getString(R.string.label_require_risk_assessment) : getString(R.string.label_unrequire_risk_assessment);
        }

        fmTripDescription.setText(destination);
        fmTripNameDetail.setText(name);
        fmTripDate.setText(tripDate);
        fmTripImportanceNote.setText(importanceNote);
        fmBudgetRange.setText(budgetRange);
        fmDescriptionDetail.setText(description);
        fmRequireRiskAssessment.setText(isRequireRiskAssessment);
    }

    protected void showExpenseList() {
        if (getArguments() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExpenseListFragment.ARG_PARAM_TRIP_ID, trip.getTripId());
            System.out.println(bundle);
            System.out.println("test " + getChildFragmentManager().getFragments());
            // Send arguments (trip id) to ExpenseListFragment.
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }
    }

    protected boolean menuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tripUpdateFragment:
                showUpdateFragment();
                return true;

            case R.id.tripDeleteFragment:
                showDeleteConfirmFragment();
                return true;
        }

        return true;
    }

    protected void showUpdateFragment() {
        Bundle bundle = null;

        if (trip != null) {
            bundle = new Bundle();
            bundle.putSerializable(TripUpdateFragment.ARG_PARAM_TRIP, trip);
        }

        Navigation.findNavController(getView()).navigate(R.id.tripUpdateFragment, bundle);
    }

    protected void showDeleteConfirmFragment() {
        new DeleteConfirmFragment(getString(R.string.notification_delete_confirm)).show(getChildFragmentManager(), null);
    }

    protected void showAddExpenseFragment() {
        new ExpenseCreateFragment(trip.getTripId()).show(getChildFragmentManager(), null);
    }

    @Override
    public void sendFromDeleteConfirmFragment(int status) {
        if (status == 1 && trip != null) {
            long numOfDeletedRows = db.deleteTrip(trip.getTripId());

            if (numOfDeletedRows > 0) {
                Toast.makeText(getContext(), R.string.notification_delete_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();

                return;
            }
        }

        Toast.makeText(getContext(), R.string.notification_delete_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFromExpenseCreateFragment(Expense expense) {
        if (expense != null) {
            expense.setTripId(trip.getTripId());

            long id = db.insertExpense(expense);

            Toast.makeText(getContext(), id == -1 ? R.string.notification_create_fail : R.string.notification_create_success, Toast.LENGTH_SHORT).show();

            reloadExpenseList();
        }
    }

    protected void reloadExpenseList() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseListFragment.ARG_PARAM_TRIP_ID, trip.getTripId());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fmTripDetailExpenseList, ExpenseListFragment.class, bundle)
                .commit();
    }
}