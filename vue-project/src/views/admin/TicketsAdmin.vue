<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <!-- Основной контент -->
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок с поиском -->
      <div class="mb-8">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 class="text-3xl font-bold text-slate-900">Управление тикетами</h1>
            <p class="mt-2 text-slate-600">
              Организуйте и отслеживайте обращения по отделам и командам
            </p>
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

      <!-- Отделы и статистика -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <!-- Всего отделов -->
        <div
          class="bg-white rounded-xl shadow-lg p-6 border border-slate-200 hover:shadow-xl transition-all duration-300"
        >
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div
                class="w-12 h-12 bg-gradient-to-br from-blue-500 to-purple-500 rounded-xl flex items-center justify-center shadow-lg"
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
                    d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                  />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-slate-600">Всего отделов</p>
              <p class="text-3xl font-bold text-slate-900">{{ departments.length }}</p>
            </div>
          </div>
        </div>

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
              <p class="text-3xl font-bold text-slate-900">
                {{ departments.reduce((sum, dept) => sum + (dept.teams?.length || 0), 0) }}
              </p>
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
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-4 gap-6">
        <!-- Выбор отдела -->
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
                  d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                />
              </svg>
              Отделы
            </h2>
          </div>
          <div class="p-4">
            <ul class="space-y-2">
              <li
                v-for="department in departments"
                :key="department.id"
                @click="selectDepartment(department)"
                class="cursor-pointer px-4 py-3 rounded-lg hover:bg-gradient-to-r hover:from-blue-50 hover:to-indigo-50 transition-all duration-200 group"
                :class="{
                  'bg-gradient-to-r from-blue-100 to-indigo-100 border border-blue-200':
                    department.id === selectedDepartment?.id,
                  'hover:shadow-md': department.id !== selectedDepartment?.id,
                }"
              >
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <span class="font-medium text-slate-900">{{ department.department }}</span>
                  </div>
                  <span class="text-xs bg-slate-100 text-slate-600 px-2 py-1 rounded-full">
                    {{ department.teams.length }} команд{{
                      department.teams.length === 1 ? 'а' : ''
                    }}
                  </span>
                </div>
              </li>
            </ul>
          </div>
        </div>

        <!-- Команды текущего отдела -->
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
            <div v-if="selectedDepartment">
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
                    <div class="flex items-center space-x-2">
                      <span class="text-xs bg-slate-100 text-slate-600 px-2 py-1 rounded-full">
                        {{ team.tickets?.length || 0 }} тикет{{
                          getTicketWordEnding(team.tickets?.length || 0)
                        }}
                      </span>
                      <div class="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
                    </div>
                  </div>
                </li>
              </ul>
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
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                />
              </svg>
              <p class="mt-2 text-sm text-slate-500">Выберите отдел для просмотра команд</p>
            </div>
          </div>
        </div>

        <!-- Тикеты выбранной команды -->
        <div class="lg:col-span-2 bg-white rounded-xl shadow-lg border border-slate-200">
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
                          <span class="font-medium">{{ selectedDepartment.department }}</span>
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
                          <span class="font-medium">{{ selectedTeam.name }}</span>
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
                        {{ ticket.priority }}
                      </span>
                      <span :class="getStatusClass(ticket.status)" class="whitespace-nowrap">
                        {{ ticket.status }}
                      </span>
                    </div>

                    <!-- Действия -->
                    <div
                      class="flex flex-col-reverse space-y-2 space-y-reverse sm:flex-row sm:space-y-0 sm:space-x-2"
                    >
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
                {{
                  !selectedDepartment
                    ? 'Выберите отдел и команду для просмотра тикетов'
                    : !selectedTeam
                      ? 'Выберите команду для просмотра тикетов'
                      : 'Тикетов не найдено'
                }}
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
import TicketForm from '@/components/admin/TicketForm.vue'

