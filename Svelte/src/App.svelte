<script>
    import ExpenseEditor from "./ExpenseEditor.svelte";
    import Log from "./Log.svelte";
    import ExpensesList from "./ExpensesList.svelte";
    import ExpensesApi from "./ExpensesApi";

    let selectedExpense;

    let getAllExpensesPromise = getAllExpenses();

    async function getAllExpenses() {
        return await ExpensesApi.getAll();
    }

    async function handleExpenseSelected(event) {
        selectedExpense = await ExpensesApi.get(event.detail.id);
    }

    async function handleExpenseSave(event) {
        selectedExpense = undefined;

        await ExpensesApi.update(event.detail.expense);

        getAllExpensesPromise = getAllExpenses();
    }

    async function handleExpenseDelete(event) {
        selectedExpense = undefined;

        await ExpensesApi.delete(event.detail.id);

        getAllExpensesPromise = getAllExpenses();
    }
</script>

<main>
    <div class="mainContainer">
        <div>
            {#await getAllExpensesPromise}
                <p>...waiting expenses...</p>
            {:then expenses}
                <ExpensesList expenses={expenses} on:expenseSelected={handleExpenseSelected}/>
            {/await}
        </div>

        <div>
            <Log/>
        </div>
    </div>
</main>

{#if selectedExpense !== undefined}
    <div class="modal">
        <div class="modal-content">
            <ExpenseEditor on:save={handleExpenseSave} on:delete={handleExpenseDelete} expense={selectedExpense}/>
        </div>
    </div>
{/if}

<style>
    .mainContainer {
        background-color: whitesmoke;
        padding: 20px;
    }

    .mainContainer div {
        margin: 20px;
    }

    .modal {
        display: block;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0, 0, 0);
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
    }
</style>