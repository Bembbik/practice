<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <!-- Основной контент -->
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок с поиском -->
      <div class="mb-8">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 class="text-3xl font-bold text-slate-900">Управление тикетами</h1>
            <p class="mt-2 text-slate-600">Организуйте и отслеживайте обращения по командам</p>
          </div>
          <!-- Поиск -->
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
              placeholder="Поиск тикетов..."
              class="block w-full pl-10 pr-3 py-2 border border-slate-300 rounded-lg leading-5 bg-white placeholder-slate-500 focus:outline-none focus:placeholder-slate-400 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
            />
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
                    d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"
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
        <!-- Высокоприоритетные тикеты -->
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
              <p class="text-sm font-medium text-slate-600">Высокоприоритетные</p>
              <p class="text-3xl font-bold text-slate-900">
                {{ highPriorityCount }}
              </p>
            </div>
          </div>
        </div>
        <!-- Активные тикеты -->
        <div
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-blue-500 to-indigo-500 rounded-xl flex items-center justify-center shadow-lg"
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
              <p class="text-sm font-medium text-slate-600">Активные тикеты</p>
              <p class="text-3xl font-bold text-slate-900">{{ activeTicketsCount }}</p>
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
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                />
              </svg>
              Команды
            </h2>
          </div>
          <div class="p-4">
            <ul class="space-y-2">
              <li
                v-for="team in teams"
                :key="team.id"
                @click="selectTeam(team)"
                class="cursor-pointer px-4 py-3 rounded-lg hover:bg-gradient-to-r hover:from-purple-50 hover:to-pink-50 transition-all duration-200 group"
                :class="{
                  'bg-gradient-to-r from-purple-100 to-pink-100 border border-purple-200':
                    team.id === selectedTeam?.id,
                  'hover:shadow-md': team.id !== selectedTeam?.id,
                }"
              >
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <span class="font-medium text-slate-900">{{ team.name }}</span>
                  </div>
                  <div class="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <!-- Тикеты выбранной команды -->
        <div class="lg:col-span-3 bg-white rounded-xl shadow-lg border border-slate-200">
          <div
            class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-xl"
          >
            <div class="flex items-center justify-between">
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
                    d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                  />
                </svg>
                Тикеты
                <span v-if="selectedTeam" class="ml-2 text-sm text-slate-500"
                  >— {{ selectedTeam.name }}</span
                >
              </h2>
              <button
                @click="openNewTicket"
                :disabled="!selectedTeam"
                class="flex items-center px-4 py-2 bg-gradient-to-r from-blue-600 to-indigo-600 text-white text-sm font-medium rounded-lg hover:from-blue-700 hover:to-indigo-700 transition-all duration-200 shadow-lg hover:shadow-xl transform hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
              >
                <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                  />
                </svg>
                Новый тикет
              </button>
            </div>
          </div>

          <!-- Фильтры статусов -->
          <div v-if="selectedTeam" class="px-6 py-4 border-b border-slate-200 bg-slate-50">
            <div class="flex flex-wrap gap-2">
              <button
                v-for="filter in statusFilters"
                :key="filter.value"
                @click="selectedStatusFilter = filter.value"
                class="px-3 py-1.5 text-sm font-medium rounded-lg transition-all duration-200"
                :class="{
                  'bg-blue-600 text-white shadow-md': selectedStatusFilter === filter.value,
                  'bg-white text-slate-600 hover:bg-slate-100 border border-slate-200':
                    selectedStatusFilter !== filter.value,
                }"
              >
                {{ filter.label }}
                <span class="ml-1 text-xs opacity-75">({{ getFilterCount(filter.value) }})</span>
              </button>
            </div>
          </div>

          <div class="p-6">
            <div v-if="selectedTeam && filteredTickets.length" class="space-y-4">
              <div
                v-for="ticket in filteredTickets"
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
                      <!-- Описание тикета -->
                      <div
                        v-if="ticket.description"
                        class="mt-3 text-xs text-slate-700 line-clamp-2"
                      >
                        {{ ticket.description }}
                      </div>
                      <div class="flex flex-wrap items-center mt-2 gap-x-4 gap-y-2">
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
                  <!-- Правая часть с приоритетом, статусом и действиями -->
                  <div class="flex flex-col items-end space-y-3 ml-4 flex-shrink-0">
                    <!-- Приоритет и статус -->
                    <div class="flex flex-col items-end space-y-2">
                      <span :class="getPriorityClass(ticket.priority)" class="whitespace-nowrap">
                        {{ getPriorityLabel(ticket.priority) }}
                      </span>
                      <span :class="getStatusClass(ticket.status)" class="whitespace-nowrap">
                        {{ getStatusLabel(ticket.status) }}
                      </span>
                    </div>
                    <!-- Действия -->
                    <div
                      class="flex flex-col-reverse space-y-2 space-y-reverse sm:flex-row sm:space-y-0 sm:space-x-2"
                    >
                      <!-- Действия для закрытых тикетов -->
                      <template v-if="ticket.status === 'CLOSED'">
                        <button
                          @click="completeTicket(ticket.id)"
                          class="flex items-center px-3 py-1.5 text-xs font-medium text-green-600 hover:text-green-800 hover:bg-green-50 rounded-lg transition-all duration-200 border border-green-200 hover:border-green-300"
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
                              d="M5 13l4 4L19 7"
                            />
                          </svg>
                          Завершить
                        </button>
                        <button
                          @click="reopenTicket(ticket.id)"
                          class="flex items-center px-3 py-1.5 text-xs font-medium text-orange-600 hover:text-orange-800 hover:bg-orange-50 rounded-lg transition-all duration-200 border border-orange-200 hover:border-orange-300"
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
                              d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6"
                            />
                          </svg>
                          Вернуть
                        </button>
                      </template>
                      <!-- Обычные действия для других статусов -->
                      <template v-else>
                        <button
                          @click="deleteTicket(ticket.id)"
                          class="flex items-center px-3 py-1.5 text-xs font-medium text-red-600 hover:text-red-800 hover:bg-red-50 rounded-lg transition-all duration-200 border border-red-200 hover:border-red-300"
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
                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                            />
                          </svg>
                          Удалить
                        </button>
                        <button
                          @click="openEditTicket(ticket.id)"
                          class="flex items-center px-3 py-1.5 text-xs font-medium text-blue-600 hover:text-blue-800 hover:bg-blue-50 rounded-lg transition-all duration-200 border border-blue-200 hover:border-blue-300"
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
                              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                            />
                          </svg>
                          Редактировать
                        </button>
                      </template>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-12">
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
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
              <p class="mt-2 text-sm text-slate-500">
                {{ selectedTeam ? 'Тикетов не найдено' : 'Выберите команду для просмотра тикетов' }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Форма создания/редактирования тикета -->
    <TicketForm
      v-if="showTicketForm"
      :ticket="currentTicket"
      @save="saveTicket"
      @close="closeTicketForm"
    />
  </div>
</template>
<script>
import axios from 'axios'
import TicketForm from '@/components/department/TicketForm.vue'

export default {
  name: 'TicketsDepartment',
  components: {
    TicketForm,
  },
  data() {
    return {
      searchQuery: '',
      teams: [],
      selectedTeam: null,
      tickets: [],
      showTicketForm: false,
      currentTicket: null,
      selectedStatusFilter: 'all',
      statusFilters: [
        { value: 'all', label: 'Все' },
        { value: 'open', label: 'Открытые' },
        { value: 'closed', label: 'Закрытые' },
        { value: 'completed', label: 'Завершенные' },
      ],
    }
  },
  computed: {
    highPriorityCount() {
      return this.tickets.filter((t) => t.priority === 'HIGH').length
    },
    activeTicketsCount() {
      return this.tickets.filter((t) => t.status !== 'COMPLETED').length
    },
    filteredTickets() {
      if (!this.selectedTeam) return []

      // Получаем тикеты только выбранной команды
      let filtered = this.tickets.filter((ticket) => ticket.teamId === this.selectedTeam.id)

      // Фильтр по поиску
      if (this.searchQuery) {
        filtered = filtered.filter(
          (ticket) =>
            ticket.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            ticket.team.toLowerCase().includes(this.searchQuery.toLowerCase()),
        )
      }

      // Фильтр по статусу
      if (this.selectedStatusFilter !== 'all') {
        filtered = filtered.filter((ticket) => {
          switch (this.selectedStatusFilter) {
            case 'open':
              return ticket.status === 'OPEN'
            case 'closed':
              return ticket.status === 'CLOSED'
            case 'completed':
              return ticket.status === 'COMPLETED'
            default:
              return true
          }
        })
      }

      return filtered
    },
  },
  methods: {
    getFilterCount(filterValue) {
      if (!this.selectedTeam) return 0

      const teamTickets = this.tickets.filter((ticket) => ticket.teamId === this.selectedTeam.id)

      switch (filterValue) {
        case 'all':
          return teamTickets.length
        case 'open':
          return teamTickets.filter((t) => t.status === 'OPEN').length
        case 'closed':
          return teamTickets.filter((t) => t.status === 'CLOSED').length
        case 'completed':
          return teamTickets.filter((t) => t.status === 'COMPLETED').length
        default:
          return 0
      }
    },
    completeTicket(ticketId) {
      if (!confirm('Вы уверены, что хотите завершить этот тикет?')) return

      const ticketIndex = this.tickets.findIndex((t) => t.id === ticketId)
      if (ticketIndex !== -1) {
        this.tickets[ticketIndex].status = 'COMPLETED'
        // Здесь можно добавить API вызов для обновления статуса на сервере
        console.log('Тикет завершен:', ticketId)
      }
    },
    reopenTicket(ticketId) {
      if (!confirm('Вы уверены, что хотите вернуть этот тикет в работу?')) return

      const ticketIndex = this.tickets.findIndex((t) => t.id === ticketId)
      if (ticketIndex !== -1) {
        this.tickets[ticketIndex].status = 'OPEN'
        // Здесь можно добавить API вызов для обновления статуса на сервере
        console.log('Тикет возвращен в работу:', ticketId)
      }
    },
    calculateTimeRemaining(deadline) {
      const now = new Date()
      const deadlineDate = new Date(deadline)
      const diffDays = Math.ceil((deadlineDate - now) / (1000 * 60 * 60 * 24))
      if (diffDays < 0) {
        return 'Просрочен'
      } else if (diffDays === 0) {
        return 'Сегодня'
      } else if (diffDays === 1) {
        return '1 день'
      } else {
        return `${diffDays} дней`
      }
    },
    openNewTicket() {
      if (!this.selectedTeam) return

      this.currentTicket = {
        id: null,
        title: '',
        description: '',
        priority: 'MEDIUM',
        deadline: '',
        teamId: this.selectedTeam.id,
        departmentId: this.selectedTeam.departmentId,
      }
      this.showTicketForm = true
    },
    openEditTicket(ticketId) {
      const ticket = this.tickets.find((t) => t.id === ticketId)
      if (ticket) {
        this.currentTicket = { ...ticket }
        this.showTicketForm = true
      }
    },
    async fetchTeamsAndTickets() {
      const user = JSON.parse(localStorage.getItem('user'))
      const departmentId = user?.departmentId

      if (!departmentId) {
        console.error('departmentId не найден')
        return
      }

      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams-with-tickets`,
        )
        const data = response.data

        console.log('Ответ от сервера:', data)

        // Сохраняем исходные данные в localStorage
        const teams = data?.teams || []
        localStorage.setItem('teams', JSON.stringify(teams))

        const allTickets = []

        for (const team of teams) {
          if (team.tickets && Array.isArray(team.tickets)) {
            allTickets.push(
              ...team.tickets.map((ticket) => ({
                ...ticket,
                team: team.name,
                teamId: team.id,
                departmentId: team.departmentId,
                timeRemaining: this.calculateTimeRemaining(ticket.deadline),
              })),
            )
          }
        }

        // Сохраняем обработанные тикеты
        localStorage.setItem('team_tickets', JSON.stringify(allTickets))

        // Можно также сохранить timestamp для проверки актуальности данных
        localStorage.setItem('lastFetched', Date.now())

        // Если нужно — сохраните и в состояние компонента
        this.teams = teams
        this.tickets = allTickets
      } catch (error) {
        console.error('Ошибка загрузки данных:', error)
      }
    },
    selectTeam(team) {
      this.selectedTeam = team
      this.selectedStatusFilter = 'all' // Сбрасываем фильтр при выборе новой команды
    },
    getTicketCardClass(priority) {
      const base =
        'p-5 rounded-xl border-l-4 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5 '
      switch (priority) {
        case 'HIGH':
          return base + 'border-red-400 bg-red-50'
        case 'MEDIUM':
          return base + 'border-yellow-400 bg-yellow-50'
        case 'LOW':
          return base + 'border-green-400 bg-green-50'
        default:
          return base + 'border-slate-300 bg-slate-50'
      }
    },
    getTicketDotClass(priority) {
      switch (priority) {
        case 'HIGH':
          return 'w-3 h-3 rounded-full bg-red-500 animate-pulse'
        case 'MEDIUM':
          return 'w-3 h-3 rounded-full bg-yellow-500'
        case 'LOW':
          return 'w-3 h-3 rounded-full bg-green-500'
        default:
          return 'w-3 h-3 rounded-full bg-slate-400'
      }
    },
    getPriorityClass(priority) {
      switch (priority) {
        case 'HIGH':
          return 'bg-red-100 text-red-800 border-red-200 px-2 py-1 text-xs font-medium rounded'
        case 'MEDIUM':
          return 'bg-yellow-100 text-yellow-800 border-yellow-200 px-2 py-1 text-xs font-medium rounded'
        case 'LOW':
          return 'bg-green-100 text-green-800 border-green-200 px-2 py-1 text-xs font-medium rounded'
        default:
          return 'bg-slate-100 text-slate-800 border-slate-200 px-2 py-1 text-xs font-medium rounded'
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
          return priority
      }
    },
    getStatusClass(status) {
      switch (status) {
        case 'OPEN':
          return 'bg-blue-100 text-blue-800 border-blue-200 px-2 py-1 text-xs font-medium rounded'
        case 'CLOSED':
          return 'bg-orange-100 text-orange-800 border-orange-200 px-2 py-1 text-xs font-medium rounded'
        case 'COMPLETED':
          return 'bg-green-100 text-green-800 border-green-200 px-2 py-1 text-xs font-medium rounded'
        default:
          return 'bg-slate-100 text-slate-800 border-slate-200 px-2 py-1 text-xs font-medium rounded'
      }
    },
    getStatusLabel(status) {
      switch (status) {
        case 'OPEN':
          return 'Открыт'
        case 'CLOSED':
          return 'Закрыт'
        case 'COMPLETED':
          return 'Завершен'
        default:
          return status
      }
    },
    getTimeRemainingClass(timeRemaining) {
      if (timeRemaining.includes('Просрочен')) {
        return 'text-red-700 bg-red-50 px-2 py-1 rounded-md border border-red-200'
      } else if (timeRemaining.includes('час')) {
        return 'text-orange-700 bg-orange-50 px-2 py-1 rounded-md border border-orange-200'
      } else {
        return ''
      }
    },
    saveTicket(formData) {
      this.showTicketForm = false
      this.fetchTeamsAndTickets()
    },
    closeTicketForm() {
      this.showTicketForm = false
    },
    async deleteTicket(id) {
      if (!confirm('Вы уверены, что хотите удалить этот тикет?')) return

      try {
        const ticketToDelete = this.tickets.find((t) => t.id === id)

        if (!ticketToDelete) {
          alert('Тикет не найден')
          return
        }

        // Получаем departmentId из localStorage
        const user = JSON.parse(localStorage.getItem('user'))
        const departmentId = user?.departmentId

        if (!departmentId) {
          throw new Error('departmentId не найден в localStorage')
        }

        // Формируем данные для отправки
        const deleteData = {
          title: ticketToDelete.title,
          description: ticketToDelete.description,
          deadline: ticketToDelete.deadline,
          status: ticketToDelete.status,
          priority: ticketToDelete.priority,
          teamId: ticketToDelete.teamId,
        }

        // Выполняем DELETE-запрос с данными в теле
        await axios.delete(
          `${import.meta.env.VITE_API_URL}/department/${departmentId}/tickets/${id}`,
          {
            data: deleteData,
            headers: {
              'Content-Type': 'application/json',
            },
          },
        )

        // Удаляем тикет из локального списка
        this.tickets = this.tickets.filter((t) => t.id !== id)
      } catch (error) {
        console.error('Ошибка при удалении тикета:', error)
        alert('Не удалось удалить тикет. Проверьте соединение или попробуйте позже.')
      }
    },
  },
  mounted() {
    this.fetchTeamsAndTickets()
  },
}
</script>
