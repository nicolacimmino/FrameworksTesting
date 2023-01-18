import { body, validationResult } from "express-validator";
import { InputInvalidApiError } from "../Errors/InputInvalidApiError";

export abstract class ApiRequest {
    protected req;

    protected abstract doParse();

    public parse() {
        this.doParse();
    }

    public constructor(req: any) {
        this.req = req;
    }
}