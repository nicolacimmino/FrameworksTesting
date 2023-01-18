import {default as express} from "express";
import {default as serverless} from "serverless-http";
import {GetExpenseController} from "./src/Controllers/GetExpenseController";
import {GetExpenseRequest} from "./src/Requests/GetExpenseRequest";
import {ValidationErrorMiddleware} from "./src/Middleware/ValidationErrorMiddleware";

const {Validator} = require("express-json-validator-middleware");

const {validate} = new Validator();

const app = express();

app.use(express.json());

app.get(
    "/api/expenses/:id",
    validate({params: GetExpenseRequest.schema}),
    async function (req, res) {
        (new GetExpenseController(new GetExpenseRequest(req), res)).invoke();
    });

app.use(ValidationErrorMiddleware.handle);

app.use((req, res, next) => {
    return res.status(404).json({
        error: "Not Found",
    });
});

module.exports.handler = serverless(app);
