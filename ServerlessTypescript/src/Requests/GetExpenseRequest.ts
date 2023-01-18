import { ApiRequest } from "./ApiRequest";

export class GetExpenseRequest extends ApiRequest {
    public id: string;

    public static schema = {
        type: "object",
        required: ["id"],
        properties: {
            id: {
                type: "string",
                minLength: 1,
            },
        },
    };

    protected doParse() {
        const { id } = this.req.params;

        this.id = id;
    }
}