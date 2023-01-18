import React from 'react';

import Expense from "./Expense";

class ExpenseList extends React.Component {

    renderExpense(expense) {
        return (
            <Expense
                key={expense.id} onSelect={() => {
                this.props.onExpenseSelect(expense)
            }}
                value={expense}>
            </Expense>
        )
    }

    render() {

        if (this.props.expenses === undefined) {
            return ("No expenses.")
        }

        let expensesComponents = [];

        this.props.expenses.forEach((expense) => {
            expensesComponents.push(this.renderExpense(expense));
        });

        return (
            <table className="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Amount</th>
                </tr>
                </thead>
                <tbody>
                {expensesComponents}
                </tbody>
            </table>
        );
    }
}

export default ExpenseList;