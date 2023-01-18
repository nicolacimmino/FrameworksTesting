<template>
  <div v-if="editedExpense">
    <table>
      <tr>
        <td>
          <input v-model="editedExpense.description">
        </td>
        <td>
          <input v-model="editedExpense.amount"> {{ editedExpense.currency }}
        </td>
      </tr>
      <tr>
        <td>
          <input type="button" value="Save" v-on:click="saveExpense">
        </td>
        <td>
          <input type="button" value="Delete">
        </td>
      </tr>
    </table>
  </div>
  <div v-else>
    No Expense Selected.
  </div>
</template>

<script>

export default {
  props: {
    expense: Object
  },
  data() {
    return {
      editedExpense: undefined
    }
  },
  updated() {
    this.editedExpense = this.expense
  },
  methods: {
    saveExpense() {
      this.$emit("save-expense", this.editedExpense);
    }
  }
}
</script>
