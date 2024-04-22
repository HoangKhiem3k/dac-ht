<script lang="ts" setup>
import { useTagStore } from "../../stores/tag";
import { computed, ref } from "vue";
import { debounce } from "../../utils/debounce";
import { type Tag } from "@/types/tag";

const tagStore = useTagStore();
const emit = defineEmits(["dataTagsFilter"]);
const props = defineProps({
  tagsSelected: {
    type: Array,
    default: [],
  },
});
const convertArray = <T>(arr: (T | Tag)[]): Tag[] => {
  return arr.map((item) => {
    if (typeof item === "object" && "id" in item && "tagName" in item) {
      return item as Tag;
    } else {
      return { id: 0, tagName: item as string };
    }
  });
};

const tags = computed(() => tagStore.tags);
const searchTagInput = ref<string>("");
const tagsSelected = ref(props.tagsSelected);
const searchTags = debounce(async (v: string) => {
  if (v !== "") {
    tagStore.getTags({ tagName: v });
  }
}, 500);
const onChange = () => {
  const convertedArray = convertArray<string | Tag>(tagsSelected.value);
  emit("dataTagsFilter", convertedArray);
};
</script>
<template>
  <v-combobox
    style="width: 100%"
    variant="solo"
    density="compact"
    v-model:search-input="searchTagInput"
    v-model="tagsSelected"
    :items="tags"
    item-title="tagName"
    item-value="id"
    chips
    closable-chips
    clearable
    single-line
    multiple
    @update:search="searchTags"
    @update:modelValue="onChange"
    label="Input tags..."
  ></v-combobox>
</template>

<style lang=""></style>
