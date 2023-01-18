import { ApiError } from "./ApiError";


export class InputInvalidApiError extends ApiError {
    public constructor(message: string) {
        super();
        this.httpStatus = 400;
        this.message = message;
    }
}