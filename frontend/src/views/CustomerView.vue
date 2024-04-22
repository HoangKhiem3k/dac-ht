<script setup lang="ts">
import FilterCustomer from "../components/customer/FilterCustomer.vue";
import CustomerDataTable from "../components/customer/CustomerDataTable.vue";
import Pagination from "../components/common/pagination/Pagination.vue";
import { useCustomerStore } from "../stores/customer";
import { computed, ref } from "vue";

const customerStore = useCustomerStore();

// Handle paginate
const totalRecords = computed(() => customerStore.totalCustomers);
const pageNumber = ref(1);
const pageSize = ref(10);
const pageSizeOptions = ref([10, 20, 50]);
const handleChangePageOption = (page) => {
  customerStore.setSearchParams({
    ...customerStore.searchParams,
    pageNum: page.pageNumber,
    pageSize: page.pageSize,
  });
  customerStore.getCustomers();
};
const handleResetPageNumber = () => {
  pageNumber.value = 1;
};
// Handle paginate
</script>

<template>
  <!-- Filter section -->
  <FilterCustomer @handleResetPageNumber="handleResetPageNumber" />
  <!-- Filter section -->

  <!-- Data table section -->
  <CustomerDataTable />
  <Pagination
    :totalRecords="totalRecords"
    :pageNumber="pageNumber"
    :pageSize="pageSize"
    :pageSizeOptions="pageSizeOptions"
    @handleChangePageOption="handleChangePageOption"
  />
  <!-- Data table section -->
</template>
<style lang="scss" scoped></style>
