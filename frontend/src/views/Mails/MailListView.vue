<template>
    <div>
        <NavBar @navigateTo="changeList"/>
        
        <v-toolbar>
      <v-text-field v-model="searchQuery" label="Search" class="mx-2"></v-text-field>
      <v-select
        v-model="sortKey"
        :items="showContacts? ['alphabetical'] : ['importance', 'date']"
        label="Sort by"
        class="mx-2"
        clearable
      ></v-select>
      <v-select
        v-show="!showContacts"
        v-model="filterKey"
        :items="['from', 'subject']"
        label="flter by"
        class="mx-2"
        clearable
      ></v-select>
    </v-toolbar>

        <v-toolbar v-if="selectedMails.length > 0">
        <v-btn color="error" @click="deleteSelectedMails">
            <v-icon>mdi-delete</v-icon>
            Delete
        </v-btn>
            <v-icon>mdi-folder-move</v-icon>
        <v-select
            v-model="selectedFolders"
            :items="folders"
            label="Move to"
            class="mx-2"
            multiple
            clearable
        ></v-select>

        </v-toolbar>

        <div v-show="showContacts">
            <ContactView />
        </div>
        <div v-show="!showContacts">
            <v-list class="mail-list">
                <div v-for="mail in user?.folders.inbox.emails" :key="mail"     class="mail">
                    <v-checkbox
                    v-model="selectedMails"
                    :label="mail"
                    :value="mail"
                    ></v-checkbox> 
                    <v-list-item :value="mail"  @click="df">
                        <div  class="bs">
                            <p class="truncate">{{ mail.from }}</p>
                            <p class="truncate">{{ mail.subject }}</p>
                            <p class="truncate">{{ mail.date }}</p>
                        </div>
                    </v-list-item>
                </div>
            </v-list>
        </div>


</div>
</template>

<script>
  import NavBar from '../../components/NavBar.vue';
  import ContactView from '../ContactView.vue';
  export default {
    components:{NavBar, ContactView},
    data() {
        return {
            sort: null,
            search:null,
            filterKey: null,
            user: null,
            currentFolder: '',
            selectedMails: [],
            // selectedFolder: [],
            currentList: [],
            selectedFolders: [],
            showContacts: false,
            contacts:[],
        }
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        async fetchData(){
            try {
                fetch('http://localhost:3000/user')
                .then(response => response.json())
                .then(data => {
                    this.user = data;
                    console.log(this.user.name);
                });
                
            }catch(e) {
                console.error('Error fetching user data:', e.message);
            }
        },
        //send a post request to the server to change the current folder and recievve a new list to show
        async changeList(folderName){
            console.log(folderName);
            if(folderName === 'contacts'){
                this.showContacts = true;
                console.log(this.showContacts);
            }else{
                console.log(this.showContacts);
                this.showContacts = false;
                await fetch('http://localhost:3000/user', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                    params:{
                        to: folderName,
                    }
                })
                })
                .then(response => response.json())
                .then(data => {
                    this.currentList = data;
                    this.currentFolder = folderName;
                })
                .catch(error => console.error('Error changing list:', error));
            }
        },

        async deleteSelectedMails() {
                await fetch('http://localhost:3000/user', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        params: {
                            emails: this.selectedMails,
                            from: this.currentFolder
                        }
                    })
                })
                .then(response => response.json())
                .then(data => {
                    this.currentList = data;
                })
                .catch(error => console.error('Error deleting selected mails:', error));
      },
       //send a post request to the server to move the selected mails to the selected folders
        async moveSelectedMails() {
                await fetch('http://localhost:3000/user', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        params:{
                            emails: this.selectedMails,
                            from: this.currentFolder,
                            to: this.selectedFolders,
                        }
                    })
                })
                .then(response => response.json())
                .then(data => {
                    this.currentList = data;
                })
                .catch(error => console.error('Error moving selected mails:', error));
        },

    }
}
</script>

<style scoped>
nav {
    z-index: 2;
}
p{
    max-width: 30vh;
}
.v-icon {
    display: flex;
    align-self: center;
    margin-right: 0.5%;
}
.mail{
    display: flex;
    margin: 0.25vh 0 0.5vh 0.5vh;
}
.bs {
    display: flex;
    justify-content: space-between;
}
.v-list-item {
    max-height: 10vh;
    width: 93%;
}
.mail-list{
    z-index: 1;
    /* margin-top: 2vh; */
    height: 87.5vh;
    overflow-y: auto;
}
.home-view {
    display: flex;
}
.mails-nav {
    display: flex;
    flex-direction: column;
    width: 100%;
}
</style>