<template>
  <div class="lock-detail">
    <button class="btn-back" @click="goBack">Back to List</button>

    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="lock" class="lock-info">
      <h1>Lock Detail</h1>

      <!-- 基础信息区域 -->
      <div class="info-section">
        <h2>Basic Information</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>ID:</label>
            <span class="lock-id">{{ lock._id }}</span>
          </div>
          <div class="info-item">
            <label>Status:</label>
            <span :class="'status-' + lock.status">{{ lock.status }}</span>
          </div>
          <div class="info-item">
            <label>Combination:</label>
            <span class="lock-id">{{ lock.combination || '-' }}</span>
          </div>
          <div class="info-item">
            <label>Total Duration:</label>
            <span>{{ formatDuration(lock.totalDuration) }}</span>
          </div>
          <div class="info-item">
            <label>Display Remaining Time:</label>
            <span>{{ lock.displayRemainingTime ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Limit Lock Time:</label>
            <span>{{ lock.limitLockTime ? 'Yes' : 'No' }}</span>
          </div>
        </div>
      </div>

      <!-- Shared Lock 信息 -->
      <div class="info-section">
        <h2>Shared Lock Information</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>Name:</label>
            <span>{{ lock.sharedLock?.name || '-' }}</span>
          </div>
          <div class="info-item">
            <label>Is Public:</label>
            <span>{{ lock.sharedLock?.isPublic ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Max Locked Users:</label>
            <span>{{ formatMaxUsers(lock.sharedLock?.maxLockedUsers) }}</span>
          </div>
          <div class="info-item">
            <label>Require Contact:</label>
            <span>{{ lock.sharedLock?.requireContact ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Require Password:</label>
            <span>{{ lock.sharedLock?.requirePassword ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Hide Time Logs:</label>
            <span>{{ lock.sharedLock?.hideTimeLogs ? 'Yes' : 'No' }}</span>
          </div>
        </div>
        <div v-if="lock.sharedLock?.description" class="description-box">
          <label>Description:</label>
          <p>{{ lock.sharedLock.description }}</p>
        </div>
      </div>

      <!-- 时间信息区域 -->
      <div class="info-section">
        <h2>Time Information</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>Start Date:</label>
            <span>{{ formatDate(lock.startDate) }}</span>
          </div>
          <div class="info-item">
            <label>End Date:</label>
            <span>{{ formatDate(lock.endDate) }}</span>
          </div>
          <div class="info-item">
            <label>Min Date:</label>
            <span>{{ formatDate(lock.minDate) }}</span>
          </div>
          <div class="info-item">
            <label>Max Date:</label>
            <span>{{ formatDate(lock.maxDate) }}</span>
          </div>
          <div class="info-item">
            <label>Max Limit Date:</label>
            <span>{{ formatDate(lock.maxLimitDate) }}</span>
          </div>
        </div>
      </div>

      <!-- 用户信息区域 -->
      <div class="info-section">
        <h2>User Information</h2>
        <div class="user-info">
          <div class="user-card">
            <h3>Locked User</h3>
            <div class="user-details">
              <img v-if="lock.user?.avatarUrl" :src="lock.user.avatarUrl" class="user-avatar">
              <div class="user-details-text">
                <p><strong>Username:</strong> {{ lock.user?.username || '-' }}</p>
                <p><strong>Online:</strong> <span :class="{ 'online-dot': lock.user?.online }">{{ lock.user?.online ? 'Yes' : 'No' }}</span></p>
                <p><strong>Role:</strong> {{ lock.user?.role || '-' }}</p>
              </div>
            </div>
          </div>
          <div class="user-card">
            <h3>Shared Lock Creator</h3>
            <div class="user-details">
              <img v-if="lock.sharedLock?.user?.avatarUrl" :src="lock.sharedLock.user.avatarUrl" class="user-avatar">
              <div class="user-details-text">
                <p><strong>Username:</strong> {{ lock.sharedLock?.user?.username || '-' }}</p>
                <p><strong>Online:</strong> <span :class="{ 'online-dot': lock.sharedLock?.user?.online }">{{ lock.sharedLock?.user?.online ? 'Yes' : 'No' }}</span></p>
                <p><strong>Is Premium:</strong> {{ lock.sharedLock?.user?.isPremium ? 'Yes' : 'No' }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Shared Lock 详情 -->
      <div class="info-section">
        <h2>Shared Lock Details</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>Duration Mode:</label>
            <span>{{ lock.sharedLock?.durationMode || '-' }}</span>
          </div>
          <div class="info-item">
            <label>Min Duration:</label>
            <span>{{ formatDurationSeconds(lock.sharedLock?.minDuration) }}</span>
          </div>
          <div class="info-item">
            <label>Max Duration:</label>
            <span>{{ formatDurationSeconds(lock.sharedLock?.maxDuration) }}</span>
          </div>
          <div class="info-item">
            <label>Max Limit Duration:</label>
            <span>{{ formatDurationSeconds(lock.sharedLock?.maxLimitDuration) }}</span>
          </div>
          <div class="info-item">
            <label>Created At:</label>
            <span>{{ formatDate(lock.sharedLock?.createdAt) }}</span>
          </div>
          <div class="info-item">
            <label>Updated At:</label>
            <span>{{ formatDate(lock.sharedLock?.updatedAt) }}</span>
          </div>
          <div class="info-item">
            <label>Last Saved At:</label>
            <span>{{ formatDate(lock.sharedLock?.lastSavedAt) }}</span>
          </div>
        </div>
        <div v-if="lock.sharedLock?.tags && lock.sharedLock.tags.length > 0" class="tags-box">
          <label>Tags:</label>
          <div class="tags">
            <span v-for="(tag, index) in lock.sharedLock.tags" :key="index" class="tag">{{ tag }}</span>
          </div>
        </div>
      </div>

      <!-- 状态信息区域 -->
      <div class="info-section">
        <h2>Status Information</h2>
        <div class="info-grid">
          <div class="info-item">
            <label>Unlocked At:</label>
            <span>{{ formatDate(lock.unlockedAt) }}</span>
          </div>
          <div class="info-item">
            <label>Archived At:</label>
            <span>{{ formatDate(lock.archivedAt) }}</span>
          </div>
          <div class="info-item">
            <label>Frozen At:</label>
            <span>{{ formatDate(lock.frozenAt) }}</span>
          </div>
          <div class="info-item">
            <label>Keyholder Archived At:</label>
            <span>{{ formatDate(lock.keyholderArchivedAt) }}</span>
          </div>
          <div class="info-item">
            <label>Allow Session Offer:</label>
            <span>{{ lock.allowSessionOffer ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Is Test Lock:</label>
            <span>{{ lock.isTestLock ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Hide Time Logs:</label>
            <span>{{ lock.hideTimeLogs ? 'Yes' : 'No' }}</span>
          </div>
          <div class="info-item">
            <label>Trusted:</label>
            <span>{{ lock.trusted ? 'Yes' : 'No' }}</span>
          </div>
        </div>
      </div>

      <!-- 更新时间区域 -->
      <div class="update-time-section">
        <h2>Update Time</h2>
        <div class="update-form">
          <div class="form-group">
            <label>Days:</label>
            <input type="number" v-model="updateForm.days" min="-365" max="365">
          </div>
          <div class="form-group">
            <label>Hours:</label>
            <input type="number" v-model="updateForm.hours" min="-24" max="24">
          </div>
          <div class="form-group">
            <label>Minutes:</label>
            <input type="number" v-model="updateForm.minutes" min="-60" max="60">
          </div>
          <button class="btn-update" @click="updateTime" :disabled="updating">
            {{ updating ? 'Updating...' : 'Update Time' }}
          </button>
        </div>
        <p v-if="updateMessage" class="update-message" :class="updateSuccess ? 'success' : 'error'">
          {{ updateMessage }}
        </p>
      </div>

      <!-- 原始数据 -->
      <details class="raw-data">
        <summary>Raw Data</summary>
        <pre class="json-display">{{ JSON.stringify(lock, null, 2) }}</pre>
      </details>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { locksApi } from '../api/chaster'

const props = defineProps({
  lockId: String
})

const emit = defineEmits(['back'])

const lock = ref(null)
const loading = ref(true)
const error = ref('')
const updating = ref(false)
const updateMessage = ref('')
const updateSuccess = ref(false)

const updateForm = ref({
  days: 0,
  hours: 0,
  minutes: 0
})

const fetchLock = async () => {
  loading.value = true
  error.value = ''
  try {
    const response = await locksApi.getLockById(props.lockId)
    lock.value = response.data
  } catch (err) {
    error.value = err.response?.data?.error || err.message
  } finally {
    loading.value = false
  }
}

const updateTime = async () => {
  updating.value = true
  updateMessage.value = ''
  try {
    const response = await locksApi.updateLockTime(props.lockId, updateForm.value)
    lock.value = response.data
    updateMessage.value = 'Time updated successfully!'
    updateSuccess.value = true
    updateForm.value = { days: 0, hours: 0, minutes: 0 }
  } catch (err) {
    updateMessage.value = err.response?.data?.error || err.message
    updateSuccess.value = false
  } finally {
    updating.value = false
  }
}

const goBack = () => {
  emit('back')
}

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleString()
}

const formatDuration = (milliseconds) => {
  if (!milliseconds) return '-'
  // 从毫秒转换为秒
  const seconds = milliseconds / 1000
  const days = Math.floor(seconds / 86400)
  const hours = Math.floor((seconds % 86400) / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  const secs = Math.floor(seconds % 60)
  return `${days}d ${hours}h ${minutes}m ${secs}s`
}

const formatDurationSeconds = (seconds) => {
  if (!seconds) return '-'
  const days = Math.floor(seconds / 86400)
  const hours = Math.floor((seconds % 86400) / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  return `${days}d ${hours}h ${minutes}m`
}

const formatMaxUsers = (value) => {
  if (value === null || value === undefined) return 'Unlimited'
  return value
}

watch(() => props.lockId, fetchLock)

onMounted(() => {
  fetchLock()
})
</script>

<style scoped>
.lock-detail {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.loading {
  padding: 40px;
  text-align: center;
  color: #666;
}

.btn-back {
  padding: 10px 20px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 20px;
}

.btn-back:hover {
  background-color: #5a6268;
}

h1 {
  margin-bottom: 30px;
}

h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
  border-bottom: 2px solid #3498db;
  padding-bottom: 8px;
}

h3 {
  margin: 0 0 10px 0;
  color: #555;
}

.info-section {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 25px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 15px;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-item label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #555;
}

.lock-id {
  font-family: monospace;
  word-break: break-all;
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

.description-box {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #e0e0e0;
}

.description-box label {
  font-weight: bold;
  color: #555;
}

.description-box p {
  margin: 8px 0 0 0;
  line-height: 1.6;
  color: #333;
  white-space: pre-wrap;
}

.photo-box {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #e0e0e0;
}

.photo-box label {
  font-weight: bold;
  color: #555;
}

.lock-photo {
  margin-top: 10px;
  max-width: 100%;
  max-height: 400px;
  border-radius: 8px;
}

.user-info {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.user-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 6px;
  border: 1px solid #e0e0e0;
}

.user-details {
  display: flex;
  gap: 15px;
  align-items: flex-start;
}

.user-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
}

.user-details-text p {
  margin: 5px 0;
  color: #555;
}

.online-dot {
  color: #27ae60;
  font-weight: bold;
}

.tags-box {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #e0e0e0;
}

.tags-box label {
  font-weight: bold;
  color: #555;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 8px;
}

.tag {
  background-color: #3498db;
  color: white;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 14px;
}

.update-time-section {
  background-color: #e8f4f8;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 25px;
}

.update-form {
  display: flex;
  gap: 15px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #555;
}

.form-group input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 100px;
}

.btn-update {
  padding: 10px 20px;
  background-color: #27ae60;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-update:hover:not(:disabled) {
  background-color: #229954;
}

.btn-update:disabled {
  background-color: #95a5a6;
  cursor: not-allowed;
}

.update-message {
  margin-top: 15px;
  padding: 10px;
  border-radius: 4px;
}

.update-message.success {
  background-color: #d4edda;
  color: #155724;
}

.update-message.error {
  background-color: #f8d7da;
  color: #721c24;
}

.raw-data {
  margin-top: 20px;
}

.raw-data summary {
  cursor: pointer;
  color: #3498db;
  font-weight: bold;
}

.raw-data summary:hover {
  color: #2980b9;
}

.json-display {
  margin-top: 10px;
  background-color: #f4f4f4;
  padding: 15px;
  border-radius: 4px;
  overflow-x: auto;
  font-size: 12px;
}

.error {
  color: #e74c3c;
  padding: 20px;
  background-color: #f8d7da;
  border-radius: 4px;
}
</style>
