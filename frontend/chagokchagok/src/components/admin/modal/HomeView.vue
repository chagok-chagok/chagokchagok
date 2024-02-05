<template>
  <div>
    <div>
      <button @click="isBarModalVisible = true">바 모달 보기</button>
      <BarModal v-if="isBarModalVisible" @close="isBarModalVisible = false" />
    </div>
    <div>
      <button @click="isChangeModalVisible = true">바꾸기 모달 보기</button>
      <ChangeModal v-if="isChangeModalVisible" @close="isChangeModalVisible = false" />
    </div>  
    <div>
      <button @click="isDetailReportModalVisible = true">상세 신고 모달 보기</button>
      <DetailReportModal v-if="isDetailReportModalVisible" @close="isDetailReportModalVisible = false" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import BarModal from '@/components/BarModal.vue';
import ChangeModal from '@/components/ChangeModal.vue';
import DetailReportModal from '@/components/DetailReportModal.vue';

const isBarModalVisible = ref(false);
const isChangeModalVisible = ref(false);
const isDetailReportModalVisible = ref(false);
</script>
