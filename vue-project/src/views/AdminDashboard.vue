<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок панели -->
      <div class="mb-8">
        <h2 class="text-3xl font-bold text-slate-900">Панель управления</h2>
        <p class="mt-2 text-slate-600">
          Добро пожаловать! Вот что происходит с вашей системой тикетов.
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
                class="w-12 h-12 bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl flex items-center justify-center shadow-lg"
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
              <p class="text-3xl font-bold text-slate-900">
                {{ statistics ? statistics.totalTickets : '—' }}
              </p>
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
                    d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-slate-600">В ожидании</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ statistics ? statistics.pendingTickets : '—' }}
              </p>
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
              <p class="text-sm font-medium text-slate-600">Решено</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ statistics ? resolvedTickets : '—' }}
              </p>
            </div>
          </div>
        </div>

        <div
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-purple-500 to-indigo-500 rounded-xl flex items-center justify-center shadow-lg"
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
              <p class="text-sm font-medium text-slate-600">Активные пользователи</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ statistics ? activeUsers : '—' }}
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- Основной контент -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Последние тикеты -->
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
                Последние тикеты
              </h3>
            </div>
          </div>
          <div class="p-6">
            <div class="space-y-4">
              <div
                v-for="ticket in recentTickets"
                :key="ticket.id"
                :class="getTicketCardClass(ticket.priority)"
              >
                <div class="flex items-start justify-between w-full">
                  <!-- Левая часть с основной информацией -->
                  <div class="flex items-start space-x-4 flex-1 min-w-0">
                    <div
                      :class="getTicketDotClass(ticket.priority)"
                      class="mt-1.5 flex-shrink-0"
                    ></div>
                    <div class="flex-1 min-w-0">
                      <p class="text-sm font-semibold text-slate-900 mb-3 leading-tight">
                        {{ ticket.title }}
                      </p>
                      <div class="flex flex-wrap items-center gap-x-4 gap-y-2">
                        <!-- Отдел -->
                        <div class="flex items-center text-xs text-slate-600">
                          <svg
                            class="w-3.5 h-3.5 mr-1.5 text-slate-500"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                          >
                            <path
                              stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                            />
                          </svg>
                          <span class="font-medium">{{ ticket.department }}</span>
                        </div>
                        <!-- Команда -->
                        <div class="flex items-center text-xs text-slate-600">
                          <svg
                            class="w-3.5 h-3.5 mr-1.5 text-slate-500"
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
                          <span class="font-medium">{{ ticket.team }}</span>
                        </div>
                        <!-- Время до завершения -->
                        <div
                          class="flex items-center text-xs font-medium"
                          :class="getTimeRemainingClass(ticket.timeRemaining)"
                        >
                          <svg
                            class="w-3.5 h-3.5 mr-1.5"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                          >
                            <path
                              stroke-linecap="round"
                              stroke-linejoin="round"
                              stroke-width="2"
                              d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                            />
                          </svg>
                          <span>{{ ticket.timeRemaining }}</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Правая часть с приоритетом и статусом -->
                  <div class="flex flex-col items-end space-y-2 ml-4 flex-shrink-0">
                    <span :class="getPriorityClass(ticket.priority)" class="whitespace-nowrap">
                      {{ ticket.priority }}
                    </span>
                    <span :class="getStatusClass(ticket.status)" class="whitespace-nowrap">
                      {{ ticket.status }}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="mt-6 pt-4 border-t border-slate-100">
              <RouterLink
                :to="{ name: 'TicketsAdmin' }"
                class="flex items-center justify-center space-x-2 w-full text-sm text-blue-600 hover:text-blue-800 font-semibold py-3 px-4 rounded-lg hover:bg-blue-50 transition-all duration-200 group"
              >
                <span>Посмотреть все тикеты</span>
                <svg
                  class="w-4 h-4 transform group-hover:translate-x-1 transition-transform duration-200"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5l7 7-7 7"
                  />
                </svg>
              </RouterLink>
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
          <div class="p-6">
            <div class="grid grid-cols-1 gap-4">
              <router-link
                :to="{ name: 'TicketsAdmin' }"
                class="flex items-center p-4 border-2 border-dashed border-slate-300 rounded-xl hover:border-blue-500 hover:bg-gradient-to-r hover:from-blue-50 hover:to-indigo-50 transition-all duration-300 group"
              >
                <div
                  class="w-10 h-10 bg-gradient-to-br from-blue-500 to-indigo-500 rounded-lg flex items-center justify-center mr-4 group-hover:scale-110 transition-transform duration-200"
                >
                  <svg
                    class="w-5 h-5 text-white"
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
              </router-link>

              <router-link
                :to="{ name: 'AdminUsers' }"
                class="flex items-center p-4 border-2 border-dashed border-slate-300 rounded-xl hover:border-purple-500 hover:bg-gradient-to-r hover:from-purple-50 hover:to-pink-50 transition-all duration-300 group"
              >
                <div
                  class="w-10 h-10 bg-gradient-to-br from-purple-500 to-pink-500 rounded-lg flex items-center justify-center mr-4 group-hover:scale-110 transition-transform duration-200"
                >
                  <svg
                    class="w-5 h-5 text-white"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"
                    />
                  </svg>
                </div>
                <div class="text-left">
                  <span class="text-sm font-semibold text-slate-900">Добавить пользователя</span>
                  <p class="text-xs text-slate-600">Новый участник</p>
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
import axios from 'axios'
import { RouterLink } from 'vue-router'

