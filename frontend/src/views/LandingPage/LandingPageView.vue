<template>
    <div>

        <h1 class="greeting">Welcome to Mail Server</h1>
        <div class="ShowError" v-if="!y.isValid">{{ y.message }}</div>
        <v-sheet width="300" class="mx-auto">
      <v-form fast-fail @submit.prevent>
          <v-text-field
          v-model="email"
          label="email"
          :rules="emailRules"
        ></v-text-field>
  
        <v-text-field
          v-model="password"
          label="password"
          :rules="passwordRules"
        ></v-text-field>
        <v-btn @click="signIn" type="submit" block class="mt-2" color="blue">Sign In</v-btn>
        <h3 class="sentence">If you do not have account</h3>
            <v-btn @click="signUp" type="submit" block class="mt-2" color="red">Sign Up</v-btn> 
      </v-form>
    </v-sheet>
    </div>
  </template>
 <script>
 export default {
     data: () => ({
         email: '',
     password: '',
     y: {isValid:true,message:"hamada"},
   }),
   methods: {
     signIn() {
      let x = {
          params:
          {
          emailAddress: this.email,
          password: this.password
        }

        };
        console.log(JSON.stringify(x, null, 2));
        fetch('http://localhost:8081/login',{
          method:'POST',
          headers: {
          'Content-Type': 'application/json',
          },
          body: JSON.stringify(x),
        })
          .then(res =>res.json())
          .then((data) =>{

           this.y=data;
          if(this.y.isValid){
          this.$router.push('/list');
        }
      })
     },
     signUp() {
        this.$router.push('/signup');
     },
   },
 };
 </script>
  <style>
   .greeting{
    color: rgb(137, 184, 228);
    margin-left: 36%;
   }
   .sentence{
    color: gray;
    margin-left: 10%;
    margin-top: 10%;
   }
   .ShowError{
      color: red;
      margin-top: 1%;
      margin-left: 38.5%;
      font-weight: bold;
    }
    </style>
      