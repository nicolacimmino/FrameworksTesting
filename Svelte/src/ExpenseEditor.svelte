<script>
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    export let expense

    function saveExpense() {
        dispatch("save", {expense: expense});
    }

    function deleteExpense() {
        dispatch("delete", {id: expense.id});
    }
</script>

{#if expense}
    <table>
        <tr>
            <td>
                <input bind:value="{expense.description}">
            </td>
            <td>
                <input bind:value="{expense.amount}"> {expense.currency}
            </td>
        </tr>
        <tr>
            <td>
                <input type="button" on:click={saveExpense} value="Save">
            </td>
            <td>
                <input type="button" on:click={deleteExpense} value="Delete">
            </td>
        </tr>
    </table>
{:else}
    No Expense selected.
{/if}