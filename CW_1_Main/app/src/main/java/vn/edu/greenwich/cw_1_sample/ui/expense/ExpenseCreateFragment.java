package vn.edu.greenwich.cw_1_sample.ui.expense;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.models.Expense;
import vn.edu.greenwich.cw_1_sample.ui.dialog.DatePickerFragment;
import vn.edu.greenwich.cw_1_sample.ui.dialog.TimePickerFragment;

public class ExpenseCreateFragment extends DialogFragment
        implements DatePickerFragment.FragmentListener, TimePickerFragment.FragmentListener {
    protected long tripId;

    protected EditText fmExpenseCreateDate, fmExpenseCreateTime, fmExpenseServiceTxt, fmExpenseCostTxt, fmExpensePlaceTxt;
    protected Button fmExpenseCreateButtonCancel, fmExpenseCreateButtonAdd;
    protected Spinner fmExpenseCreateType;

    public ExpenseCreateFragment() {
        tripId = -1;
    }

    public ExpenseCreateFragment(long tripId) {
        this.tripId = tripId;
    }

    @Override
    public void sendFromDatePickerFragment(String date) {
        fmExpenseCreateDate.setText(date);
    }

    @Override
    public void sendFromTimePickerFragment(String time) {
        fmExpenseCreateTime.setText(time);
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
        View view = inflater.inflate(R.layout.fragment_expense_create, container, false);

        fmExpenseCreateDate = view.findViewById(R.id.fmExpenseCreateDate);
        fmExpenseCreateTime = view.findViewById(R.id.fmExpenseCreateTime);
        fmExpenseServiceTxt = view.findViewById(R.id.fmExpenseServiceTxt);
        fmExpenseCostTxt = view.findViewById(R.id.fmExpenseCostTxt);
        fmExpensePlaceTxt = view.findViewById(R.id.fmExpensePlaceTxt);
        fmExpenseCreateButtonCancel = view.findViewById(R.id.fmExpenseCreateButtonCancel);
        fmExpenseCreateButtonAdd = view.findViewById(R.id.fmExpenseCreateButtonAdd);
        fmExpenseCreateType = view.findViewById(R.id.fmExpenseCreateType);

        fmExpenseCreateButtonCancel.setOnClickListener(v -> dismiss());
        fmExpenseCreateButtonAdd.setOnClickListener(v -> createExpense());

        fmExpenseCreateDate.setOnTouchListener((v, motionEvent) -> showDateDialog(motionEvent));
        fmExpenseCreateTime.setOnTouchListener((v, motionEvent) -> showTimeDialog(motionEvent));

        setTypeSpinner();

        return view;
    }

    protected void setTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.expense_type,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fmExpenseCreateType.setAdapter(adapter);
    }

    protected boolean showDateDialog(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new DatePickerFragment().show(getChildFragmentManager(), null);
            return true;
        }

        return false;
    }

    protected boolean showTimeDialog(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            new TimePickerFragment().show(getChildFragmentManager(), null);
            return true;
        }

        return false;
    }

    protected void createExpense() {
        Expense expense = new Expense();

        expense.setExpenseService(fmExpenseCreateType.getSelectedItem().toString());
        expense.setExpensePlace(fmExpenseCreateTime.getText().toString());
        expense.setExpenseTime(fmExpenseCreateDate.getText().toString());
        expense.setExpenseCost(fmExpenseCostTxt.getText().toString());

        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromExpenseCreateFragment(expense);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromExpenseCreateFragment(Expense expense);
    }
}