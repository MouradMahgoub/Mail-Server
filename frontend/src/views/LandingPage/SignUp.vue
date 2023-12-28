<template>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="8">
          <v-card>
            <v-card-title class="text-h5">Sign Up</v-card-title>
            <div class="ShowError" v-if="!y.isValid">{{y.message}}</div>
            <v-card-text>
              <v-form @submit.prevent="signUp">
                <v-text-field v-model="username" label="Username" required></v-text-field>
                <v-text-field v-model="email" label="Email" required></v-text-field>
                <v-text-field v-model="password" label="Password" type="password" required></v-text-field>
                    <!-- <router-link to="/list"> -->
                        <v-btn type="submit" color="primary">Sign Up</v-btn>
                    <!-- </router-link> -->
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        username: '',
        email: '',
        password: '',
        y:{isValid:true,message:"hmada"}
      };
    },
    methods: {
      signUp() {
        let x = {
          params:
          {
            userName: this.username,
          emailAddress: this.email,
          password: this.password
        }

        };
         console.log(JSON.stringify(x, null, 2));
        fetch('http://localhost:8085/signUp', {

        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(x),
      })
          .then(res =>res.json())
          .then((data) => {
        this.y = data;

        console.log(JSON.stringify(this.y, null, 2));

        if (this.y.isValid) {
          this.$router.push('/list/inbox');
          localStorage.setItem('user', JSON.stringify(x.params));
        } else {
          console.error('Error during signup:', this.y.error);
        }
      })
      },
    },
  };
  </script>
  
  <style scoped>
    .ShowError{
      color: red;
      margin-top: 1%;
      margin-left: 3%;
      font-weight: bold;
    }
  </style>
