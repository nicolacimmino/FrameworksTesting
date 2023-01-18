import axios from "axios";

class ExpensesApi {

    getExpenses() {
        return axios({
            method: 'get',
            url: 'http://127.0.0.1:8002/api/expenses/',
        }).then((response) => {
            return response.data;
        });
    }

    updateExpense(expense) {
        return axios({
            method: 'patch',
            url: 'http://127.0.0.1:8002/api/expenses/' + expense.id,
            data: expense
        }).then((response) => {
            return response.data;
        });
    }
}

export default ExpensesApi;

