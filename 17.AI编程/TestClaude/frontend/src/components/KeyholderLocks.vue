<template>
  <div class="keyholder-locks">
    <div class="filter-bar">
      <select v-model="selectedStatus" @change="fetchLocks" class="filter-select">
        <option value="">All Status</option>
        <option value="locked">Locked</option>
        <option value="unlocked">Unlocked</option>
        <option value="frozen">Frozen</option>
      </select>
      <input
        v-model="searchText"
        type="text"
        placeholder="Search..."
        @keyup.enter="fetchLocks"
        class="search-input"
      >
      <button @click="fetchLocks" class="btn-refresh">Refresh</button>
    </div>

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

    <div v-if="response?.total > 0" class="pagination-info">
      Total: {{ response.total }} locks
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { locksApi } from '../api/chaster'

const emit = defineEmits(['view-detail'])

const locks = ref([])
const response = ref(null)
const loading = ref(true)
const error = ref('')
const selectedStatus = ref('')
const searchText = ref('')

const fetchLocks = async () => {
  loading.value = true
  error.value = ''
  try {
    const apiResponse = await locksApi.getKeyholderLocks({
      status: selectedStatus.value,
      search: searchText.value,
      page: 0,
      limit: 15
    })
    response.value = apiResponse.data
    locks.value = apiResponse.data.locks
  } catch (err) {
    error.value = err.response?.data?.error || err.message
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  emit('view-detail', id, 'keyholder')
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
.keyholder-locks {
  width: 100%;
}

.filter-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.filter-select,
.search-input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.filter-select {
  min-width: 120px;
}

.search-input {
  flex: 1;
  min-width: 200px;
}

.btn-refresh {
  padding: 8px 16px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-refresh:hover {
  background-color: #5a6268;
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

.description-cell {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
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

.pagination-info {
  margin-top: 20px;
  color: #666;
  text-align: center;
}
</style>
