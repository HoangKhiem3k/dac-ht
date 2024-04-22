<script setup>
import { ref } from "vue";

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  titleModal: String,
  headerColor: { type: String, default: "#fff" },
  bodyColor: { type: String, default: "#f3f6f8" },
  textColor: { type: String, default: "black" },
  showAction: { type: Boolean, default: true },
});

const emit = defineEmits(["hideModal"]);
</script>
<template>
  <div class="modal-confirm">
    <v-dialog persistent v-model="props.show" max-width="500px">
      <v-card>
        <v-card-title
          class="modal-confirm_header d-flex justify-space-between align-center"
          :style="{ background: headerColor }"
        >
          <p class="font-weight-bold" :style="{ color: textColor }">
            {{ titleModal }}
          </p>
          <v-icon
            icon="mdi-close-thick"
            class="close-icon"
            @click="$emit('hideModal')"
            size="20"
          />
        </v-card-title>
        <v-card-text
          class="modal-confirm_body pa-5"
          :style="{ background: bodyColor }"
        >
          <slot></slot>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<style lang="scss" scoped>
.modal-confirm {
  &_header {
    height: 30px;
    transform: rotate(0deg);
    transition: 0.2s;
    background: v-bind(headerColor);

    p {
      font-size: 15px;
    }
    .close-icon {
      transition: 0.2s;
      &:hover {
        transform: rotate(90deg);
        transition: 0.2s;
        color: var(--color-gray-2);
      }
    }
  }

  &_body {
    height: 100%;
    background: #f3f6f8;

    .actions {
      gap: 5px;
    }
  }
}
</style>
