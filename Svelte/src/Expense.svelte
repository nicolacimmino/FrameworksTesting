<script>
    import {createEventDispatcher} from "svelte";
    import moment from "moment";

    const dispatch = createEventDispatcher();

    export let expense

    function handleClickExpense() {
        dispatch("selected", {id: expense.id});
    }

</script>

<div class="expenseRow" on:click={handleClickExpense}>
    <div class="expensedate">
        {moment(expense.created_at).format("DD/MM/YYYY")}<br>
        {moment(expense.created_at).format("hh:mm")}
    </div>
    <div>
        {expense.description}
    </div>
    <div>
        {expense.amount} {expense.currency}
    </div>
</div>

<style>
    .expenseRow {
        display: grid;
        grid-template: 50px / 0.6fr 1.4fr 1fr;
        text-align: left;
        border-bottom: 1px solid lightsteelblue;
        padding: 15px;
    }

    .expenseRow:nth-last-child(1) {
        border-bottom: none;
    }

    .expensedate:first-line {
        font-weight: bold;
        color: black;
    }

    .expensedate:nth-child(1) {
        font-weight: normal;
        color: darkgrey;
    }
</style>