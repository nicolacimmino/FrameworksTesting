

export class ApiError {
    public message: string;

    public httpStatus: number;

    public toApiFormat() {
        return {
            "status": "ERROR",
            "message": this.message
        }
    }
}