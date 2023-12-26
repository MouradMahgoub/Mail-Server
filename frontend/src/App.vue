<template>
  <v-app >
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>


<script>
  import NavBar from './components/NavBar.vue';
  export default {
    components:{NavBar},
    methods: {
      handleNavigation(mailList) {
        console.log(mailList);
      }
    }
  }
</script>
<style>
</style>









