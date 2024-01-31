<script setup>
import { ref, computed, defineProps, defineEmits, watch } from "vue";

const props = defineProps({
  currentPage: Number,
  totalPages: Number,
  pageSize: Number,
});
const emit = defineEmits(["page-change"]);

const start = ref(0);
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
  if (props.totalPages <= 5) {
    // 전체 페이지가 5개 이하인 경우
    start.value = 1;
    end.value = props.totalPages;
  } else {
    // 전체 페이지가 5개보다 많은 경우
    if (props.currentPage <= 3) {
      // 현재 페이지가 3 이하인 경우
      start.value = 1;
      end.value = 5;
    } else if (props.currentPage >= props.totalPages - 2) {
      // 현재 페이지가 (전체 페이지 - 2) 이상인 경우
      start.value = props.totalPages - 4;
      end.value = props.totalPages;
    } else {
      // 그 외의 경우
      start.value = props.currentPage - 2;
      end.value = props.currentPage + 2;
    }
  }
};

calculatePageNumbers();

const pageNumbers = computed(() => {
  const pages = [];
  const firstPage = 1;
  const lastPage = props.totalPages;

  if (props.currentPage > 3) {
    pages.push(firstPage);

    if (props.currentPage > 4) {
      pages.push("...");
    }
  }

  for (
    let i = Math.max(props.currentPage - 1, firstPage);
    i <= Math.min(props.currentPage + 1, lastPage);
    i++
  ) {
    pages.push(i);
  }

  if (lastPage - props.currentPage >= 3) {
    if (lastPage - props.currentPage > 3) {
      pages.push("...");
    }

    pages.push(lastPage);
  }

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
      <template v-if="pageNumber === '...'">{{ pageNumber }}</template>
      <button
        v-else
        @click="gotoPage(pageNumber)"
        :class="{ active: currentPage === pageNumber }"
      >
        {{ pageNumber }}
      </button>
    </span>

    <span v-if="end.value < totalPages - 2">
      <!-- totalPages - 2보다 작은 경우에만 표시 -->
      <span v-if="end.value < totalPages - 3">...</span>
      <button v-if="end.value < totalPages - 4">{{ totalPages - 1 }}</button>
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

span {
  margin: 0 5px;
}
</style>
