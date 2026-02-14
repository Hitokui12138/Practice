<template>
  <div id="app">
    <header>
      <h1>Chaster Lock Manager</h1>
    </header>
    <main>
      <LockListContainer v-if="currentView === 'list'" @view-detail="viewLock" />
      <LockDetail v-if="currentView === 'detail'" :lock-id="selectedLockId" @back="goToList" />
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import LockListContainer from './components/LockListContainer.vue'
import LockDetail from './components/LockDetail.vue'

const currentView = ref('list')
const selectedLockId = ref('')

const viewLock = (id, type) => {
  selectedLockId.value = id
  currentView.value = 'detail'
}

const goToList = () => {
  currentView.value = 'list'
  selectedLockId.value = ''
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
  background-color: #f5f5f5;
}

#app {
  min-height: 100vh;
}

header {
  background-color: #2c3e50;
  color: white;
  padding: 20px;
  text-align: center;
}

header h1 {
  margin: 0;
  font-size: 24px;
}

main {
  padding: 20px;
}
</style>
