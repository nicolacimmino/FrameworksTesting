import Log from "./Log";
import axios from "axios";

export default class ExpensesApi {
    static async getAll() {
        Log.add("Get all expenses.");

        return axios({
            method: "get",
            url: "http://127.0.0.1:8002/api/expenses/",
        }).then((response) => {
            return response.data;
        });

    }

    static async get(id) {
        Log.add("Get an expense.");

        return axios({
            method: "get",
            url: `http://127.0.0.1:8002/api/expenses/${id}`,
        }).then((response) => {
            return response.data;
        });
    }

    static async update(expense) {
        Log.add("Save an expense.");

        return axios({
            method: "patch",
            url: `http://127.0.0.1:8002/api/expenses/${expense.id}`,
            data: expense
        }).then((response) => {
            return response.data;
        });
    }

    static async delete(id) {
        Log.add("Delete an expense.");

        return axios({
            method: "delete",
            url: `http://127.0.0.1:8002/api/expenses/${id}`,
        }).then((response) => {
            return response.data;
        });
    }
}
