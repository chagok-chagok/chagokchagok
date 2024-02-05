<template>
  <div class="parking-lot">
    <section
      class="parking-section"
      v-for="section in parkingSections"
      :key="section.name"
    >
      <h2>{{ section.name }}구역</h2>
      <div class="spaces-grid">
        <button
          class="space"
          v-for="space in section.spaces"
          :key="space.id"
          :class="{
            disabled: space.disabled,
            large: space.large,
            selected: selectedSpace === space,
          }"
          @click="selectSpace(space)"
        >
          {{ space.id }}
        </button>
      </div>
    </section>
    <div v-if="selectedSpace" class="space-details">
      <p>차량번호: {{ selectedSpace.vehicleNumber }}</p>
      <p>입차시간: {{ selectedSpace.entryTime }}</p>
      <button @click="releaseBarrier">차단바 해제</button>
      <button @click="exchangeSpace">자리 교환하기</button>
      <button class="close-button" @click="closeDetails">&times;</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const parkingSections = ref([
  {
    name: "A",
    spaces: generateParkingSpaces("A", 14, { large: [3, 4] }), // A03, A04 are larger and disabled
  },
  {
    name: "B",
    spaces: generateParkingSpaces("B", 16), // Regular spaces
  },
  {
    name: "C",
    spaces: generateParkingSpaces("C", 16), // Regular spaces
  },
]);

// This function generates parking spaces with a 'disabled' state for given indices.
function generateParkingSpaces(sectionName, totalSpaces, options = {}) {
  const { large } = options;
  return Array.from({ length: totalSpaces }, (_, i) => {
    const index = i + 1;
    return {
      id: `${sectionName}${String(index).padStart(2, "0")}`,
      disabled: large && large.includes(index),
      large: large && large.includes(index),
    };
  });
}

const selectedSpace = ref(null);

const selectSpace = (space) => {
  selectedSpace.value = space;
  selectedSpace.value.vehicleNumber = "19가1234";
  selectedSpace.value.entryTime = "2024-01-18 15:30:00";
};
const releaseBarrier = () => {
  // Logic to release the parking barrier
  alert("차단바가 해제되었습니다.");
};

const exchangeSpace = () => {
  // Logic to exchange the parking space
  alert("자리 교환이 완료되었습니다.");
};

const closeDetails = () => {
  selectedSpace.value = null;
};
</script>
<style scoped>
.parking-lot {
  display: flex;
  justify-content: space-around;
  gap: 2rem;
}

.parking-section {
  border: 1px solid #000;
  padding: 1rem;
}

.spaces-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* Two columns for parking spaces */
  grid-gap: 0.5rem;
}

.space {
  width: 100px;
  height: 50px;
  border: 1px solid #000;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ccc; /* Default color for unoccupied space */
}

.space.disabled {
  background-color: #add8e6; /* Disabled space color */
}

.space.large {
  grid-column: span 2; /* Large spaces take up two columns */
}

.space.selected {
  background-color: #90ee90; /* Light green color to indicate selection */
}

.space-details {
  position: absolute;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);
  background-color: white;
  padding: 1rem;
  border: 1px solid #000;
  z-index: 10;
}

.close-button {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 1.5rem;
}
</style>
