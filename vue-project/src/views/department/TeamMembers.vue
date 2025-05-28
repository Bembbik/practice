<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <!-- Основной контент -->
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок с поиском -->
      <div class="mb-8">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 class="text-3xl font-bold text-slate-900">Команды и участники</h1>
            <p class="mt-2 text-slate-600">Просмотрите состав команд и их участников</p>
          </div>

          <!-- Поиск и кнопка добавления команды в одной строке -->
          <div class="flex items-center space-x-4">
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg
                  class="h-5 w-5 text-slate-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                  />
                </svg>
              </div>
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Поиск команд..."
                class="block w-full pl-10 pr-3 py-2 border border-slate-300 rounded-lg leading-5 bg-white placeholder-slate-500 focus:outline-none focus:placeholder-slate-400 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
              />
            </div>
            <!-- Кнопка добавления команды -->
            <button
              @click="openCreateTeamForm"
              class="flex items-center px-4 py-2 bg-gradient-to-r from-blue-600 to-indigo-600 text-white text-sm font-medium rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all duration-200 shadow-lg hover:shadow-xl transform hover:scale-105"
            >
              <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 4v16m8-8H4"
                />
              </svg>
              Добавить команду
            </button>
          </div>
        </div>
      </div>

      <!-- Статистика -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <!-- Всего команд -->
        <div
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-amber-500 to-orange-500 rounded-xl flex items-center justify-center shadow-lg"
              >
                <svg
                  class="w-6 h-6 text-white"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 515.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-slate-600">Всего команд</p>
              <p class="text-3xl font-bold text-slate-900">{{ teams.length }}</p>
            </div>
          </div>
        </div>

        <!-- Тимлидов -->
        <div
          v-if="selectedTeam"
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-purple-500 to-pink-500 rounded-xl flex items-center justify-center shadow-lg"
              >
                <svg
                  class="w-6 h-6 text-white"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                  />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-slate-600">Тимлид</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ selectedTeam.teamleadName ? 1 : 0 }}
              </p>
            </div>
          </div>
        </div>

        <!-- Участников -->
        <div
          v-if="selectedTeam"
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-indigo-500 to-blue-500 rounded-xl flex items-center justify-center shadow-lg"
              >
                <svg
                  class="w-6 h-6 text-white"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-slate-600">Участников</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ teamMembersCount }}
              </p>
            </div>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-4 gap-6">
        <!-- Команды -->
        <div class="bg-white rounded-xl shadow-lg border border-slate-200">
          <div
            class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-xl"
          >
            <h2 class="text-lg font-semibold text-slate-900 flex items-center">
              <svg
                class="w-5 h-5 mr-2 text-slate-600"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 515.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 919.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                />
              </svg>
              Команды
            </h2>
          </div>
          <div class="p-4">
            <ul class="space-y-2">
              <li
                v-for="team in filteredTeams"
                :key="team.id"
                class="cursor-pointer px-4 py-3 rounded-lg hover:bg-gradient-to-r hover:from-purple-50 hover:to-pink-50 transition-all duration-200 group"
                :class="{
                  'bg-gradient-to-r from-purple-100 to-pink-100 border border-purple-200':
                    team.id === selectedTeam?.id,
                  'hover:shadow-md': team.id !== selectedTeam?.id,
                }"
              >
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3" @click="selectTeam(team)">
                    <span class="font-medium text-slate-900">{{ team.name }}</span>
                  </div>
                  <div class="flex items-center space-x-2">
                    <div class="text-xs bg-slate-100 text-slate-600 px-2 py-1 rounded-full">
                      {{ getTeamMembersCount(team) }} уч.
                    </div>
                    <!-- Кнопка редактирования -->
                    <button
                      @click.stop="editTeam(team)"
                      class="p-1.5 text-slate-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-all duration-200"
                      title="Редактировать команду"
                    >
                      <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                        />
                      </svg>
                    </button>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <!-- Правая часть с информацией о команде -->
        <div class="lg:col-span-3 space-y-6">
          <!-- Информация о выбранной команде -->
          <div v-if="selectedTeam" class="bg-white rounded-xl shadow-lg border border-slate-200">
            <div
              class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-t-xl"
            >
              <div class="flex items-center justify-between">
                <h2 class="text-xl font-bold text-slate-900 flex items-center">
                  <div
                    class="w-8 h-8 bg-gradient-to-br from-blue-500 to-indigo-600 rounded-lg flex items-center justify-center mr-3 shadow-lg"
                  >
                    <svg
                      class="w-4 h-4 text-white"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 515.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 919.288 0M15 7a3 3 0 11-6 0 3 3 0 616 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                      />
                    </svg>
                  </div>
                  {{ selectedTeam.name }}
                </h2>
                <div class="flex items-center space-x-4">
                  <div class="text-sm text-slate-600">
                    Всего участников:
                    <span class="font-semibold">{{ getTeamMembersCount(selectedTeam) }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Тимлид команды -->
          <div v-if="selectedTeam" class="bg-white rounded-xl shadow-lg border border-slate-200">
            <div
              class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-purple-50 to-pink-50 rounded-t-xl"
            >
              <h3 class="text-lg font-semibold text-slate-900 flex items-center">
                <div
                  class="w-6 h-6 bg-gradient-to-br from-purple-500 to-pink-500 rounded-lg flex items-center justify-center mr-3 shadow-md"
                >
                  <svg
                    class="w-3 h-3 text-white"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                    />
                  </svg>
                </div>
                Тимлид
                <span class="ml-2 text-sm text-slate-500"
                  >({{ selectedTeam.teamleadName ? 1 : 0 }})</span
                >
              </h3>
            </div>
            <div class="p-6">
              <div v-if="selectedTeam.teamleadName" class="space-y-4">
                <div
                  class="p-4 rounded-xl border-l-4 border-yellow-400 bg-gradient-to-r from-yellow-50 to-amber-50 hover:shadow-lg transition-all duration-300"
                >
                  <div class="flex items-start justify-between">
                    <div class="flex items-start space-x-4">
                      <div
                        class="w-12 h-12 rounded-full bg-gradient-to-br from-yellow-500 to-amber-600 flex items-center justify-center text-white font-bold shadow-lg"
                      >
                        {{ selectedTeam.teamleadName.charAt(0) }}
                      </div>
                      <div>
                        <p class="text-base font-bold text-slate-900">
                          {{ selectedTeam.teamleadName }}
                        </p>
                        <span
                          class="inline-flex items-center px-3 py-1 mt-2 text-xs font-bold bg-yellow-100 text-yellow-800 rounded-full border border-yellow-200"
                        >
                          <svg
                            class="w-3 h-3 mr-1"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                          >
                            <path
                              stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                            />
                          </svg>
                          Тимлид
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="text-center py-8">
                <svg
                  class="mx-auto h-12 w-12 text-slate-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                  />
                </svg>
                <p class="mt-2 text-sm text-slate-500">Нет назначенного тимлида</p>
              </div>
            </div>
          </div>

          <!-- Участники команды -->
          <div v-if="selectedTeam" class="bg-white rounded-xl shadow-lg border border-slate-200">
            <div
              class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-green-50 to-emerald-50 rounded-t-xl"
            >
              <h3 class="text-lg font-semibold text-slate-900 flex items-center">
                <div
                  class="w-6 h-6 bg-gradient-to-br from-green-500 to-emerald-500 rounded-lg flex items-center justify-center mr-3 shadow-md"
                >
                  <svg
                    class="w-3 h-3 text-white"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                    />
                  </svg>
                </div>
                Участники
                <span class="ml-2 text-sm text-slate-500">({{ teamMembersCount }})</span>
              </h3>
            </div>
            <div class="p-6">
              <div v-if="teamMembers.length" class="space-y-4">
                <div
                  v-for="member in teamMembers"
                  :key="member.id"
                  class="p-4 rounded-xl border-l-4 border-green-400 bg-gradient-to-r from-green-50 to-emerald-50 hover:shadow-lg transition-all duration-300"
                >
                  <div class="flex items-start justify-between">
                    <div class="flex items-start space-x-4">
                      <div
                        class="w-10 h-10 rounded-full bg-gradient-to-br from-green-500 to-emerald-600 flex items-center justify-center text-white font-bold shadow-md"
                      >
                        {{ member.name.charAt(0) }}
                      </div>
                      <div>
                        <p class="text-sm font-semibold text-slate-900">{{ member.name }}</p>
                        <span
                          class="inline-flex items-center px-2 py-1 mt-1 text-xs font-medium bg-green-100 text-green-800 rounded-full border border-green-200"
                        >
                          Участник
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="text-center py-8">
                <svg
                  class="mx-auto h-12 w-12 text-slate-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
                <p class="mt-2 text-sm text-slate-500">Нет участников в команде</p>
              </div>
            </div>
          </div>

          <!-- Заглушка когда команда не выбрана -->
          <div v-if="!selectedTeam" class="bg-white rounded-xl shadow-lg border border-slate-200">
            <div class="p-12 text-center">
              <svg
                class="mx-auto h-16 w-16 text-slate-400 mb-4"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 515.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 919.288 0M15 7a3 3 0 11-6 0 3 3 0 616 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                />
              </svg>
              <h3 class="text-lg font-medium text-slate-900 mb-2">Выберите команду</h3>
              <p class="text-slate-500">
                Выберите команду из списка слева, чтобы просмотреть информацию о ее участниках
              </p>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Форма создания/редактирования команды -->
    <ComandsForm
      v-if="showCommandsForm"
      :team="currentTeam"
      :all-users="users"
      @save="handleSaveTeam"
      @close="closeCommandsForm"
    />
  </div>
</template>

<script>
import axios from 'axios'
import ComandsForm from '@/components/department/ComandsForm.vue'

export default {
  name: 'TeamMembers',
  components: { ComandsForm },
  data() {
    return {
      searchQuery: '',
      teams: [],
      allTeams: [],
      currentTeam: null,
      selectedTeam: null,
      showCommandsForm: false,
      users: [],
      teamMembers: [], // Участники выбранной команды
    }
  },
  computed: {
    filteredTeams() {
      if (!this.searchQuery) return this.teams
      return this.teams.filter((team) =>
        team.name.toLowerCase().includes(this.searchQuery.toLowerCase()),
      )
    },
    teamMembersCount() {
      return this.teamMembers.length
    },
  },
  async mounted() {
    const user = JSON.parse(localStorage.getItem('user'))
    const departmentId = user?.departmentId

    if (departmentId) {
      await this.fetchTeamsByDepartment(departmentId)
    }

    await this.loadUsers()
  },
  methods: {
    async loadUsers() {
      try {
        const res = await axios.get(`${import.meta.env.VITE_API_URL}/admin/users`)
        this.users = res.data
      } catch (error) {
        console.error('Ошибка при загрузке пользователей:', error)
        this.users = []
      }
    },
    async fetchTeamsByDepartment(departmentId) {
      if (!departmentId) {
        console.error('departmentId не указан')
        return
      }

      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams`,
        )

        this.allTeams = response.data
        this.teams = [...response.data]

        console.log('Команды загружены:', response.data)
      } catch (error) {
        console.error('Ошибка при загрузке команд:', error)
        alert('Не удалось загрузить команды')
      }
    },
    async fetchTeamMembers(teamId) {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/department/teams/${teamId}/members`,
        )
        this.teamMembers = response.data
      } catch (error) {
        console.error('Ошибка при загрузке участников команды:', error)
        this.teamMembers = []
      }
    },
    async selectTeam(team) {
      this.selectedTeam = team
      await this.fetchTeamMembers(team.id)
    },
    openCreateTeamForm() {
      this.currentTeam = null
      this.showCommandsForm = true
    },
    editTeam(team) {
      this.currentTeam = { ...team }
      this.showCommandsForm = true
    },
    closeCommandsForm() {
      this.showCommandsForm = false
      this.currentTeam = null
    },
    async handleSaveTeam(savedTeam) {
      try {
        // Обновляем список команд
        const user = JSON.parse(localStorage.getItem('user'))
        const departmentId = user?.departmentId

        if (departmentId) {
          await this.fetchTeamsByDepartment(departmentId)
        }

        // Если была выбрана команда для редактирования, обновляем ее данные
        if (this.selectedTeam && this.selectedTeam.id === savedTeam.id) {
          this.selectedTeam = savedTeam
          await this.fetchTeamMembers(savedTeam.id)
        }

        this.closeCommandsForm()

        // Показываем уведомление об успехе
        alert(this.currentTeam ? 'Команда успешно обновлена!' : 'Команда успешно создана!')
      } catch (error) {
        console.error('Ошибка при обработке сохранения команды:', error)
        alert('Произошла ошибка при сохранении команды')
      }
    },
    getTeamMembersCount(team) {
      // Считаем тимлида (если есть) + участников команды
      let count = 0
      if (team.teamleadName) {
        count += 1
      }
      // Здесь можно добавить подсчет участников, если они будут доступны в API
      return count
    },
  },
}
</script>
