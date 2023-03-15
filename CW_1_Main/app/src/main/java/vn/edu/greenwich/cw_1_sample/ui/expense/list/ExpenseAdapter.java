package vn.edu.greenwich.cw_1_sample.ui.expense.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import vn.edu.greenwich.cw_1_sample.R;
import vn.edu.greenwich.cw_1_sample.models.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    protected ArrayList<Expense> list;

    public ExpenseAdapter(ArrayList<Expense> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = list.get(position);

        holder.listItemExpenseDate.setText(expense.getExpenseService());
        holder.listItemExpenseTime.setText(expense.getExpenseTime());
        holder.listItemExpenseType.setText(expense.getExpensePlace());
        holder.listItemExpenseContent.setText(expense.getExpenseCost());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView listItemExpenseDate, listItemExpenseTime, listItemExpenseType, listItemExpenseContent;

        public ViewHolder(View itemView) {
            super(itemView);

            listItemExpenseDate = itemView.findViewById(R.id.listItemExpenseDate);
            listItemExpenseTime = itemView.findViewById(R.id.listItemExpenseTime);
            listItemExpenseType = itemView.findViewById(R.id.listItemExpenseType);
            listItemExpenseContent = itemView.findViewById(R.id.listItemExpenseContent);
        }
    }
}