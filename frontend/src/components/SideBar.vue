<template>
    <div class="side-bar">

        <Compose />
        
        <v-btn
        prepend-icon="mdi-account"
        color="teal" 
        @click="navigateTo('contacts')"
        class="mt-3"
        >Contacts</v-btn>
        
        <v-list :value="activeButton">
        <v-list-item 
        prepend-icon="mdi-inbox" 
        title="Inbox" 
        value="inbox"
        @click="navigateTo('inbox')"
        ></v-list-item>
        <v-list-item 
        prepend-icon="mdi-send" 
        title="Send" 
        value="send" 
        @click="navigateTo('sent')"
        ></v-list-item>
        <v-list-item 
        prepend-icon="mdi-label-variant-outline" 
        title="Important" 
        value="important"  
        @click="navigateTo('important')"
        ></v-list-item>
        <v-list-item 
        prepend-icon="mdi-file" 
        title="Draft" 
        value="draft" 
        @click="navigateTo('draft')"
        ></v-list-item>
        <v-list-item 
        prepend-icon="mdi-trash-can-outline" 
        title="Trash" 
        value="trash" 
        @click="navigateTo('trash')"
        ></v-list-item>
        <v-list-group value="Labels">
      <template v-slot:activator="{ props }">
        <v-list-item v-bind="props"  title="Labels">
          <!-- Add input for adding new label -->
          <v-text-field v-model="newLabel" @input="onNewLabelInput" placeholder="New Label"></v-text-field>
          <v-btn @click="addNewLabel" icon>
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-list-item>
      </template>

      <!-- Display existing labels -->
      <v-list-item v-for="label in labels" :key="label" :title="label" :value="label" @click="navigateTo(label)">
        <!-- Add button for deleting label -->
        <v-btn @click="deleteLabel(label)" icon>
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-list-item>
    </v-list-group>
  </v-list>
  </div>
</template>

<script>
import Compose from './Compose.vue'
export default {
    name: 'SideBar',
     props: ['user'], 
    components:{Compose},
    data(){
        return{
             labels: [],
             newLabel: '',

        }
    },
    methods: {
        navigateTo(folderName){
            this.$emit('navigateTo', folderName);
            // console.log(folderName);
        },
        //fetch the labels from the server
        async fetchLabels(){
            fetch('http://localhost:3000/labels')
            .then(response => response.json())
            .then(data => {
                this.labels = data;
            })
            .catch(error => console.log(error));
        },
        addNewLabel() {
      if (this.newLabel.trim() !== '') {
        this.labels.push(this.newLabel.trim());
        this.newLabel = ''; // Clear the input field
      }
    },

    // Delete a label
    deleteLabel(label) {
      const index = this.labels.indexOf(label);
      if (index !== -1) {
        this.labels.splice(index, 1);
      }
    },

    },
    mounted(){
        this.fetchLabels();
    }
}
</script>

<style scoped>
.side-bar{
    display: flex;
    flex-direction: column;
    padding: 5vh 2vh  2vh 2vh;
}
.compose{
    display: flex;
    justify-content: space-between;
}
.active{
    background: rgb(238, 159, 159);
}
</style>