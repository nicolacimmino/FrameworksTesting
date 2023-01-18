<script>
    import Expense from "./Expense.svelte";
    import {createEventDispatcher} from "svelte";

    const dispatch = createEventDispatcher();

    export let expenses = [];

    function handleExpenseSelected(event) {
        dispatch("expenseSelected", {id: event.detail.id});
    }

</script>

<div class="expenseTable">
    <div class="expensesHeader">
        <div>Date</div>
        <div>Description</div>
        <div>Amount</div>
    </div>
    {#each expenses as expense}
        <Expense expense={expense} on:selected={handleExpenseSelected}/>
    {/each}
</div>

<style>
    .expenseTable {
        /*border: 1px solid lightgrey;*/
        border-radius: 10px;
        background-color: white;
    }

    .expensesHeader {
        border-radius: 10px 10px 0 0;
        display: grid;
        grid-template: 10px / 0.6fr 1.4fr 1fr;
        text-align: left;
        /*border-bottom: 1px solid black;*/
        padding: 15px;
        background-color: lightsteelblue;
        text-transform: uppercase;
    }
</style>