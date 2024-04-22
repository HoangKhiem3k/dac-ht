<script setup>
import { computed } from "vue";
import { useCommonStore } from "@/stores/common.ts";

const commonStore = useCommonStore();
const toastMessage = computed(() => {
  return commonStore.getToast;
});
</script>
<template>
  <div class="toast-message">
    <v-snackbar
      v-model="toastMessage.show"
      :timeout="toastMessage.timeout"
      :color="toastMessage.color"
      :location="'top'"
      class="text-subtitle-3"
    >
      <div class="font-weight-bold d-flex justify-center">
        <v-icon
          :icon="toastMessage.icon ?? 'mdi-check-circle-outline'"
          size="20"
          class="mr-2"
        ></v-icon>
        <v-list class="py-0 text-white" :bg-color="toastMessage.color">
          <v-list-item
            class="text-white pt-0"
            v-for="item in toastMessage.messages"
            :key="item"
            :title="item"
            min-height="20px"
          ></v-list-item>
        </v-list>
      </div>
      <v-icon
        icon="mdi-close-circle"
        size="25"
        class=""
        @click="toastMessage.show = false"
      ></v-icon>
    </v-snackbar>
  </div>
</template>

<style lang="scss" scoped>
:deep .v-snackbar__content {
  display: flex;
  justify-content: space-between;
  color: white !important;
  .v-list-item-title {
    white-space: initial;
  }
}
</style>
