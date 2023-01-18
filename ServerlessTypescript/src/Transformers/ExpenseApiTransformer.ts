
export class ExpenseApiTransformer {
    public static toApi(data) {
        const { id, category, description, amount, currency } = data;

        return {
            id,
            category,
            description,
            amount,
            currency
        }
    }
}

