<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Индикатор загрузки -->
      <div v-if="loading" class="flex items-center justify-center min-h-96">
        <div class="text-center">
          <div
            class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"
          ></div>
          <p class="text-slate-600">Загрузка данных отдела...</p>
        </div>
      </div>

      <!-- Сообщение об ошибке -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-lg p-6 mb-8">
        <div class="flex items-center">
          <svg
            class="w-6 h-6 text-red-600 mr-3"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
            />
          </svg>
          <div>
            <h3 class="text-red-800 font-semibold">Ошибка загрузки данных</h3>
            <p class="text-red-700 text-sm mt-1">{{ error }}</p>
          </div>
        </div>
        <button
          @click="loadDashboardData"
          class="mt-4 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors duration-200"
        >
          Попробовать снова
        </button>
      </div>

      <!-- Основной контент -->
      <div v-else>
        <!-- Заголовок панели -->
        <div class="mb-8">
          <h2 class="text-3xl font-bold text-slate-900">{{ departmentName || 'Панель отдела' }}</h2>
          <p class="mt-2 text-slate-600">
            Панель управления отделом. Отслеживайте работу команд и тикетов.
          </p>
        </div>

        <!-- Статистика -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
          >
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <div
                  class="w-12 h-12 bg-gradient-to-br from-blue-500 to-cyan-600 rounded-xl flex items-center justify-center shadow-lg"
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
                      d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                    />
                  </svg>
                </div>
              </div>
              <div class="ml-4">
                <p class="text-sm font-medium text-slate-600">Команд в отделе</p>
                <p class="text-3xl font-bold text-slate-900">{{ teamCount }}</p>
              </div>
            </div>
          </div>

          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
          >
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <div
                  class="w-12 h-12 bg-gradient-to-br from-emerald-500 to-green-500 rounded-xl flex items-center justify-center shadow-lg"
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
                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                </div>
              </div>
              <div class="ml-4">
                <p class="text-sm font-medium text-slate-600">Завершено тикетов</p>
                <p class="text-3xl font-bold text-slate-900">{{ completedTickets }}</p>
              </div>
            </div>
          </div>

          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
          >
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <div
                  class="w-12 h-12 bg-gradient-to-br from-red-500 to-pink-500 rounded-xl flex items-center justify-center shadow-lg"
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
                      d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
                    />
                  </svg>
                </div>
              </div>
              <div class="ml-4">
                <p class="text-sm font-medium text-slate-600">Срочные тикеты</p>
                <p class="text-3xl font-bold text-slate-900">{{ highPriorityTickets }}</p>
              </div>
            </div>
          </div>

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
                      d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                    />
                  </svg>
                </div>
              </div>
              <div class="ml-4">
                <p class="text-sm font-medium text-slate-600">Всего тикетов</p>
                <p class="text-3xl font-bold text-slate-900">{{ totalTickets }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Основной контент -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <!-- Последние тикеты отдела -->
          <div class="lg:col-span-2 bg-white rounded-xl shadow-lg border border-slate-200">
            <div
              class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-xl"
            >
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-slate-900 flex items-center">
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
                      d="M19 11H5m14-4H3m16 8H1m18 4H7"
                    />
                  </svg>
                  Последние тикеты отдела
                </h3>
              </div>
            </div>
            <div class="p-6">
              <!-- Индикатор загрузки тикетов -->
              <div v-if="ticketsLoading" class="flex items-center justify-center py-8">
                <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
                <span class="ml-3 text-slate-600">Загрузка тикетов...</span>
              </div>

              <!-- Список тикетов -->
              <div v-else-if="recentTickets.length > 0" class="space-y-4">
                <div
                  v-for="ticket in recentTickets"
                  :key="ticket.id"
                  class="flex items-center justify-between p-4 rounded-xl border transition-all duration-200 hover:shadow-md"
                  :class="getTicketCardClass(ticket.priority)"
                >
                  <div class="flex items-center space-x-4">
                    <div
                      class="w-3 h-3 rounded-full"
                      :class="getTicketDotClass(ticket.priority)"
                    ></div>
                    <div>
                      <p class="text-sm font-semibold text-slate-900">{{ ticket.title }}</p>
                      <p class="text-xs text-slate-600 flex items-center mt-1">
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
                            d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                          />
                        </svg>
                        {{ ticket.team || 'Не назначена' }} • {{ ticket.author || 'Неизвестно' }} •
                        {{ ticket.createdAt || 'Недавно' }}
                      </p>
                    </div>
                  </div>
                  <div class="flex items-center space-x-2">
                    <span
                      class="px-3 py-1 text-xs font-semibold rounded-full"
                      :class="getPriorityClass(ticket.priority)"
                    >
                      {{ getPriorityLabel(ticket.priority) }}
                    </span>
                  </div>
                </div>
              </div>

              <!-- Пустое состояние -->
              <div v-else class="text-center py-8">
                <svg
                  class="w-12 h-12 text-slate-400 mx-auto mb-4"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                  />
                </svg>
                <p class="text-slate-600">Нет тикетов для отображения</p>
              </div>

              <div class="mt-6">
                <button
                  @click="navigateToTickets"
                  class="w-full text-center text-sm text-blue-600 hover:text-blue-800 font-semibold py-3 px-4 rounded-lg hover:bg-blue-50 transition-all duration-200"
                >
                  Посмотреть все тикеты отдела →
                </button>
              </div>
            </div>
          </div>

          <!-- Команды и быстрые действия -->
          <div class="space-y-6">
            <!-- Команды отдела -->
            <div class="bg-white rounded-xl shadow-lg border border-slate-200">
              <div
                class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-xl"
              >
                <h3 class="text-lg font-semibold text-slate-900 flex items-center">
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
                      d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                    />
                  </svg>
                  Команды отдела
                </h3>
              </div>
              <div class="p-4">
                <!-- Индикатор загрузки команд -->
                <div v-if="teamsLoading" class="flex items-center justify-center py-4">
                  <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
                  <span class="ml-2 text-sm text-slate-600">Загрузка команд...</span>
                </div>

                <!-- Список команд -->
                <div v-else-if="teams.length > 0" class="space-y-3">
                  <div
                    v-for="team in teams"
                    :key="team.id"
                    class="flex items-center justify-between p-3 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-lg border border-blue-100 hover:shadow-md transition-all duration-200"
                  >
                    <div class="flex items-center space-x-3">
                      <div
                        class="w-8 h-8 bg-gradient-to-br from-blue-500 to-indigo-500 rounded-lg flex items-center justify-center"
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
                            d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                          />
                        </svg>
                      </div>
                      <div>
                        <p class="text-sm font-semibold text-slate-900">{{ team.name }}</p>
                        <p class="text-xs text-slate-600">{{ team.members || 0 }} участников</p>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Пустое состояние для команд -->
                <div v-else class="text-center py-4">
                  <p class="text-sm text-slate-600">Команды не найдены</p>
                </div>
              </div>
            </div>

            <!-- Быстрые действия -->
            <div class="bg-white rounded-xl shadow-lg border border-slate-200">
              <div
                class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-xl"
              >
                <h3 class="text-lg font-semibold text-slate-900 flex items-center">
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
                      d="M13 10V3L4 14h7v7l9-11h-7z"
                    />
                  </svg>
                  Быстрые действия
                </h3>
              </div>
              <div class="p-4">
                <div class="grid grid-cols-1 gap-3">
                  <button
                    @click="navigateToCreateTicket"
                    class="flex items-center p-3 border-2 border-dashed border-slate-300 rounded-xl hover:border-blue-500 hover:bg-gradient-to-r hover:from-blue-50 hover:to-indigo-50 transition-all duration-300 group"
                  >
                    <div
                      class="w-8 h-8 bg-gradient-to-br from-blue-500 to-indigo-500 rounded-lg flex items-center justify-center mr-3 group-hover:scale-110 transition-transform duration-200"
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
                          d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                        />
                      </svg>
                    </div>
                    <div class="text-left">
                      <span class="text-sm font-semibold text-slate-900">Новый тикет</span>
                      <p class="text-xs text-slate-600">Создать обращение</p>
                    </div>
                  </button>

                  <button
                    @click="navigateToTeamManagement"
                    class="flex items-center p-3 border-2 border-dashed border-slate-300 rounded-xl hover:border-purple-500 hover:bg-gradient-to-r hover:from-purple-50 hover:to-pink-50 transition-all duration-300 group"
                  >
                    <div
                      class="w-8 h-8 bg-gradient-to-br from-purple-500 to-pink-500 rounded-lg flex items-center justify-center mr-3 group-hover:scale-110 transition-transform duration-200"
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
                          d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                        />
                      </svg>
                    </div>
                    <div class="text-left">
                      <span class="text-sm font-semibold text-slate-900">Управление командами</span>
                      <p class="text-xs text-slate-600">Настройки команд</p>
                    </div>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'DepartmentDashboard',
  data() {
    return {
      departmentName: '',
      teamCount: 0,
      completedTickets: 0,
      highPriorityTickets: 0,
      totalTickets: 0,
      teams: [],
      recentTickets: [],
      loading: true,
      ticketsLoading: false,
      teamsLoading: false,
      error: null,
    }
  },
  computed: {
    departmentId() {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      return user?.departmentId || null
    },
    apiUrl() {
      return import.meta.env.VITE_API_URL || 'http://localhost:3000/api'
    },
  },
  async created() {
    await this.loadDashboardData()
  },
  methods: {
    async loadDashboardData() {
      if (!this.departmentId) {
        this.error = 'ID отдела не найден. Пожалуйста, войдите в систему заново.'
        this.loading = false
        return
      }

      this.loading = true
      this.error = null

      try {
        // Загружаем все данные параллельно
        await Promise.all([this.loadStatistics(), this.loadTeams(), this.loadRecentTickets()])
      } catch (err) {
        console.error('Ошибка при загрузке данных панели:', err)
        this.error = this.getErrorMessage(err)
      } finally {
        this.loading = false
      }
    },

    async loadStatistics() {
      try {
        const response = await axios.get(`${this.apiUrl}/department/${this.departmentId}/stats`, {
          timeout: 10000,
          headers: {
            'Content-Type': 'application/json',
          },
        })

        if (response.data) {
          const {
            departmentName = 'Неизвестный отдел',
            teamCount = 0,
            completedTickets = 0,
            highPriorityTickets = 0,
            totalTickets = 0,
          } = response.data

          this.departmentName = departmentName
          this.teamCount = teamCount
          this.completedTickets = completedTickets
          this.highPriorityTickets = highPriorityTickets
          this.totalTickets = totalTickets
        }
      } catch (err) {
        console.error('Ошибка загрузки статистики:', err)
        throw new Error('Не удалось загрузить статистику отдела')
      }
    },

    async loadTeams() {
      this.teamsLoading = true
      try {
        const response = await axios.get(`${this.apiUrl}/department/${this.departmentId}/teams`, {
          timeout: 10000,
          headers: {
            'Content-Type': 'application/json',
          },
        })

        this.teams = Array.isArray(response.data) ? response.data : []
      } catch (err) {
        console.error('Ошибка загрузки команд:', err)
        this.teams = []
        // Не бросаем ошибку, так как это не критично
      } finally {
        this.teamsLoading = false
      }
    },

    async loadRecentTickets() {
      this.ticketsLoading = true
      try {
        const response = await axios.get(`${this.apiUrl}/department/${this.departmentId}/tickets`, {
          params: { limit: 4 },
          timeout: 10000,
          headers: {
            'Content-Type': 'application/json',
          },
        })

        this.recentTickets = Array.isArray(response.data) ? response.data : []
      } catch (err) {
        console.error('Ошибка загрузки тикетов:', err)
        this.recentTickets = []
        // Не бросаем ошибку, так как это не критично
      } finally {
        this.ticketsLoading = false
      }
    },

    getErrorMessage(error) {
      if (error.response) {
        // Сервер ответил с кодом ошибки
        const status = error.response.status
        const message = error.response.data?.message || error.response.data?.error

        switch (status) {
          case 401:
            return 'Не авторизован. Пожалуйста, войдите в систему заново.'
          case 403:
            return 'Недостаточно прав для доступа к данным отдела.'
          case 404:
            return 'Отдел не найден или данные недоступны.'
          case 500:
            return 'Внутренняя ошибка сервера. Попробуйте позже.'
          default:
            return message || `Ошибка сервера (${status}). Попробуйте позже.`
        }
      } else if (error.request) {
        // Запрос был отправлен, но ответ не получен
        return 'Сервер недоступен. Проверьте подключение к интернету.'
      } else {
        // Ошибка при настройке запроса
        return error.message || 'Произошла неожиданная ошибка.'
      }
    },

    // Навигационные мет��ды
    navigateToTickets() {
      this.$router.push({ name: 'DepartmentTickets' }).catch((err) => {
        console.warn('Маршрут DepartmentTickets не найден:', err)
        // Fallback навигация
        this.$router.push('/department/tickets').catch(() => {
          console.error('Не удалось перейти к тикетам отдела')
        })
      })
    },

    navigateToCreateTicket() {
      this.$router.push({ name: 'TicketsDepartment' }).catch((err) => {
        console.warn('Маршрут TicketsDepartment не найден:', err)
        // Fallback навигация
        this.$router.push('/tickets/create').catch(() => {
          console.error('Не удалось перейти к созданию тикета')
        })
      })
    },

    navigateToTeamManagement() {
      this.$router.push({ name: 'TeamMembers' }).catch((err) => {
        console.warn('Маршрут TeamMembers не найден:', err)
        // Fallback навигация
        this.$router.push('/teams').catch(() => {
          console.error('Не удалось перейти к управлению командами')
        })
      })
    },

    // Методы стилизации
    getTicketCardClass(priority) {
      const baseClass = 'border-l-4 '
      switch (priority) {
        case 'HIGH':
        case 'Высокий':
          return (
            baseClass + 'bg-gradient-to-r from-red-50 to-pink-50 border-red-200 border-l-red-500'
          )
        case 'MEDIUM':
        case 'Средний':
          return (
            baseClass +
            'bg-gradient-to-r from-amber-50 to-yellow-50 border-amber-200 border-l-amber-500'
          )
        case 'LOW':
        case 'Низкий':
          return (
            baseClass +
            'bg-gradient-to-r from-emerald-50 to-green-50 border-emerald-200 border-l-emerald-500'
          )
        default:
          return baseClass + 'bg-slate-50 border-slate-200 border-l-slate-400'
      }
    },

    getTicketDotClass(priority) {
      switch (priority) {
        case 'HIGH':
        case 'Высокий':
          return 'bg-red-500 animate-pulse'
        case 'MEDIUM':
        case 'Средний':
          return 'bg-amber-500'
        case 'LOW':
        case 'Низкий':
          return 'bg-emerald-500'
        default:
          return 'bg-slate-400'
      }
    },

    getPriorityClass(priority) {
      switch (priority) {
        case 'HIGH':
        case 'Высокий':
          return 'bg-red-100 text-red-800'
        case 'MEDIUM':
        case 'Средний':
          return 'bg-amber-100 text-amber-800'
        case 'LOW':
        case 'Низкий':
          return 'bg-emerald-100 text-emerald-800'
        default:
          return 'bg-slate-100 text-slate-800'
      }
    },

    getPriorityLabel(priority) {
      switch (priority) {
        case 'HIGH':
          return 'Высокий'
        case 'MEDIUM':
          return 'Средний'
        case 'LOW':
          return 'Низкий'
        default:
          return priority || 'Не указан'
      }
    },
  },
}
</script>

<style scoped>
/* Анимации для карточек */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fadeIn {
  animation: fadeIn 0.3s ease-out forwards;
}

/* Улучшенные стили для индикаторов загрузки */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.animate-spin {
  animation: spin 1s linear infinite;
}

/* Стили для hover эффектов */
.group:hover .group-hover\:scale-110 {
  transform: scale(1.1);
}

.group:hover .group-hover\:translate-x-1 {
  transform: translateX(0.25rem);
}
</style>
