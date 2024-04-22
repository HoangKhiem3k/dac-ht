<script setup lang="ts">
import { ref, computed, onMounted, watch } from "vue";

const props = defineProps<{
  totalRecords: number;
  pageNumber: number;
  pageSize: number;
  pageSizeOptions: Array;
}>();
const emit = defineEmits(["handleChangePageOption"]);
const pageNumber = ref<number>(props.pageNumber);

const pageSize = ref<number>(props.pageSize);
const totalPage = computed(() =>
  Math.ceil(props.totalRecords / pageSize.value)
);
const handleChangePageSize = () => {
  pageNumber.value = 1;
  handleChangePageNumber();
};
const handleChangePageNumber = () => {
  emit("handleChangePageOption", {
    pageNumber: pageNumber.value - 1,
    pageSize: pageSize.value,
  });
};
</script>
<template>
  <div class="pagination">
    <p>Page size</p>
    <v-select
      style="margin-top: 20px"
      density="compact"
      variant="solo"
      v-model="pageSize"
      :items="props.pageSizeOptions"
      @update:modelValue="handleChangePageSize"
    ></v-select>
    <v-pagination
      v-model="pageNumber"
      size="small"
      variant="flat"
      :length="totalPage"
      :total-visible="6"
      @update:modelValue="handleChangePageNumber"
    ></v-pagination>
    <p>Total records: {{ props.totalRecords }}</p>
  </div>
</template>
<style lang="scss">
.pagination {
  display: flex;
  float: right;
  justify-content: center;
  align-items: center;
  margin-right: 150px;
  margin-top: 20px;
  p {
    margin-right: 20px;
  }
}
</style>
