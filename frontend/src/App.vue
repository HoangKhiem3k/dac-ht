<script setup lang="ts">
import { useRoute } from "vue-router";
import { computed, watch, ref } from "vue";
import { RouterView } from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import EmptyLayout from "@/layouts/EmptyLayout.vue";
import ToastMessage from "@/components/common/toast/ToastMessage.vue";

const route = useRoute();

const currentLayout = computed(() => {
  switch (route.meta.layout) {
    case "EmptyLayout":
      return EmptyLayout;
    default:
      return DefaultLayout;
  }
});

const isRouterLoaded = computed(() => {
  if (route.name !== null) return true;
  return false;
});

watch(
  route,
  (to) => {
    document.title = to.meta.title;
  },
  { flush: "pre", immediate: true, deep: true }
);
</script>

<template>
  <v-app>
    <component :is="currentLayout" v-if="isRouterLoaded">
      <RouterView />
    </component>

    <ToastMessage />
  </v-app>
</template>

<style lang="scss">
@import "@assets/base.css";
#app {
  margin: 0 auto;
  font-weight: normal;
}
</style>
