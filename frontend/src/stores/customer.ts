import { defineStore } from "pinia";
import { type Customer } from "@/types/customer";
import { type CustomerSearchParams } from "@/types/filter";
import { useCommonStore } from "@/stores/common";
import { api } from "@/services/http";
import { API_BASE_URL } from "@/config";
import {
  CUSTOMER_LIST_API_URL,
  API_SUCCESS_STATUS_CODE,
} from "@/utils/constants";
import { CUSTOMER_API_URL } from "../utils/constants";

export const useCustomerStore = defineStore({
  id: "customerStore",
  state: () => ({
    customers: [] as Customer[],
    totalCustomers: 0 as Number,
    searchParams: {
      customerName: "",
      tagIds: [],
      insertDateFrom: "",
      insertDateTo: "",
      updateDateFrom: "",
      updateDateTo: "",
      pageNum: 0,
      pageSize: 10,
      sort: {
        property: "insertDatetime",
        asc: false,
      },
    } as CustomerSearchParams,
  }),
  actions: {
    // Set search params
    setSearchParams(params: CustomerSearchParams) {
      this.searchParams = { ...this.searchParams, ...params };
    },
    // Get customers list with search params
    async getCustomers() {
      const { setProgressLoading } = useCommonStore();
      console.log("search params", this.searchParams);

      setProgressLoading(true);
      try {
        const res = await api.post<Customer[]>(
          `${API_BASE_URL}${CUSTOMER_LIST_API_URL}`,
          this.searchParams
        );
        if (res.code === API_SUCCESS_STATUS_CODE) {
          this.customers = res.data.list;
          this.totalCustomers = res.data.total;
        } else {
          this.customers = [];
          this.totalCustomers = 0;
        }
        setProgressLoading(false);
      } catch (e) {
        console.log(e);
      }
    },

    async addCustomer(customer: Customer): Customer {
      const { setProgressLoading, setToastSuccess, setToastError } =
        useCommonStore();
      setProgressLoading(true);
      try {
        const res = await api.post(
          `${API_BASE_URL}${CUSTOMER_API_URL}`,
          customer
        );
        if (res.code === API_SUCCESS_STATUS_CODE) {
          setToastSuccess("Added customer successfully.");
          this.getCustomers();
        } else {
          setToastError(res.message);
        }
        setProgressLoading(false);
      } catch (e) {
        console.log(e);
      }
    },
    async updateCustomer(customer: Customer): Customer {
      const { setProgressLoading, setToastSuccess, setToastError } =
        useCommonStore();
      setProgressLoading(true);
      try {
        const res = await api.put(
          `${API_BASE_URL}${CUSTOMER_API_URL}${customer.id}`,
          {
            tags: customer.tags,
            customerName: customer.customerName
          }
        );
        if (res.code === API_SUCCESS_STATUS_CODE) {
          setToastSuccess("Updated successfully.");
          this.getCustomers();
        } else {
          setToastError(res.message);
        }
        setProgressLoading(false);
      } catch (e) {
        console.log(e);
      }
    },
    async deleteCustomer(id) {
      const { setProgressLoading, setToastSuccess, setToastError } =
        useCommonStore();
      setProgressLoading(true);
      try {
        const res = await api.delete(`${API_BASE_URL}${CUSTOMER_API_URL}${id}`);
        if (res.code === API_SUCCESS_STATUS_CODE && res.data === true) {
          setToastSuccess("Deleted successfully.");
          this.getCustomers();
        } else {
          setToastError(res.message);
        }
        setProgressLoading(false);
      } catch (e) {
        console.log(e);
      }
    },
  },
});