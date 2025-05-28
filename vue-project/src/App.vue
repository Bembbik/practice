<template>
  <Header v-if="isAuthenticated && !isLoginPage" />
  <router-view />
</template>

<script>
import Header from './components/Header.vue'

export default {
  components: {
    Header,
  },
  computed: {
    isLoginPage() {
      return this.$route.path === '/login'
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated
    },
  },
}
</script>
