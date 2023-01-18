import { ApiError } from "../Errors/ApiError";
import { ApiRequest } from "../Requests/ApiRequest";

export abstract class Controller {
    protected res;

    protected request: ApiRequest;

    protected abstract doInvoke();

    public constructor(res) {
        this.res = res;
    }

    public async invoke() {
        try {
            this.request.parse();

            await this.doInvoke();
        } catch (error) {
            if (error instanceof ApiError) {
                this.res.status(error.httpStatus).json(error.toApiFormat());
            }
            this.internalError(error);
        }
    }

    public success(data: any) {
        this.res.status(200).json({
            result: data,            
        });
    }
    public notFound() {
        this.res.status(404).json({ error: "NOT_FOUND" });
    }

    public internalError(error: Error) {
        console.log(error);
        this.res.status(500).json({ error: "INTERNAL" });
    }
}