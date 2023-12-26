<template>
<div class="show">
  <NavBar />
  <div class="toolbar">
  <v-toolbar>
    <template v-slot:prepend v-if="$vuetify.display.smAndUp">
        <router-link to="/">
        <v-btn icon="mdi-arrow-left"></v-btn>
    </router-link>
        <v-btn icon="mdi-archive-plus-outline" class="ms-5"></v-btn>

        <v-btn icon="mdi-alert-circle-outline"></v-btn>

        <v-btn icon="mdi-delete-outline"></v-btn>
        <template v-if="$vuetify.display.smAndUp">
            <v-divider
            class="mx-3 align-self-center"
            length="24"
            thickness="2"
            vertical
            ></v-divider>

        <v-btn icon="mdi-folder-outline"></v-btn>

        <v-btn icon="mdi-tag-outline"></v-btn>

<div class="pagination">
    <v-btn icon @click="prevPage" :disabled="currentPage === 1">
      <v-icon>mdi-chevron-left</v-icon>
    </v-btn>
    <span>Email {{ currentPage }}</span>
    <v-btn icon @click="nextPage" :disabled="currentPage === totalPages">
      <v-icon>mdi-chevron-right</v-icon>
    </v-btn>
  </div>
        </template>
    </template>
  </v-toolbar>
  <div class="container">
  <div class="subject">
    <br>
  <h1>Subject:</h1>
</div>
<div class="from">
  <h3>From:</h3>
  <h3>Date:</h3>
</div>
<div class="email">
    <br>
  <h3>email:</h3>
</div>
<div class="forwardandreply">
  <v-container>
    <v-row justify="center">
      <v-col cols="auto">
  <v-btn
  color="blue"
  >
  reply
</v-btn>
</v-col>
<v-col cols="auto">
    <v-btn
    color="blue"
    >
  forward
</v-btn>
</v-col>
    </v-row>
  </v-container>
</div>
</div>
</div>
<div class="Frombutton">
      <v-list :opened="openfrom">
        <v-list-group>
          <template v-slot:activator="{ props }">
                <v-btn icon="mdi-dots-vertical" v-bind="props"></v-btn>
          </template>

            <v-list-item
              v-for="(title, i) in crudsfrom"
              :key="i"
              :value="title"
              :title="title"
            ></v-list-item>
          </v-list-group>
      </v-list>
      <div class="refreshbutton">
      <v-btn icon @click="refreshPage">
          <v-icon>mdi-refresh</v-icon>
        </v-btn>
    </div>
</div>
</div>
</template>

<script>
  import NavBar from '../../components/NavBar.vue';
  export default {
    components:{NavBar},

}
</script>

<style>

.show{
display: flex;
}

.toolbar{
/* background-color: aqua; */
width: 60%;
}

.Frombutton{
    z-index: 1000;
    display: flex;
}
.refreshbutton{
    margin-top: 1vh;
    margin-left: 2vh;
}
.from{
  display: flex;
  justify-content:space-between
}
.forwardandreply{
  display: flex;
  margin-top: 450px;
}
.container{
  display: flex;
  flex-direction: column;
}
</style>