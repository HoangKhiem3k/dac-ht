<script setup>
import { ref } from "vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import ProgressLoader from "@/components/common/loader/ProgressLoader.vue";
</script>

<template>
  <v-app>
    <!-- Header -->
    <Header />
    <!-- end Header -->

    <!-- Main Content -->
    <v-main class="main-content">
      <ProgressLoader />
      <div class="main-content-body">
        <slot></slot>
      </div>

      <!-- Footer -->
      <Footer />
      <!-- end Footer -->
    </v-main>
    <!-- end Main Content -->
  </v-app>
</template>

<style lang="scss" scoped>
.main-content {
  .main-content-body {
    max-width: 100%;
    height: calc(100vh - 108px);
  }
}
</style>
