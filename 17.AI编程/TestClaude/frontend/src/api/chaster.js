import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

export const locksApi = {
  // 获取所有locks
  getLocks() {
    return api.get('/locks')
  },

  // 获取lock详情
  getLockById(id) {
    return api.get(`/locks/${id}`)
  },

  // 更新lock时间
  updateLockTime(id, { days = 0, hours = 0, minutes = 0 }) {
    return api.post(`/locks/${id}/update-time`, {
      days,
      hours,
      minutes
    })
  },

  // 获取keyholder管理的locks
  getKeyholderLocks(params = {}) {
    return api.post('/keyholder/locks/search', {
      criteria: {},
      status: 'unlocked',
      search: '',
      page: 0,
      limit: 15,
      ...params
    })
  }
}
