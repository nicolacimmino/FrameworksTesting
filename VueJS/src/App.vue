<template>
  <div class="mainContainer">
    <ExpensesList :expenses=expenses :loading=loading v-on:expense-selected=editExpense></ExpensesList>
    <ExpenseEditor :expense=selectedExpense v-on:save-expense=saveExpense></ExpenseEditor>
  </div>
</template>

<script>
import ExpensesList from "./components/ExpensesList";
import ExpensesApi from "./ExpensesApi";
import ExpenseEditor from "./components/ExpenseEditor";

export default {
  name: 'App',
  components: {
    ExpenseEditor,
    ExpensesList,
  },
  created() {
    this.loadAllExpenses();
  },
  data() {
    return {
      expenses: [],
      selectedExpense: undefined,
      loading: false
    }
  },
  methods: {
    loadAllExpenses() {
      this.loading = true;
      ExpensesApi.getAll().then(expenses => {
        this.expenses = expenses;
        this.loading = false;
      });
    },
    editExpense(expenseId) {
      ExpensesApi.get(expenseId).then(expense => {
        this.selectedExpense = expense;
      })
    },
    saveExpense(expense) {
      ExpensesApi.update(expense).then(() => {
        this.selectedExpense = undefined;
        this.loadAllExpenses();
      })
    }
  }
}
</script>

<style>
.mainContainer {
  background-color: whitesmoke;
  padding: 20px;
}
</style>