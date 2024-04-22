<script lang="ts" setup>
const props = defineProps({
  backgroundColor: {
    type: String,
    default: "var(--color-green-1)",
  },
  icon: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "Button",
  },
  textColor: {
    type: String,
    default: "var(--color-neutral-0)",
  },
  marginLeft: {
    type: String,
    default: "0",
  },
  marginRight: {
    type: String,
    default: "0",
  },
});
const emit = defineEmits(["onClick"]);
const handleOnClick = () => {
  emit("onClick");
};
</script>
<template>
  <v-btn
    variant="flat"
    :color="props.backgroundColor"
    :text-color="props.textColor"
    :style="{
      color: props.textColor,
      marginLeft: props.marginLeft,
      marginRight: props.marginRight,
    }"
    @click="handleOnClick"
  >
    <template v-if="props.icon !== ''" v-slot:prepend>
      <v-icon :size="16" :icon="props.icon" :color="props.textColor" />
    </template>
    {{ props.title }}
  </v-btn>
</template>
