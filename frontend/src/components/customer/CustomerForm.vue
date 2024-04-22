<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { useField, useForm } from "vee-validate";
import { useCustomerStore } from "../../stores/customer";
import { type TagCreate, type Tag } from "@/types/tag";
import TagFilter from "../tag/TagFilter.vue";
import CommonButton from "../common/button/CommonButton.vue";

onMounted(() => {
  if (props.type === "update") {
    tags.value = props.tags;
  } else {
    tags.value = [];
  }
});
const props = defineProps({
  type: String,
  tags: {
    type: Array,
    default: [],
  },
  customerNameUpdate: {
    type: String,
    default: "",
  },
  customerIdUpdate: {
    type: Number,
    default: null,
  },
});
const emit = defineEmits(["hideModal"]);
const customerStore = useCustomerStore();
const { handleSubmit, handleReset } = useForm({
  validationSchema: {
    name(value) {
      if (value && value.length >= 2 && value.length <= 20) return true;
      if (!value) {
        return "Name is required.";
      } else if (value.length < 2) {
        return "Name needs to be at least 2 characters.";
      } else {
        return "Name cannot exceed 20 characters.";
      }
    },
  },
});
const name = useField("name");
onMounted(() => {
  if (props.type === "update") {
    name.value.value = props.customerNameUpdate;
  } else {
    return;
  }
});
const tags = ref<Tag[]>([]);
const tagsSelected = ref<Tag[]>(props.tags);

const submit = handleSubmit((values) => {
  if (props.type == "update") {
    handleUpdateCustomer();
  } else {
    handleCreateCustomer();
  }
});
const handleUpdateCustomer = () => {
  const customerUpdate = {
    id: props.customerIdUpdate,
    customerName: name.value.value,
    tags: tagsSelected.value,
  };
  emit("hideModal");
  customerStore.updateCustomer(customerUpdate);
};

const handleCreateCustomer = () => {
  const dataCustomerCreate = {
    customerName: name.value.value,
    tags: tagsSelected.value,
  };
  emit("hideModal");
  customerStore.addCustomer(dataCustomerCreate);
};
const getTagInfoForCreateCustomer = (tags) => {
  tagsSelected.value = tags;
};
</script>
<template>
  <form @submit.prevent="submit">
    <p>Input customer name</p>
    <v-text-field
      density="compact"
      label="Customer name"
      variant="solo"
      single-line
      :error-messages="name.errorMessage.value"
      v-model="name.value.value"
    ></v-text-field>
    <div class="tags-update" style="width: 460px; margin-top: 20px">
      <p>Select existing tags or create a new one</p>
      <TagFilter
        @dataTagsFilter="getTagInfoForCreateCustomer"
        :tagsSelected="tagsSelected"
      />
    </div>
    <div class="actions d-flex justify-end w-100 mt-5">
      <CommonButton
        background-color="var(--color-blue-1)"
        title="Submit"
        @onClick="submit"
      />
      <CommonButton
        background-color="var(--color-red-1)"
        title="Close"
        @onClick="$emit('hideModal')"
        marginLeft="10px"
      />
    </div>
  </form>
</template>
<style lang="scss"></style>
