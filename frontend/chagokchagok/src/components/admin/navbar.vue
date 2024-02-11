<script setup>
import { RouterLink } from "vue-router";
import { ref, onMounted, watchEffect } from "vue";
import { notificationStore } from "@/stores/alert.js";
import { storeToRefs } from "pinia";
const store = notificationStore();
const { sseStatus } = storeToRefs(store);
const isModalOpen = ref(false);

const openModal = () => {
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

const logout = () => {
  // 로그아웃 로직 추가
  console.log("로그아웃");
  closeModal();
};

const changePassword = () => {
  // 프로필 변경 로직 추가
  console.log("프로필 변경");
  closeModal();
};

const toggleSettings = () => {
  isModalOpen.value = !isModalOpen.value;
};
const closeModalOnOutsideClick = (event) => {
  if (isModalOpen.value) {
    const modalElement = event.target.closest(".modal");
    const isIconClick = event.target.closest(".settings");

    if (!modalElement && !isIconClick) {
      closeModal();
    }
  }
};
</script>
<template>
  <!-- router link 랑 setting icon 링크 설정 해줘야함 -->
  <nav class="navbar-container">
    <div class="links-container">
      <RouterLink to="/admin/dashboard" class="router-link"
        >Dashboard</RouterLink
      >
      <RouterLink to="/admin/section" class="router-link"
        >차량배정현황</RouterLink
      >
      <RouterLink to="/admin/report" class="router-link">신고기록</RouterLink>
    </div>
    <div class="admin-container">
      <div class="admin-name">ADMIN(관리자) 님</div>
      <transition name="modal-fade">
        <Modal v-show="isModalOpen" class="modal">
          <div class="modal-content">
            <div class="modal-inner">
              <div class="inner status" @click="openSettings">
                [ 연결상태&nbsp
                <font-awesome-icon
                  :icon="['fas', 'circle']"
                  :style="{
                    color:
                      sseStatus === 'connected'
                        ? '#27ce59'
                        : sseStatus === 'disconnected'
                        ? '#eb4747'
                        : '',
                  }"
                />
                &nbsp]
              </div>
              <div class="divider"></div>
              <div class="inner hover" @click="changePassword">
                비밀번호 변경
              </div>
              <div class="divider"></div>
              <div class="inner hover" @click="logout">로그아웃</div>
            </div>
          </div>
        </Modal>
      </transition>
      <div class="settings" @click="toggleSettings">
        <font-awesome-icon :icon="['fas', 'gear']" style="color: #222222" />
      </div>
    </div>
  </nav>
</template>

<style scoped>
.settings {
  padding: 0px 5px;
  cursor: pointer; /* 기본 커서 설정 */
}
.settings::before {
  /* background-color: aqua; */
  content: "";
  position: absolute;
  top: -5px; /* 위쪽 여백 조절 */
  bottom: -5px; /* 아래쪽 여백 조절 */
  left: -5px; /* 왼쪽 여백 조절 */
  right: -5px; /* 오른쪽 여백 조절 */
}
.inner {
  padding: 10px 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}
.divider {
  border-bottom: 1px dashed #939393;
  margin: 0px 0; /* 구분선 위아래 여백 조절 */
}

.navbar-container {
  /* height: 80px; */
  width: 100%;
  background: var(--Light-White-T1, #fff);
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.links-container {
  margin-left: 35px;
}
.links-container a.router-link-exact-active {
  color: var(--color-text, black);
  font-weight: bold;
}
.router-link {
  font-size: 1rem;
  color: var(--Grey-50, #d0d1d2);
  padding-right: 40px;
  &:hover {
    background-color: transparent;
  }

  text-decoration: none;
}
.admin-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-right: 30px;
  position: relative;
}

.modal {
  position: absolute;
  top: 35px;
  left: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100; /* 적절한 값으로 조절 */
  width: 150px;
}
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}
.modal-fade-enter, .modal-fade-leave-to /* .modal-fade-leave-active in <2.1.8 */ {
  opacity: 0;
  transform: translateY(-20px);
}
.modal::before {
  content: "";
  position: absolute;
  top: -18px;
  left: 115px;
  margin-left: -10px; /* 꼬리의 폭 조절 */
  border: 10px solid transparent;
  border-bottom-color: rgba(0, 0, 0, 0.8); /* 꼬리의 색상 조절 */
}
.modal-content {
  width: 100%;
  background: rgba(0, 0, 0, 0.8);
  /* padding: 20px; */
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  font-size: 0.9rem;
}

.admin-name {
  font-size: 1rem;
  font-weight: bold;
}

.hover:hover {
  border-radius: 5px;
  background-color: #6c6c6c; /* 호버 상태일 때 배경 색상 변경 */
  cursor: pointer; /* 호버 상태일 때 커서를 pointer로 변경 */
}
</style>
