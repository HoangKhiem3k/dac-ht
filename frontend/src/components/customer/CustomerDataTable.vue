<script lang="ts" setup>
import { ref, onMounted, computed } from "vue";
import ConfirmModal from "../common/modal/ConfirmModal.vue";
import CustomerForm from "./CustomerForm.vue";
import Modal from "../common/modal/Modal.vue";
import Pagination from "../common/pagination/Pagination.vue";
import Tag from "../tag/Tag.vue";
import { useCustomerStore } from "../../stores/customer";
import { type Customer } from "@/types/customer";

const customerStore = useCustomerStore();
onMounted(() => {
  customerStore.getCustomers();
  sort.value = sortDefault.value;
});

const customersList = computed(() => customerStore.customers);
const showModalConfirm = ref<boolean>(false);
const showModalUpdateCustomer = ref<boolean>(false);
const customerDeleteId = ref<number>(0);
const customerDetail = ref(null);
const openModalConfirmDelete = (id) => {
  showModalConfirm.value = true;
  customerDeleteId.value = id;
};
const handleDeleteCustomer = () => {
  showModalConfirm.value = false;
  customerStore.deleteCustomer(customerDeleteId.value);
};
const openModalUpdateCustomer = (customer) => {
  showModalUpdateCustomer.value = true;
  customerDetail.value = customer;
};

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
// Handle paginate
// Handle sort

const sortDefault = computed(() => customerStore.searchParams.sort);
const sort = ref(sortDefault);
const sortTable = (property) => {
  sort.value.property = property;
  sort.value.asc = !sort.value.asc;
  customerStore.setSearchParams({
    ...customerStore.searchParams,
    sort: {
      property: sort.value.property,
      asc: sort.value.asc,
    },
  });
  customerStore.getCustomers();
};
</script>
<template>
  <div class="data-table" style="padding: 0 150px">
    <v-table fixed-header height="45vh">
      <thead>
        <tr>
          <th
            class="text-left customer-name"
            @click="sortTable('customerName')"
          >
            Name
            <span v-if="sort.property === 'customerName'">
              <v-icon
                :size="16"
                :icon="sort.asc === true ? 'mdi-arrow-up' : 'mdi-arrow-down'"
                :color="'var(--color-blue-1)'"
              />
            </span>
          </th>
          <th
            class="text-left insert-time"
            @click="sortTable('insertDatetime')"
          >
            Created time
            <span v-if="sort.property === 'insertDatetime'">
              <v-icon
                :size="16"
                :icon="sort.asc === true ? 'mdi-arrow-up' : 'mdi-arrow-down'"
                :color="'var(--color-blue-1)'"
              />
            </span>
          </th>
          <th
            class="text-left update-time"
            @click="sortTable('updateDatetime')"
          >
            Updated time
            <span v-if="sort.property === 'updateDatetime'">
              <v-icon
                :size="16"
                :icon="sort.asc === true ? 'mdi-arrow-up' : 'mdi-arrow-down'"
                :color="'var(--color-blue-1)'"
              />
            </span>
          </th>
          <th class="text-left">Tags</th>
          <th class="text-left">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in customersList" :key="item.id">
          <td style="width: 20%">{{ item.customerName }}</td>
          <td style="width: 20%">{{ item.insertDatetime }}</td>
          <td style="width: 20%">{{ item.updateDatetime }}</td>
          <td style="width: 30%">
            <span
              v-if="item.tags && item.tags.length > 0"
              v-for="(tag, index) in item.tags"
              :key="index"
            >
              <Tag :name="tag.tagName" />
            </span>
            <p v-else>No tags available</p>
          </td>
          <td style="width: 10%">
            <div style="display: flex">
              <CommonButton
                background-color="var(--color-blue-6)"
                icon="mdi-cog"
                title="Update"
                @onClick="openModalUpdateCustomer(item)"
              />
              <CommonButton
                background-color="var(--color-red-1)"
                icon="mdi-trash-can"
                title="Delete"
                @onClick="openModalConfirmDelete(item.id)"
                marginLeft="10px"
              />
            </div>
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>
  <!-- Modal confirm delete -->
  <ConfirmModal
    :show="showModalConfirm"
    titleModal="Confirm deletion of customer"
    messageConfirm="Are you sure you want to delete this customer?"
    typeConfirm="deleteCustomer"
    @hideModal="showModalConfirm = false"
    @makeConfirm="handleDeleteCustomer"
  />
  <!-- Modal update customer -->
  <Modal
    :show="showModalUpdateCustomer"
    titleModal="Update customer"
    @hideModal="showModalUpdateCustomer = false"
  >
    <CustomerForm
      type="update"
      @hideModal="showModalUpdateCustomer = false"
      :tags="customerDetail.tags"
      :customerNameUpdate="customerDetail.customerName"
      :customerIdUpdate="customerDetail.id"
    />
  </Modal>
  <!-- Pagination -->
  <Pagination
    :totalRecords="totalRecords"
    :pageNumber="pageNumber"
    :pageSize="pageSize"
    :pageSizeOptions="pageSizeOptions"
    @handleChangePageOption="handleChangePageOption"
  />
  <!-- Pagination -->
</template>
<style lang="scss">
.customer-name,
.insert-time,
.update-time {
  &:hover {
    cursor: pointer;
  }
}
</style>