export default {
  name: 'AdminDashboard',
  components: {
    Header,
  },
  data() {
    return {
      statistics: null,
      recentTickets: [
        {
          id: 1,
          title: 'Проблема с авторизацией',
          priority: 'Высокий',
          status: 'В работе',
          department: 'IT отдел',
          team: 'Backend команда',
          timeRemaining: '2 часа',
        },
        {
          id: 2,
          title: 'Запрос функции - Темная тема',
          priority: 'Средний',
          status: 'Ожидание',
          department: 'Разработка',
          team: 'Frontend команда',
          timeRemaining: '1 день',
        },
        {
          id: 3,
          title: 'Ошибка - Кнопка не работает',
          priority: 'Низкий',
          status: 'Назначен',
          department: 'QA отдел',
          team: 'Тестирование',
          timeRemaining: '3 дня',
        },
        {
          id: 4,
          title: 'Обновление базы данных',
          priority: 'Высокий',
          status: 'Критический',
          department: 'IT отдел',
          team: 'DevOps команда',
          timeRemaining: 'Просрочен на 1 час',
        },
      ],
    }
  },
  computed: {
    resolvedTickets() {
      return this.statistics ? this.statistics.resolvedTickets || 0 : 0
    },
    activeUsers() {
      return this.statistics ? this.statistics.activeUsers || 0 : 0
    },
  },
  methods: {
    async fetchStatistics() {
      const user = JSON.parse(localStorage.getItem('user'))
      if (!user) return

      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/stats`, {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
          params: {
            login: user.login,
          },
        })

        this.statistics = response.data
      } catch (err) {
        console.error('Ошибка загрузки статистики:', err)
        this.statistics = null
      }
    },
    getTicketCardClass(priority) {
      const baseClass =
        'flex items-center justify-between p-4 rounded-xl border hover:shadow-md transition-all duration-200 '

      switch (priority) {
        case 'Высокий':
          return baseClass + 'bg-gradient-to-r from-red-50 to-pink-50 border-red-100'
        case 'Средний':
          return baseClass + 'bg-gradient-to-r from-amber-50 to-yellow-50 border-amber-100'
        case 'Низкий':
          return baseClass + 'bg-gradient-to-r from-emerald-50 to-green-50 border-emerald-100'
        default:
          return baseClass + 'bg-slate-50 border-slate-200'
      }
    },
    getTicketDotClass(priority) {
      const baseClass = 'w-3 h-3 rounded-full '

      switch (priority) {
        case 'Высокий':
          return baseClass + 'bg-red-500 animate-pulse'
        case 'Средний':
          return baseClass + 'bg-amber-500'
        case 'Низкий':
          return baseClass + 'bg-emerald-500'
        default:
          return baseClass + 'bg-slate-400'
      }
    },
    getPriorityClass(priority) {
      const baseClass = 'px-3 py-1 text-xs font-semibold rounded-full '

      switch (priority) {
        case 'Высокий':
          return baseClass + 'bg-red-100 text-red-800'
        case 'Средний':
          return baseClass + 'bg-amber-100 text-amber-800'
        case 'Низкий':
          return baseClass + 'bg-emerald-100 text-emerald-800'
        default:
          return baseClass + 'bg-slate-100 text-slate-800'
      }
    },
    getStatusClass(status) {
      const baseClass = 'px-2 py-1 text-xs font-medium rounded '

      switch (status) {
        case 'В работе':
          return baseClass + 'bg-blue-100 text-blue-800'
        case 'Ожидание':
          return baseClass + 'bg-yellow-100 text-yellow-800'
        case 'Назначен':
          return baseClass + 'bg-purple-100 text-purple-800'
        case 'Критический':
          return baseClass + 'bg-red-100 text-red-800'
        default:
          return baseClass + 'bg-slate-100 text-slate-800'
      }
    },
    getTimeRemainingClass(timeRemaining) {
      if (timeRemaining.includes('Просрочен')) {
        return 'text-red-600 font-semibold'
      } else if (timeRemaining.includes('час')) {
        return 'text-orange-600'
      } else {
        return 'text-slate-600'
      }
    },
  },
  mounted() {
    this.fetchStatistics()
  },
}
</script>
