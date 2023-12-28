<template>

        <v-dialog v-model="dialog" persistent width="500">
    <template v-slot:activator="{ props }">
        <v-btn
        color="primary"
        v-bind="props"
        prepend-icon="mdi-pen"
        >
        Compose
    </v-btn>
</template>

<v-card class="mx-auto" width="600">
    <!-- make a compose card that take to, subject, body, and a proiorety as input .. put in the top icon to cancel, and in the bottom two options (send, drat)-->
    <v-card-title>
        <v-row>
            <v-spacer></v-spacer>
            <v-icon right @click="close">mdi-close</v-icon>
        </v-row>
        <v-col>
            <v-combobox
            v-model="to"
            label="To"
            :rules="toRules"
            :items="contacts"
            required
            multiple
            ></v-combobox>
            <v-select
            v-model="priorety"
            :items="prioretyList"
            label="Priorety"
            ></v-select>
        </v-col>
        </v-card-title>
        <v-card-text>
            <v-text-field
            v-model="subject"
            label="Subject"
            ></v-text-field>
            <div>
         
         <v-btn icon @click="$refs.fileInput.click()">
           <v-icon>mdi-paperclip</v-icon> 
         </v-btn>
     
         
         <input ref="fileInput" type="file" style="display: none" @change="handleFileChange" multiple>
     
         <div v-if="uploadedFiles.length > 0">
           <h3>Uploaded Files:</h3>
           <ul>
             <li v-for="(file, index) in uploadedFiles" :key="index">
               <span>
                 <a href="#" @click.prevent="openAttachment(file)">
                   {{ file.name }}
                 </a>
                 <span class="delete-icon" @click="deleteFile(index)">X</span>
               </span>
             </li>
           </ul>
         </div>
       </div>
            <v-textarea
            v-model="body"
            label="Body"
            ></v-textarea>
        </v-card-text>
        <v-card-actions>
            <v-btn
            color="primary"
            text
            @click="close"
            >
            Save as Draft
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
        color="primary"
        outlined
        @click="valid"
        >
        Send
    </v-btn>
</v-card-actions>

</v-card>

</v-dialog>

</template>

<script>
export default {
    
    data() {
        return {
            uploadedFiles: [],
            toRules: [
                value => {
                if (value?.length >= 3) return true
                return 'Please specify at least one recipient'
                }
            ],
            from: '',
            contacts:[],
            prioretyList:['very high', 'high',  'medium', 'low','very low'],
            dialog: false,
            priorety: 'low',
            to:[],
            subject:'',
            preview:'',
            body:'',
            // attachments:[],
        }
    },


    mounted() {
      this.fetchContacts();
    },

    methods:{
        handleFileChange(event) {
            const files = event.target.files;
      
            for (const file of files) {
              const reader = new FileReader();
              reader.onload = () => {
                this.uploadedFiles.push({
                  name: file.name,
                  type: file.type,
                  url: reader.result,
                });
              };
      
              reader.readAsDataURL(file);
            }
          },
          openAttachment(file) {
            const pdfUrl = file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
              ? `https://docs.google.com/gview?url=${encodeURIComponent(file.url)}&embedded=true`
              : file.type === 'application/pdf'
                ? file.url
                : '';
      
            const newTabContent = `
              <html>
                <head>
                  <title>${file.name}</title>
                </head>
                <body style="margin: 0; display: flex; justify-content: center; align-items: center; height: 100vh;">
                  ${
                    file.type.startsWith('image')
                      ? `<img src="${file.url}" alt="${file.name}" style="max-width: 100%; max-height: 100%;">`
                      : file.type === 'application/pdf' || file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                      ? `<iframe src="${pdfUrl}" style="width: 100%; height: 100%;" frameborder="0"></iframe>`
                      : file.type.startsWith('video')
                      ? `<video controls style="max-width: 100%; max-height: 100%;">
                          <source src="${file.url}" type="${file.type}">
                        </video>`
                      : ''
                  }
                </body>
              </html>
            `;
      
            const newTab = window.open('', '_blank');
            newTab.document.write(newTabContent);
            newTab.document.close();
          },
          deleteFile(index) {
            this.uploadedFiles.splice(index, 1);
          },
          clear(){
            this.dialog = false;
            this.priorety= 'low';
            this.to='';
            this.subject='';
            this.body='';
            this.attachments=[];
            
        },
        async close(){
          try {

let x = {
  params:{
    email:{
      from: this.from,
      to: this.to,
      date: new Date().toLocaleString(),
      importance: this.priorety,
      subject: this.subject,
      body: this.body,
      attachments: this.uploadedFiles,
    }
}
}

//print x in json fromat in the console
console.log(JSON.stringify(x, null, 2));

// Simulate sending data to a server
await fetch('http://localhost:8085/toDraft', {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(x),
});
console.log(JSON.stringify(this.uploadedFiles, null, 2));
// console.log(JSON.stringify(this., null, 2));
} catch (error) {
console.error('Error sending email:', error);
} 
            this.dialog = false;
            this.clear();
        },
        // validate the input of the compose form to send the email
        valid(){
            // if (this.$refs.form.validate()) this.send();
            this.send();
        },

        async fetchContacts(){
          await fetch('http://localhost:8085/getContacts')
          .then(response => response.json())
          .then(data => {
            for(let s of data){
              for(let e of s.emailAddresses){
                this.contacts.push(e);
              }
            }
          })
        },
      
        async send() {
            try {
              let x = {
                params:{
                  email:{
                    from:  JSON.parse(localStorage.getItem('user')).emailAddress,
                    to: this.to,
                    date: new Date().toLocaleString(),
                    importance: this.priorety,
                    subject: this.subject,
                    body: this.body,
                    attachments: this.uploadedFiles,
                  }
              }
            }
            this.fetchContacts();
            //print x in json fromat in the console
            console.log(JSON.stringify(x, null, 2));

              // Simulate sending data to a server
              await fetch('http://localhost:8085/sendEmail', {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify(x),
              });
              console.log(JSON.stringify(this.uploadedFiles, null, 2));
              // console.log(JSON.stringify(this., null, 2));

      } catch (error) {
        console.error('Error sending email:', error);
      } finally {
        // Clear the form and close the dialog
        // this.clear();
        this.dialog = false;
        // this.$router.push('/list/send');
      }
    },   
        
    },
        
        
    

}
</script>

<style>
.delete-icon {
        cursor: pointer;
        color: red;
        margin-left: 5px;
      }
</style>