<template>
  <div class="lock-list-container">
    <h1>Lock Manager</h1>

    <div class="tabs">
      <button
        v-for="tab in tabs"
        :key="tab.value"
        @click="activeTab = tab.value"
        :class="['tab-button', { active: activeTab === tab.value }]"
      >
        {{ tab.label }}
      </button>
    </div>

    <div class="tab-content">
      <MyselfLocks
        v-if="activeTab === 'myself'"
        @view-detail="handleViewDetail"
      />
      <KeyholderLocks
        v-else-if="activeTab === 'keyholder'"
        @view-detail="handleViewDetail"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import MyselfLocks from './MyselfLocks.vue'
import KeyholderLocks from './KeyholderLocks.vue'

const emit = defineEmits(['view-detail'])

const activeTab = ref('myself')

const tabs = [
  { label: 'Myself', value: 'myself' },
  { label: 'Keyholder', value: 'keyholder' }
]

const handleViewDetail = (id, type) => {
  emit('view-detail', id, type)
}
</script>

<style scoped>
.lock-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  margin-bottom: 30px;
}

.tabs {
  display: flex;
  border-bottom: 2px solid #ddd;
  margin-bottom: 20px;
}

.tab-button {
  padding: 12px 24px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: #666;
  position: relative;
  transition: color 0.3s;
}

.tab-button:hover {
  color: #3498db;
}

.tab-button.active {
  color: #3498db;
  font-weight: 600;
}

.tab-button.active::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  right: 0;
  height: 2px;
  background-color: #3498db;
}

.tab-content {
  min-height: 400px;
}
</style>
