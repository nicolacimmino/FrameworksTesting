import {Expense} from "../Models/Expense";


export class ExpensesRepository {

    public static getByLocator(id: string): Expense {
        return {
            id: "123",
            category: "home",
            description: "test purchase",
            amount: 12.2,
            currency: "EUR"
        }
    }
}