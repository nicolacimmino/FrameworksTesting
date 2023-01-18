<template>
  <div class="expenseTable">
    <div class="expensesHeader">
      <div>Date</div>
      <div>Description</div>
      <div>Amount</div>
    </div>
    <div v-if="loading">
      Loading...
    </div>
    <div v-else>
      <div v-for="expense in expenses" :key="expense.id">
        <Expense :expense=expense v-on:click="onExpenseSelected(expense.id)"></Expense>
      </div>
    </div>

  </div>
</template>

<script>
import Expense from "./Expense";

export default {
  components: {
    Expense
  },
  props: {
    expenses: Array,
    loading: Boolean
  },
  methods: {
    onExpenseSelected(event) {
      this.$emit("expense-selected", event);
    }
  }
}
</script>

<style scoped>
.expenseTable {
  border-radius: 10px;
  background-color: white;
}

.expensesHeader {
  border-radius: 10px 10px 0 0;
  display: grid;
  grid-template: 10px / 0.6fr 1.4fr 1fr;
  text-align: left;
  padding: 15px;
  background-color: lightsteelblue;
  text-transform: uppercase;
}
</style>
