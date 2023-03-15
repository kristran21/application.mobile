package vn.edu.greenwich.cw_1_sample.ui.trip.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Trip;

public class TripListFragment extends Fragment {
    protected ArrayList<Trip> tripList = new ArrayList<>();

    protected ResimaDAO db;
    protected TextView fmTripListEmptyNotice;
    protected RecyclerView fmTripListRecyclerView;
    protected EditText fmResidentListFilter;
    protected TripAdapter tripAdapter;
    protected ImageButton fmResidentListButtonResetSearch, fmResidentListButtonSearch;

    public TripListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        db = new ResimaDAO(context);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        tripList = db.getTripList(null, null, false);

        fmTripListRecyclerView = view.findViewById(R.id.fmTripListRecylerView);
        fmTripListEmptyNotice = view.findViewById(R.id.fmTripListEmptyNotice);

        fmResidentListButtonResetSearch = view.findViewById(R.id.fmResidentListButtonResetSearch);
        fmResidentListButtonResetSearch.setOnClickListener(v -> resetSearch());

        fmResidentListButtonSearch = view.findViewById(R.id.fmResidentListButtonSearch);
        fmResidentListButtonSearch.setOnClickListener(v -> resetSearch());

        fmResidentListFilter = view.findViewById(R.id.fmResidentListFilter);
        fmResidentListFilter.addTextChangedListener(filter());

        tripAdapter = new TripAdapter(tripList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        fmTripListRecyclerView.addItemDecoration(dividerItemDecoration);
        fmTripListRecyclerView.setAdapter(new TripAdapter(tripList));
        fmTripListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        reloadList(null);
    }

    protected void reloadList(Trip trip) {
        tripList = db.getTripList(trip, null, false);
        tripAdapter.updateList(tripList);

        // Show "No Resident." message.
        fmTripListEmptyNotice.setVisibility(tripList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    protected TextWatcher filter() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tripAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    protected void resetSearch() {
        fmResidentListFilter.setText("");
        reloadList(null);
    }
}
