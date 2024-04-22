<script lang="ts" setup>
import { ref } from "vue";
import Modal from "../../components/common/modal/Modal.vue";
import { useCustomerStore } from "../../stores/customer";
import TagFilter from "../tag/TagFilter.vue";
import CommonButton from "../common/button/CommonButton.vue";
import { useField, useForm } from "vee-validate";
const customerStore = useCustomerStore();

// Handle add customer
const showModalAddCustomer = ref<boolean>(false);
// Handle search customer
const customerName = ref<string>("");
const handleChangeInputCustomerName = () => {
  customerStore.setSearchParams({
    ...customerStore.searchParams,
    customerName: customerName.value,
  });
};
const getTagInfoForFilterCustomer = (tags) => {
  const filteredTagIds = tags.filter((obj) => obj.id !== null);
  customerStore.setSearchParams({
    ...customerStore.searchParams,
    tagIds: filteredTagIds.map((obj) => Number(obj.id)),
  });
};
const { handleSubmit, handleReset } = useForm({
  validationSchema: {
    insertDateFrom(value) {
      if (
        insertDateTo.value.value &&
        new Date(value) > new Date(insertDateTo.value.value)
      ) {
        return "Start date must be smaller than End date.";
      }
      return true;
    },
    insertDateTo(value) {
      if (
        insertDateFrom.value.value &&
        new Date(value) < new Date(insertDateFrom.value.value)
      ) {
        return "End date must be greater than Start date.";
      }
      return true;
    },
    updateDateFrom(value) {
      if (
        updateDateTo.value.value &&
        new Date(value) > new Date(updateDateTo.value.value)
      ) {
        return "Start date must be smaller than End date.";
      }
      return true;
    },
    updateDateTo(value) {
      if (
        updateDateFrom.value.value &&
        new Date(value) < new Date(updateDateFrom.value.value)
      ) {
        return "End date must be greater than Start date.";
      }
      return true;
    },
  },
});
const insertDateFrom = useField("insertDateFrom");
const insertDateTo = useField("insertDateTo");
const updateDateFrom = useField("updateDateFrom");
const updateDateTo = useField("updateDateTo");
const handleSearch = handleSubmit((values) => {
  customerStore.setSearchParams({
    ...customerStore.searchParams,
    pageNum: 0,
    insertDateFrom: insertDateFrom.value.value
      ? insertDateFrom.value.value
      : "",
    insertDateTo: insertDateTo.value.value ? insertDateTo.value.value : "",
    updateDateFrom: updateDateFrom.value.value
      ? updateDateFrom.value.value
      : "",
    updateDateTo: updateDateTo.value.value ? updateDateTo.value.value : "",
  });

  customerStore.getCustomers();
});
</script>
<template>
  <div class="menu">
    <p>Customer List</p>
    <CommonButton
      background-color="var(--color-green-1)"
      icon="mdi-plus"
      title="Add new customer"
      @onClick="showModalAddCustomer = true"
    />
  </div>
  <form @submit.prevent="submit">
    <div class="filter-customer-tag">
      <div class="search" style="margin-right: 30px">
        <v-text-field
          v-model="customerName"
          append-inner-icon="mdi-magnify"
          density="compact"
          label="Search customer..."
          variant="solo"
          hide-details
          single-line
          @input="handleChangeInputCustomerName"
          style="width: 270px"
        ></v-text-field>
      </div>
      <div
        class="tags-filter"
        style="width: 675px; margin-top: 24px; margin-right: 30px"
      >
        <TagFilter @dataTagsFilter="getTagInfoForFilterCustomer" />
      </div>
      <CommonButton
        background-color="var(--color-orange)"
        icon="mdi-magnify"
        title="Search"
        @onClick="handleSearch"
      />
    </div>
    <div class="filter-date">
      <div class="insert-date">
        <p style="margin-right: 20px">Created date</p>
        <v-text-field
          density="compact"
          label="insertDateFrom"
          variant="solo"
          single-line
          :error-messages="insertDateFrom.errorMessage.value"
          v-model="insertDateFrom.value.value"
          type="date"
          style="width: 170px; margin-top: 25px"
        ></v-text-field>
        <p style="margin: 0 10px">~</p>
        <v-text-field
          density="compact"
          label=" insertDateTo"
          variant="solo"
          single-line
          :error-messages="insertDateTo.errorMessage.value"
          v-model="insertDateTo.value.value"
          type="date"
          style="width: 170px; margin-top: 25px"
        ></v-text-field>
      </div>
      <div class="update-date">
        <p style="margin-right: 20px">Updated date</p>
        <v-text-field
          density="compact"
          label="insertDateFrom"
          variant="solo"
          single-line
          :error-messages="updateDateFrom.errorMessage.value"
          v-model="updateDateFrom.value.value"
          type="date"
          style="width: 170px; margin-top: 25px"
        ></v-text-field>
        <p style="margin: 0 10px">~</p>
        <v-text-field
          density="compact"
          label=" insertDateTo"
          variant="solo"
          single-line
          :error-messages="updateDateTo.errorMessage.value"
          v-model="updateDateTo.value.value"
          type="date"
          style="width: 170px; margin-top: 25px"
        ></v-text-field>
      </div>
    </div>
  </form>
  <!-- Popup add customer -->
  <Modal
    :show="showModalAddCustomer"
    titleModal="Add new customer"
    @hideModal="showModalAddCustomer = false"
  >
    <CustomerForm type="add" @hideModal="showModalAddCustomer = false"
  /></Modal>
  <!-- Popup add customer -->
</template>
<style lang="scss">
.menu {
  padding: 0 150px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  p {
    color: var(--black-color);

    font-size: 25px;
    font-weight: bold;
  }
}
.filter-customer-tag,
.filter-date {
  display: flex;
  align-items: center;
  padding: 0 150px;
  .insert-date,
  .update-date {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    margin-right: 30px;
  }
}
</style>
