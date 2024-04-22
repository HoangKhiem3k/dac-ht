<script lang="ts" setup>
const props = defineProps({
  name: String,
});
</script>
<template>
  <v-chip style="margin: 5px">{{ props.name }}</v-chip>
</template>

<style lang="scss"></style>
