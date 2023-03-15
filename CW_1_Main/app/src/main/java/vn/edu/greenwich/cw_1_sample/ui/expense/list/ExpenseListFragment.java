package vn.edu.greenwich.cw_1_sample.ui.expense.list;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.database.ResimaDAO;
import vn.edu.greenwich.cw_1_sample.models.Expense;

public class ExpenseListFragment extends Fragment {
    public static final String ARG_PARAM_TRIP_ID = "trip_id";

    protected ArrayList<Expense> expenseList = new ArrayList<>();

    protected ResimaDAO db;
    protected TextView fmExpenseListEmptyNotice;
    protected RecyclerView fmExpenseListRecyclerView;

    public ExpenseListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        db = new ResimaDAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        if (getArguments() != null) {
            Expense expense = new Expense();
            expense.setTripId(getArguments().getLong(ARG_PARAM_TRIP_ID));

            expenseList = db.getExpenseList(expense, null, false);
        }

        fmExpenseListRecyclerView = view.findViewById(R.id.fmExpenseListRecyclerView);
        fmExpenseListEmptyNotice = view.findViewById(R.id.fmExpenseListEmptyNotice);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        fmExpenseListRecyclerView.addItemDecoration(dividerItemDecoration);
        fmExpenseListRecyclerView.setAdapter(new ExpenseAdapter(expenseList));
        fmExpenseListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Show "No Expense." message.
        fmExpenseListEmptyNotice.setVisibility(expenseList.isEmpty() ? View.VISIBLE : View.GONE);

        return view;
    }
}