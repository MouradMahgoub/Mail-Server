<template>
  <!-- show the contacts as a list with a pgination and and make it available to show the contacts details and add a new contacts. besides, edit the contact[rename it, add or delete contact, add or delete emailAddresses and add or delete phoneNums of the contact ]  -->
    <div>

      
      
      <v-toolbar>
        <v-row>
                <v-col cols="2">
                  <v-text-field 
                    v-model="searchKey"
                    label="Search" 
                    class="mx-2"
                    ></v-text-field>
                </v-col>
                
                <v-col cols="3">
                    <v-select
                    v-model="sortKey"
                    :items="['alphabetical']"
                    label="Sort by"
                    class="mx-2"
                    clearable
                    ></v-select>
                </v-col>                
            </v-row>
          </v-toolbar>
            
        <v-btn
        v-if="searchKey || sortKey || filterFrom || filterSubject"
        @click="clear()"
        color="secondary"
        style="width:20vh;  margin: 1vh 1vh 1vh 60vh;"
        >Cancel</v-btn>
        <v-btn 
          v-if="searchKey || sortKey"
          @click="applyFiltersContacts"
          color="primary"
          style="width:20vh; margin: 1vh 60vh 1vh 1vh; float: right;"
          >Apply</v-btn>
          
        <v-divider></v-divider>



      <v-btn color="orange"  @click="createContact">
        Add contacts
       </v-btn>
       <v-dialog  v-model="contactDialog" max-width="700px">
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
          <v-btn color="blue darken-1" text @click="closedialog">close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
          <v-btn color="blue darken-1" text @click="closedialog">close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <v-dialog v-model="editcontactDialog" max-width="700px">
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
          <v-btn color="blue darken-1" text @click="editContacts">Save</v-btn>
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
      searchKey: null,
      sortKey: null,
      
      addEmailDialog: false,
      newEmail: '',
      addPhoneDialog: false,
      newPhone: '',
      oldName:'',
            contactDialog: false,
            editcontactDialog:false,
      editingContact: {
        name: '',
        emailAddresses:[],
        phoneNums:[],
        importance:'',
      },
        }
    },

    methods:{

      clear(){
        this.searchKey= null;
        this.sortKey= null;
        this.fetchContacts();
      },

      async applyFiltersContacts(){
            if(this.searchKey != null && this.searchKey != ''){
                await fetch('http://localhost:8085/searchContact', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        params:{
                            contactName: this.searchKey,
                        }
                    })
                })
                .then(response => response.json())
                .then(data => {
                    this.contacts = data;
                })
                .catch(error => console.error('Error applying filters:', error));
            }
            if(this.sortKey != null && this.sortKey != ''){
                await fetch('http://localhost:8085/sortContacts')
                .then(response => response.json())
                .then(data => {
                    this.contacts = data;
                })
                .catch(error => console.error('Error applying filters:', error));
            }
        },


        //send a post request to the server to change to contacts and recieve them
        async fetchContacts(){
            await fetch('http://localhost:8085/getContacts')
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
await fetch('http://localhost:8085/addContact', {
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
})

this.contactDialog=false;


},

async editContacts(){
  let x = {
params:
{
  contact:
  {
    name: this.editingContact.name,
    emailAddresses: this.editingContact.emailAddresses,
    phoneNums: this.editingContact.phoneNums,
    importance: this.editingContact.importance
  },
  oldName: this.oldName
}

};
console.log(JSON.stringify(x, null, 2));
await fetch('http://localhost:8085/editContact', {
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
})
this.editcontactDialog=false;
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
      this.editcontactDialog = true;
      this.editingContact = { ...contact };
      this.oldName=this.editingContact.name;
    },
    saveContact() {
      // Your code to save the edited contact
      this.editcontactDialog = false;
    },
   async deleteContact() {
      // Your code to delete the contact
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
await fetch('http://localhost:8085/deleteContact', {
method: 'DELETE',
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
})


      this.editcontactDialog = false;
    },
    addContact() {
      // Your code to add a new contact
      this.contactDialog = false;
    },
    addPhone() {
      this.editingContact.phoneNums.push(this.newPhone);
      this.addPhoneDialog = false;
    },
    addEmail(){
      this.editingContact.emailAddresses.push(this.newEmail);
      this.addEmailDialog = false;
    },
    deletePhone(index) {
      this.editingContact.phoneNums.splice(index, 1);
    },
    deleteEmail(index) {
      this.editingContact.emailAddresses.splice(index, 1);
    },
    closedialog(){
      this.contactDialog=false;
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