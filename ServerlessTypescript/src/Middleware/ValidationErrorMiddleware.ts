import { ValidationError } from "express-json-validator-middleware";


export class ValidationErrorMiddleware {
    static handle(error, request, response, next) {
        if (response.headersSent) {
            return next(error);
        }

        if (!(error instanceof ValidationError)) {
            return next(error);
        }

        response.status(400).json({
            errors: error.validationErrors,
        });        
    }
}