export default {
  name: 'AdminTickets',
  components: {
    TicketForm,
  },
  data() {
    return {
      searchQuery: '',
      departments: [],
      selectedDepartment: null,
      selectedTeam: null,
      showTicketForm: false,
      isEditing: false,
      currentTicket: null,
    }
  },
  computed: {
    teams() {
      return this.selectedDepartment ? this.selectedDepartment.teams : []
    },
    totalTeams() {
      return this.departments.reduce((total, dept) => total + dept.teams.length, 0)
    },
    highPriorityCount() {
      let count = 0
      for (const dept of this.departments) {
        for (const team of dept.teams || []) {
          if (team.tickets && Array.isArray(team.tickets)) {
            count += team.tickets.filter(
              (ticket) => ticket.priority === 'HIGH' || ticket.priority === 'Высокий',
            ).length
          }
        }
      }
      return count
    },
    filteredTickets() {
      if (!this.selectedTeam || !this.selectedTeam.tickets) return []

      const tickets = this.selectedTeam.tickets.map((ticket) => ({
        ...ticket,
        timeRemaining: this.calculateTimeRemaining(ticket.deadline),
      }))

      if (!this.searchQuery) return tickets

      return tickets.filter(
        (ticket) =>
          ticket.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          (ticket.description &&
            ticket.description.toLowerCase().includes(this.searchQuery.toLowerCase())),
      )
    },
  },
  methods: {
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
    getTicketWordEnding(count) {
      if (count === 1) return ''
      if (count >= 2 && count <= 4) return 'а'
      return 'ов'
    },
    openNewTicket() {
      if (!this.selectedTeam) {
        alert('Сначала выберите команду')
        return
      }

      this.currentTicket = {
        id: null,
        title: '',
        description: '',
        priority: 'Средний',
        deadline: '',
        departmentId: this.selectedDepartment.id,
        teamId: this.selectedTeam.id,
      }
      this.showTicketForm = true
    },
    openEditTicket(id) {
      if (!this.selectedTeam || !this.selectedTeam.tickets) {
        alert('Тикет не найден')
        return
      }

      const foundTicket = this.selectedTeam.tickets.find((t) => t.id === id)
      if (!foundTicket) {
        alert('Тикет не найден')
        return
      }

      this.currentTicket = {
        ...foundTicket,
        departmentId: this.selectedDepartment.id,
        teamId: this.selectedTeam.id,
      }
      this.showTicketForm = true
    },

    closeTicketForm() {
      this.showTicketForm = false
    },
    async saveTicket(formData) {
      try {
        // Закрываем форму сразу, т.к. данные успешно отправлены
        this.showTicketForm = false

        // Обновляем данные с сервера
        await this.fetchHierarchy()

        // Показываем уведомление (если используется notify-библиотека)
        if (this.$notify?.success) {
          this.$notify.success('Тикет успешно сохранён')
        }
      } catch (error) {
        console.error('Ошибка при обновлении данных:', error)

        // Если нужно — можно показать предупреждение пользователю
        alert('Не удалось загрузить обновлённые данные. Попробуйте перезагрузить страницу.')
      }
    },
    async fetchHierarchy() {
      const user = JSON.parse(localStorage.getItem('user'))
      if (!user) {
        this.error = 'Пользователь не авторизован'
        return
      }

      this.isLoading = true
      this.error = null

      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/admin-ticket`, {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
          params: {
            login: user.login,
          },
        })

        this.departments = response.data

        // Сохраняем данные в localStorage
        localStorage.setItem('ticketHierarchy', JSON.stringify(response.data))

        // ... deleted code ... (removed automatic department selection and tickets array creation)
      } catch (err) {
        console.error('Ошибка загрузки иерархии:', err)
        this.error = 'Не удалось загрузить данные'
      } finally {
        this.isLoading = false
      }
    },
    selectDepartment(department) {
      this.selectedDepartment = department
      this.selectedTeam = null // Сбрасываем выбранную команду при смене отдела
    },
    selectTeam(team) {
      this.selectedTeam = team
    },
    editTicket(id) {
      this.$router.push({ name: 'AdminTicketEdit', params: { id } })
    },
    async deleteTicket(id) {
      if (!confirm('Вы уверены, что хотите удалить этот тикет?')) {
        return
      }

      const apiUrl = import.meta.env.VITE_API_URL

      try {
        await axios.delete(`${apiUrl}/admin/tickets/${id}`, {
          headers: {
            'Content-Type': 'application/json',
          },
        })

        // После успешного удаления — обновляем данные из API
        await this.fetchHierarchy()

        // Показываем уведомление (если используется notify-библиотека)
        if (this.$notify?.success) {
          this.$notify.success('Тикет успешно удален')
        }
      } catch (error) {
        console.error('Ошибка при удалении тикета:', error)
        alert('Не удалось удалить тикет. Попробуйте еще раз.')
      }
    },
    getTicketCardClass(priority) {
      const baseClass =
        'p-5 rounded-xl border-l-4 hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5 '

      switch (priority) {
        case 'Высокий':
          return (
            baseClass +
            'bg-gradient-to-r from-red-50 via-red-25 to-pink-50 border-red-400 hover:from-red-100 hover:to-pink-100'
          )
        case 'Средний':
          return (
            baseClass +
            'bg-gradient-to-r from-amber-50 via-amber-25 to-yellow-50 border-amber-400 hover:from-amber-100 hover:to-yellow-100'
          )
        case 'Низкий':
          return (
            baseClass +
            'bg-gradient-to-r from-emerald-50 via-emerald-25 to-green-50 border-emerald-400 hover:from-emerald-100 hover:to-green-100'
          )
        default:
          return (
            baseClass +
            'bg-gradient-to-r from-slate-50 to-slate-100 border-slate-300 hover:from-slate-100 hover:to-slate-200'
          )
      }
    },
    getTicketDotClass(priority) {
      const baseClass = 'w-3 h-3 rounded-full shadow-sm '

      switch (priority) {
        case 'Высокий':
          return baseClass + 'bg-red-500 animate-pulse shadow-red-200'
        case 'Средний':
          return baseClass + 'bg-amber-500 shadow-amber-200'
        case 'Низкий':
          return baseClass + 'bg-emerald-500 shadow-emerald-200'
        default:
          return baseClass + 'bg-slate-400 shadow-slate-200'
      }
    },
    getPriorityClass(priority) {
      const baseClass = 'px-3 py-1.5 text-xs font-bold rounded-full shadow-sm border '

      switch (priority) {
        case 'Высокий':
          return baseClass + 'bg-red-100 text-red-800 border-red-200'
        case 'Средний':
          return baseClass + 'bg-amber-100 text-amber-800 border-amber-200'
        case 'Низкий':
          return baseClass + 'bg-emerald-100 text-emerald-800 border-emerald-200'
        default:
          return baseClass + 'bg-slate-100 text-slate-800 border-slate-200'
      }
    },
    getStatusClass(status) {
      const baseClass = 'px-3 py-1.5 text-xs font-semibold rounded-full shadow-sm border '

      switch (status) {
        case 'В работе':
          return baseClass + 'bg-blue-100 text-blue-800 border-blue-200'
        case 'Ожидание':
          return baseClass + 'bg-yellow-100 text-yellow-800 border-yellow-200'
        case 'Назначен':
          return baseClass + 'bg-purple-100 text-purple-800 border-purple-200'
        case 'Критический':
          return baseClass + 'bg-red-100 text-red-800 border-red-200'
        default:
          return baseClass + 'bg-slate-100 text-slate-800 border-slate-200'
      }
    },
    getTimeRemainingClass(timeRemaining) {
      if (timeRemaining.includes('Просрочен')) {
        return 'text-red-700 bg-red-50 px-2 py-1 rounded-md border border-red-200'
      } else if (timeRemaining.includes('час')) {
        return 'text-orange-700 bg-orange-50 px-2 py-1 rounded-md border border-orange-200'
      } else {
        return 'text-slate-600'
      }
    },
  },
  mounted() {
    this.fetchHierarchy()
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
</style>
