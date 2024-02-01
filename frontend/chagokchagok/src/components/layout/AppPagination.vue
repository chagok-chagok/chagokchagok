<script setup>
import { ref, computed, defineProps, defineEmits, watch } from "vue";

const props = defineProps({
  currentPage: Number,
  totalPages: Number,
  pageSize: Number,
});
const emit = defineEmits(["page-change"]);

const start = ref(1);
const end = ref(0);

// totalPages가 변경될 때마다 페이지 번호 다시 계산
watch(
  () => props.totalPages,
  () => {
    calculatePageNumbers();
  }
);

// currentPage가 변경될 때마다 start와 end 재계산
watch(
  () => props.currentPage,
  () => {
    calculatePageNumbers();
  }
);

const calculatePageNumbers = () => {
  end.value = props.totalPages;
};

calculatePageNumbers();

const pageNumbers = computed(() => {
  const pages = [];
  const pageLength = Math.min(5, props.totalPages);
  if (pageLength == 0) return pages;
  // 전체 페이지 수가 5 이하면 pagination은 전체 리스트가 됨
  // pages.push(1);
  // 현재 자신 -1부터 현재 자신 + 1까지 추가하는과정
  const prevNumber = props.currentPage - 1;
  const nextNumber = props.currentPage + 1;
  for (
    let i = Math.max(start.value + 1, props.currentPage - 1);
    i <= Math.min(end.value - 1, props.currentPage + 1);
    i++
  ) {
    pages.push(i);
  }

  // 만약 pages의 길이가 전체 리스트 길이 -2보다 작다?? => 끝으로 치우쳐져 있다
  // 어디로 치우쳐진지 검사해
  if (pages.length < pageLength - 2) {
    // prevNumber가 2보다 작으면 1쪽으로 치우쳐짐
    if (prevNumber < 2) {
      while (pages.length < pageLength - 2) {
        pages.push(pages[pages.length - 1] + 1);
      }
    }
    // nextNumber가 totalPages - 1보다 크면 totalPages쪽으로 치우쳐짐
    else if (nextNumber > props.totalPages - 1) {
      while (pages.length < pageLength - 2) {
        pages.unshift(pages[0] - 1);
      }
    }
  }

  pages.unshift(1);
  // start와 end가 같지 않으면 제일 끝에 end 추가
  if (start.value !== end.value) pages.push(end.value);
  return pages;
});

const gotoPage = (page) => {
  emit("page-change", page);
};

const prevPage = () => {
  if (props.currentPage > 1) {
    emit("page-change", props.currentPage - 1);
  }
};

const nextPage = () => {
  if (props.currentPage < props.totalPages) {
    emit("page-change", props.currentPage + 1);
  }
};
</script>

<template>
  <div class="pagination">
    <button @click="gotoPage(1)" :disabled="currentPage === 1">First</button>
    <button @click="prevPage" :disabled="currentPage === 1">Previous</button>

    <span v-if="start.value > 1">
      <!-- 1보다 큰 경우에만 표시 -->
      <button v-if="start.value > 3" @click="gotoPage(1)">1</button>
      <button v-if="start.value > 4">2</button>
      <span v-if="start.value > 5">...</span>
    </span>

    <span v-for="pageNumber in pageNumbers" :key="pageNumber">
      <button
        @click="gotoPage(pageNumber)"
        :class="{ active: currentPage === pageNumber }"
      >
        {{ pageNumber }}
      </button>
    </span>

    <span v-if="end.value < totalPages">
      <!-- totalPages보다 작은 경우에만 표시 -->
      <span v-if="end.value < totalPages - 2">...</span>
      <button v-if="end.value < totalPages - 1">{{ totalPages - 1 }}</button>
      <button @click="gotoPage(totalPages)">{{ totalPages }}</button>
    </span>

    <button @click="nextPage" :disabled="currentPage === totalPages">
      Next
    </button>
    <button
      @click="gotoPage(totalPages)"
      :disabled="currentPage === totalPages"
    >
      Last
    </button>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

button {
  margin: 0 5px;
  padding: 5px 10px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

button.active {
  background-color: #007bff;
  color: #fff;
}
</style>
