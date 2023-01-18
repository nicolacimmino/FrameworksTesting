import React from 'react';
import ExpenseList from "./ExpenseList";
import ExpenseDetails from "./ExpenseDetails";
import ExpensesApi from "./ExpensesApi";

class ExpenseApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            expenses: undefined,
            selectedExpense: undefined
        }
    }

    componentDidMount() {
        let api = new ExpensesApi();

        api.getExpenses()
            .then((expenses) => {
                this.setState({
                    ...this.state,
                    expenses: expenses
                });
            });
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <ExpenseList expenses={this.state.expenses} onExpenseSelect={(expense) => {
                            this.setState({
                                ...this.state,
                                selectedExpense: expense,
                            })
                        }}/>
                    </div>
                    <div className="col">
                        <ExpenseDetails
                            expense={this.state.selectedExpense}
                            onExpenseUpdate={(expense) => {
                                let api = new ExpensesApi();

                                api.updateExpense(expense)
                                    .then(() => {
                                        this.setState({
                                            ...this.state,
                                            selectedExpense: expense
                                        })
                                    });
                            }}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default ExpenseApp;