import {GetExpenseRequest} from "../Requests/GetExpenseRequest";
import {Controller} from "./Controller";
import {ExpensesRepository} from "../Repositories/ExpensesRepository";
import {ExpenseApiTransformer} from "../Transformers/ExpenseApiTransformer";

export class GetExpenseController extends Controller {
    protected request: GetExpenseRequest;

    public constructor(request: GetExpenseRequest, res) {
        super(res);
        this.request = request;
    }


    protected async doInvoke() {

        const expense = await ExpensesRepository.getByLocator(this.request.id);

        if (!expense) {
            this.notFound();

            return;
        }

        this.success(ExpenseApiTransformer.toApi(expense));
    }
}