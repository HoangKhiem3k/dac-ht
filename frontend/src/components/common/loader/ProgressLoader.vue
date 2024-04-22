<script setup>
import { storeToRefs } from "pinia";
import { useCommonStore } from "@/stores/common";

const { progressLoading } = storeToRefs(useCommonStore());
const props = defineProps({
  color: {
    type: String,
    default: "#009fe8",
  },
});
</script>
<template>
  <v-progress-linear
    :active="progressLoading > 0"
    :indeterminate="progressLoading > 0"
    color="deep-purple-accent-4"
    bottom
  ></v-progress-linear>
</template>