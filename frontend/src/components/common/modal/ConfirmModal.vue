<script setup>
import { ref } from "vue";

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  titleModal: String,
  messageConfirm: String,
  typeConfirm: String,
  headerColor: { type: String, default: "#fff" },
  bodyColor: { type: String, default: "#f3f6f8" },
  textColor: { type: String, default: "black" },
  showAction: { type: Boolean, default: true },
});

const emit = defineEmits(["hideModal", "makeConfirm"]);

const confirming = ref(false);

function cancel() {
  emit("hideModal");
}

function makeConfirm(type) {
  confirming.value = true;
  emit("makeConfirm", type);
  confirming.value = false;
}
</script>
<template>
  <div class="modal-confirm">
    <v-dialog persistent v-model="props.show" max-width="550px">
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
          <span
            v-for="(line, lineNumber) of messageConfirm.split('\n')"
            :key="lineNumber"
            >{{ line }}<br
          /></span>
          <div class="actions d-flex justify-end w-100 mt-5" v-if="showAction">
            <CommonButton
              background-color="#0f88f2"
              title="OK"
              @onClick="makeConfirm(props.typeConfirm)"
            />
            <CommonButton
              background-color="var(--color-red-1)"
              title="Close"
              @onClick="cancel"
              marginLeft="10px"
            />
          </div>
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
