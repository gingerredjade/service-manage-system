<template>
  <div class="kt-subheader kt-grid__item" id="kt_subheader">
    <div
      class="kt-container"
      v-bind:class="{ 'kt-container--fluid': widthFluid }">
      <div class="kt-subheader__main">
        <h3 class="kt-subheader__title">
          {{ title }}
        </h3>
        <div class="kt-subheader__breadcrumbs">
          <router-link :to="'/'" class="kt-subheader__breadcrumbs-home"
            ><i class="flaticon2-shelter"></i
          ></router-link>

          <template v-for="(breadcrumb, i) in breadcrumbs">
            <span
              :key="`${i}-${breadcrumb.id}`"
              class="kt-subheader__breadcrumbs-separator"
            ></span>
            <router-link
              v-if="breadcrumb.route"
              :key="i"
              :to="breadcrumb.route"
              class="kt-subheader__breadcrumbs-link">
              {{ breadcrumb.title }}
            </router-link>
            <span
              class="kt-subheader__desc"
              :key="i"
              v-if="!breadcrumb.route"
              >{{ breadcrumb.title }}</span>
          </template>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "KTSubheader",
  props: {
    breadcrumbs: Array,
    title: String
  },
  computed: {
    ...mapGetters(["layoutConfig"]),

    /**
     * Check if subheader width is fluid
     */
    widthFluid() {
      return this.layoutConfig("subheader.width") === "fluid";
    }
  }
};
</script>
