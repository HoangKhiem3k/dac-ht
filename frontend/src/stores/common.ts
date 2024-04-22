import { defineStore } from "pinia";
import { type Toast } from "@/types/toast";

export const useCommonStore = defineStore({
  id: "commonStore",
  state: () => ({
    toastMessage: {} as Toast,
    progressLoading: 0,
  }),
  getters: {
    getToast(state): Toast {
      return state.toastMessage;
    },
  },
  actions: {
    setToastMessage(toastMessage: Toast) {
      this.toastMessage = toastMessage;
    },
    setProgressLoading(loading: boolean) {
      loading
        ? this.progressLoading++
        : this.progressLoading > 0
        ? this.progressLoading--
        : (this.progressLoading = 0);
    },
    setToastError(text: string) {
      this.setToastMessage({
        icon: "mdi-block-helper",
        show: true,
        timeout: 3000,
        color: "red-darken-2",
        messages: [text],
      });
    },
    setToastSuccess(text: string) {
      this.setToastMessage({
        icon: "",
        show: true,
        timeout: 3000,
        color: "light-green",
        messages: [text],
      });
    },
  },
});
