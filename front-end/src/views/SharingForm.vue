<template>
  <v-main class="wrap">
    <v-container class="v-container">
      <v-row justify="center">
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title class="text-center">나눔 글쓰기</v-card-title>
            <v-card-text>
              <v-form @submit.prevent="roomCreate">
                <v-text-field
                  v-model="title"
                  label="제목"
                  maxlength="20"
                ></v-text-field>
                <v-textarea
                  v-model="contents"
                  label="내용"
                  maxlength="300"
                ></v-textarea>
                <v-text-field
                  v-model.number="cntPeople"
                  label="인원 수"
                  type="number"
                  max="150"
                ></v-text-field>
                <v-text-field
                  v-model="itemName"
                  label="상품 이름"
                  maxlength="20"
                ></v-text-field>
                <v-file-input
                  v-model="itemImage"
                  label="상품 이미지"
                  @change="fileUpload"
                ></v-file-input>
                <div class="text-right">
                  <v-btn type="submit" color="primary">제출</v-btn>
                </div>
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      title: "",
      contents: "",
      cntPeople: null,
      itemName: "",
      itemImage: null,
    };
  },
  methods: {
    fileUpload(event) {
      this.itemImage = event.target.files[0];
    },
    async roomCreate() {
      try {
        const formData = new FormData();
        formData.append("title", this.title);
        formData.append("contents", this.contents);
        formData.append("cntPeople", this.cntPeople);
        formData.append("itemName", this.itemName);
        formData.append("itemImage", this.itemImage);

        const response = await axios.post(
          `${process.env.VUE_APP_API_BASE_URL}/user/room/create`,
          formData,
          this.$token("members/reissue")
        );
        alert("상품 등록에 성공했습니다.");
        console.log(response.data.result);
      } catch (error) {
        console.error("Error submitting form:", error);
        alert("권한이 없습니다. 로그인 후 이용해주세요.");
        this.$router.push("/sharingHome");
      }
    },
  },
};
</script>