<template>
  <!-- show the contacts as a list with a pgination and and make it available to show the contacts details and add a new contacts. besides, edit the contact[rename it, add or delete contact, add or delete emailAddresses and add or delete phoneNums of the contact ]  -->
    <div>
      <v-btn color="orange"  @click="createContact">
        Add contacts
       </v-btn>
      <div v-for="contact in contacts" :key="contact.name" class="contact">
        <v-list-item @click="openContactDialog(contact)">
          <div class="bs">
            <p class="truncate">{{ contact.name }}</p>
            <p class="truncate">{{ contact.emailAddresses[0] }}</p>
            <p class="truncate">{{ contact.phoneNums[0] }}</p>
          </div>
        </v-list-item>
        
        <v-dialog v-model="contactDialog" max-width="700px">
          <v-card>
            <v-card-title>
      {{ editingContact.name ? 'Edit Contact' : 'Create New Contact' }}
    </v-card-title>
            <v-card-text>
              <v-text-field v-model="editingContact.name" label="Name"></v-text-field>

        <v-list >
          <v-divider></v-divider>
          
          <v-list-item class="mb-3">
              <v-chip
              v-for="(email, index) in editingContact.emailAddresses"
              :key="email"
              closable
              @click:close="deleteEmail(index)"
              >
              {{ email }}
            </v-chip>
            
            <v-btn icon @click="addEmailDialog = true">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-list-item>
          <v-divider></v-divider>
          


          <v-list-item class="mb-3">
            <v-chip
            v-for="(phone, index) in editingContact.phoneNums"
            :key="phone"
            closable
            @click:close="deletePhone(index)"
            >
          {{ phone }}
        </v-chip>
        <v-btn icon @click="addPhoneDialog = true">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </v-list-item>
      <v-divider></v-divider>

  </v-list>
  
  <v-dialog v-model="addEmailDialog" max-width="300px">
    <v-card>
      <v-card-title>Add Email</v-card-title>
      <v-card-text>
        <v-text-field v-model="newEmail" label="Email"></v-text-field>
      </v-card-text>
    <v-card-actions>
      <v-btn color="blue darken-1" text @click="addEmail">Add</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>


<v-dialog v-model="addPhoneDialog" max-width="300px">
  <v-card>
    <v-card-title>Add Phone</v-card-title>
    <v-card-text>
      <v-text-field v-model="newPhone" label="Phone"></v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-btn color="blue darken-1" text @click="addPhone">Add</v-btn>
    </v-card-actions>
  </v-card>
</v-dialog>


        </v-card-text>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="changeContacts">Save</v-btn>
          <v-btn color="blue darken-1" text @click="deleteContact">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</div>
  
</template>

<script>
export default {
  data(){
    return{
      contacts:[],
      searchQuery: null,
      sortKey: null,
      filterKey: null,


      
      addEmailDialog: false,
      newEmail: '',
      addPhoneDialog: false,
      newPhone: '',
            contactDialog: false,
      editingContact: {
        name: '',
        emailAddresses:[],
        phoneNums:[],
        importance:'',
      },
        }
    },
    methods:{
        //send a post request to the server to change to contacts and recieve them
        async fetchContacts(){
            await fetch('http://localhost:3000/contacts')
            .then(response => response.json())
            .then(data => {
                this.contacts = data;
                // this.currentFolder = folderName;
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        },
        async changeContacts(){

let x = {
params:
{
  contact:
  {
    name: this.editingContact.name,
    emailAddresses: this.editingContact.emailAddresses,
    phoneNums: this.editingContact.phoneNums,
    importance: this.editingContact.importance
  }
}

};
console.log(JSON.stringify(x, null, 2));
await fetch('http://localhost:8081/addContact', {
method: 'POST',
headers: {
'Content-Type': 'application/json',
},
body: JSON.stringify(x),
})
.then(response => response.json())
.then(data => {
  // console.log("moura")
this.contacts = data;

console.log(JSON.stringify(this.contacts, null, 2));
// console.log("moura")

// if (this.y.isValid) {
//   // Redirect to the list page after successful signup
//   this.$router.push('/list');
// } else {
//   console.error('Error during signup:', this.y.error);
// }
})




},

    
createContact() {
  this.editingContact = {
    name: '',
    emailAddresses: [],
    phoneNums: [],
    importance:'1',
  };
  this.contactDialog = true;
},


    openContactDialog(contact) {
      this.contactDialog = true;
      this.editingContact = { ...contact };
    },
    saveContact() {
      // Your code to save the edited contact
      this.contactDialog = false;
    },
    deleteContact() {
      // Your code to delete the contact
      this.contactDialog = false;
    },
    addContact() {
      // Your code to add a new contact
      this.contactDialog = false;
    },
    addPhone() {
      this.editingContact.phoneNums.push(this.newPhone);
    },
    addEmail(){
      this.editingContact.emailAddresses.push(this.newEmail);
    },
    deletePhone(index) {
      this.editingContact.phoneNums.splice(index, 1);
    },
    deleteEmail(index) {
      this.editingContact.emailAddresses.splice(index, 1);
    },


    },
    mounted(){
        this.fetchContacts();
        console.log('sssmsmsm');
    }
}
</script>

<style>

  .no-overflow {
  overflow: hidden;
  flex-wrap: wrap;
}
</style>