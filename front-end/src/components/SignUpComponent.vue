<template>
  <v-dialog v-model="dialog" fullscreen :scrim="false" transition="dialog-bottom-transition">
      <v-card class="blue-lighten-1 pa-12 rounded">
        <!-- <v-card class="mx-auto px-6 py-8" max-width="344"> -->
        <v-card-title>
          <span class="headline">회원가입</span>
        </v-card-title>
        <v-form v-model="form" @submit.prevent="onSubmit">

        </v-form>
      </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


export default {
  props: {
    value: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      email: "",
      password: "",
      form: false,
      loading: false,
    }
  },
  watch: {
    dialog: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input', value);
      },
    },
  },
  methods: {
    required (v) {
      return !!v || '입력이 비어있습니다.'
    },
    async onSubmit () {
      if (!this.form) return;

      this.loading = true;
      await this.doLogin();
      this.loading = false;
    },
    async doLogin(){
      try{
          const loginData = {email: this.email, password: this.password }
          const response =  await axios.post(`${process.env.VUE_APP_API_BASE_URL}/login`, loginData);
          console.log(response);
          const access_token = response.data.Authorization;
          const refresh_token = response.data.AuthorizationRefresh;
          if (access_token && refresh_token) {
              const access_decoded = jwtDecode(access_token);
              localStorage.setItem("role", access_decoded.role);
              localStorage.setItem("Authorization", access_token);
              localStorage.setItem("AuthorizationRefresh", refresh_token);
              window.location.reload();
          }else{
              console.log("200 OK but not token");
              alert("Login Failed")
          }
      }catch(error){
          const error_message = error.response.data.error_message;
          if(error_message){
              console.log(error_message);
              alert(error_message);
          }else{
              console.log(error);
              alert("Login Failed")
          }
      }
    },
    signInWithGoogle() {
      window.location.href = "http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:8081/oauth2/redirect";
    },
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
@font-face {
  font-family: 'jua';
  src: url(../../public/font/BMJUA_ttf.ttf);
}

  * {
    font-family: 'jua',sans-serif;
  }

  .v-card--shaped {
    border-radius: 24px;
  }

  a {
    color: black;
    text-decoration-line: none;
  }
  a:hover{
    text-decoration-line: underline;
  }

  .social-button:hover {
    box-shadow: 0 8px 10px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
  }

  .text {
    margin-left: 8px;
    display: inline-flex;
    align-items: center;
    justify-content: flex-start;
  }

  #google-connect {
    height: 40px; /* 버튼의 높이를 조절합니다. */
    color: rgb(220, 74, 61); /* 텍스트 색상 */
    text-transform: uppercase;
    width: 100%;
    border-radius: 3px;
    margin: 10px auto;
    display: flex; /* 텍스트를 가운데 정렬하기 위해 Flexbox를 사용합니다. */
    align-items: center; /* 수직 가운데 정렬을 설정합니다. */
  }

  #google-connect {
    background-color: white; /* 기본 배경색 */
    border: 1px solid rgb(220, 74, 61);
  }
  
  #google-connect:hover {
    background-color: rgb(220, 74, 61);
    color: #FFF;
  }
</style>