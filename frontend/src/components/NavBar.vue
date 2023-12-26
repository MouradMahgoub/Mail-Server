<template>
  <nav>      
    <v-app-bar flat app color="rgb(235, 235, 235)">
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-app-bar-title class="text-uppercase">
        <span class="font-weight-light">Mail</span>
        <span>Server</span>
      </v-app-bar-title>
      <v-btn flat color="grey">
        <span>Sign Out </span>
        <v-icon>mdi-logout-variant</v-icon>
      </v-btn>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" app class="indigo">
      <SideBar @navigateTo="changeList" />
    </v-navigation-drawer>
</nav>
</template>

<script>
  import SideBar from  './SideBar.vue';
  export default {
    components:{SideBar},
    data: () => ({
      drawer:true,
    }),
    methods: {
       changeList(folderName){
        // this.$router.push({name:folderName});
        // console.log(folderName);
        this.$emit('navigateTo', folderName);
      },
    },
  }
</script>

<style scoped>
.Frombutton{
    /* border: 2px solid black; */
    width: 17%; 
    height: 10vh; 
}
.Tobutton{
    margin-left: 30px;
    /* border: 2px solid black; */
    width: 17%;     
    height: 10vh; 
}
.Anytimebutton{
    margin-left: 30px; 
    /* border: 2px solid black; */
    width: 17%; 
    height: 10vh; 
}

#hasattachmentbutton {
    margin-left: 30px; 
    background-color: rgb(178, 178, 178); 
    border: none;
    border: 2px solid black;
    color: black;
    cursor: pointer;
    width: 17%; 
    height: 10vh; 
}

#hasattachmentbutton.blue {
      background-color: blue; 
}
#isunreadbutton {
margin-left: 30px; 
  background-color: white; 
  /* padding: 10px; */
  border: none;
  border: 2px solid black;
  color: black;
  cursor: pointer;
  width: 17%; 
height: 10vh; 
}

#isunreadbutton.blue {
      background-color: blue; 
    }
.nav-bar{
    margin-top: 40px;
    display: flex;
    flex-direction: row;
    /* padding: 2vh 2vh  2vh 2vh; */
}
.Search {
    margin-left: 10%;
    margin-top: 10px; /* Adjust the margin-top as needed */
    width: 50%;
  }

  .Search input {
    padding: 5px;
    width: 50%;
    border: 2px solid white;
    background-color: white;
  }
</style>