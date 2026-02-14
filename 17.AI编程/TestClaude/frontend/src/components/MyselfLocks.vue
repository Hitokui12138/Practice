<template>
  <div class="myself-locks">
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <table v-else class="locks-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Shared Lock</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="lock in locks" :key="lock._id" @click="viewDetail(lock._id)" class="lock-row">
          <td class="lock-id">{{ lock._id.slice(0, 12) }}...</td>
          <td :class="'status-' + lock.status">{{ lock.status }}</td>
          <td>{{ lock.sharedLock?.name || '-' }}</td>
          <td>{{ formatDate(lock.startDate) }}</td>
          <td>{{ formatDate(lock.endDate) }}</td>
          <td><button class="btn-view">View</button></td>
        </tr>
      </tbody>
    </table>
    <div v-if="locks.length === 0" class="empty">No locks found</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { locksApi } from '../api/chaster'

const emit = defineEmits(['view-detail'])

const locks = ref([])
const loading = ref(true)
const error = ref('')

const fetchLocks = async () => {
  loading.value = true
  error.value = ''
  try {
    const response = await locksApi.getLocks()
    locks.value = response.data
  } catch (err) {
    error.value = err.response?.data?.error || err.message
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  emit('view-detail', id, 'myself')
}

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleString()
}

defineExpose({ fetchLocks })

onMounted(() => {
  fetchLocks()
})
</script>

<style scoped>
.myself-locks {
  width: 100%;
}

.loading {
  padding: 20px;
  text-align: center;
  color: #666;
}

.error {
  padding: 20px;
  color: #e74c3c;
  background-color: #f8d7da;
  border-radius: 4px;
}

.locks-table {
  width: 100%;
  border-collapse: collapse;
}

.locks-table th,
.locks-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.locks-table th {
  background-color: #f5f5f5;
  font-weight: 600;
}

.lock-id {
  font-family: monospace;
}

.status-locked {
  color: #e74c3c;
  font-weight: bold;
}

.status-unlocked {
  color: #27ae60;
  font-weight: bold;
}

.status-frozen {
  color: #3498db;
  font-weight: bold;
}

.lock-row {
  cursor: pointer;
  transition: background-color 0.2s;
}

.lock-row:hover {
  background-color: #f9f9f9;
}

.btn-view {
  padding: 6px 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-view:hover {
  background-color: #2980b9;
}

.empty {
  padding: 40px;
  text-align: center;
  color: #999;
}
</style>
