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
            <v-text-field
            v-model="to"
            label="To"
            :rules="toRules"
            required
            ></v-text-field>
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

            contacts:['djd', 'cnmcn', 'djhjd'],
            prioretyList:['very high', 'high',  'medium', 'low','very low'],
            dialog: false,
            priorety: 'low',
            to:'',
            subject:'',
            body:'',
            attachments:[],
        }
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
        close(){
            this.dialog = false;
            // if (this.$refs.form.validate()) this.clear();

            this.clear();
        },
        // validate the input of the compose form to send the email
        valid(){
            // if (this.$refs.form.validate()) this.send();
            this.send();
        },
        
        send(){this.clear();
        this.dialog = false;},
        //send a post request the data email to the server
        // async send(){},
        async send() {
      try {
        // Simulate sending data to a server
        const response = await fetch('http://localhost:3000', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            to: this.to,
            priority: this.priorety,
            subject: this.subject,
            body: this.body,
            attachments: this.uploadedFiles,
          }),
        });

        if (response.ok) {
          // Email sent successfully, you can handle the response accordingly
          console.log('Email sent successfully!');
        } else {
          // Email sending failed, handle the error
          console.error('Failed to send email:', response.statusText);
        }
      } catch (error) {
        console.error('Error sending email:', error);
      } finally {
        // Clear the form and close the dialog
        this.clear();
        this.dialog = false;
      }
    },   
        
        
        
    },
    updated(){
    }

}
</script>

<style>
.delete-icon {
        cursor: pointer;
        color: red;
        margin-left: 5px;
      }
</style>