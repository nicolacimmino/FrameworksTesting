import axios from "axios";

export default class ExpensesApi {
    static async getAll() {

        return axios({
            method: "get",
            url: "http://127.0.0.1:8002/api/expenses/",
        }).then((response) => {
            return response.data;
        });

    }

    static async get(id) {

        return axios({
            method: "get",
            url: `http://127.0.0.1:8002/api/expenses/${id}`,
        }).then((response) => {
            return response.data;
        });
    }

    static async update(expense) {

        return axios({
            method: "patch",
            url: `http://127.0.0.1:8002/api/expenses/${expense.id}`,
            data: expense
        }).then((response) => {
            return response.data;
        });
    }

    static async delete(id) {

        return axios({
            method: "delete",
            url: `http://127.0.0.1:8002/api/expenses/${id}`,
        }).then((response) => {
            return response.data;
        });
    }
}
