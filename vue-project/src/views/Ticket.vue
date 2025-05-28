<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок страницы -->
      <div class="mb-8">
        <h1 class="text-2xl font-bold text-slate-900 mb-2">Тикеты</h1>
      </div>

      <!-- Фильтры и поиск -->
      <div class="bg-white rounded-xl shadow-lg border border-slate-200 mb-6">
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
                d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"
              />
            </svg>
            Фильтры
          </h2>
        </div>
        <div class="p-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2">Поиск</label>
            <div class="relative">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Поиск по названию, отделу или команде..."
                class="w-full rounded-lg border border-slate-300 pl-10 pr-4 py-2 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200"
              />
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg
                  class="w-4 h-4 text-slate-400"
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
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2">Приоритет</label>
            <select
              v-model="priorityFilter"
              class="w-full rounded-lg border border-slate-300 px-4 py-2 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 appearance-none bg-white"
            >
              <option value="">Все приоритеты</option>
              <option value="Высокий">Высокий</option>
              <option value="Средний">Средний</option>
              <option value="Низкий">Низкий</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2">Статус</label>
            <select
              v-model="statusFilter"
              class="w-full rounded-lg border border-slate-300 px-4 py-2 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 appearance-none bg-white"
            >
              <option value="">Все статусы</option>
              <option value="В работе">В работе</option>
              <option value="Ожидание">Ожидание</option>
              <option value="Назначен">Назнач��н</option>
              <option value="Критический">Критический</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Список тикетов -->
      <div class="bg-white rounded-xl shadow-lg border border-slate-200">
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
              <span class="ml-2 text-sm text-slate-500">— {{ filteredTickets.length }} шт.</span>
            </h2>
          </div>
        </div>

        <div class="p-6">
          <div v-if="filteredTickets.length" class="space-y-4">
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
              {{ tickets.length ? 'Нет тикетов, соответствующих фильтрам' : 'Тикеты не найдены' }}
            </p>
          </div>

          <!-- Пагинация -->
          <div v-if="filteredTickets.length > 0" class="mt-6 flex justify-center">
            <nav class="flex items-center space-x-1">
              <button
                class="px-3 py-1.5 text-sm font-medium text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-md transition-all duration-200"
              >
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 19l-7-7 7-7"
                  />
                </svg>
              </button>
              <button class="px-3 py-1.5 text-sm font-medium bg-blue-100 text-blue-700 rounded-md">
                1
              </button>
              <button
                class="px-3 py-1.5 text-sm font-medium text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-md transition-all duration-200"
              >
                2
              </button>
              <button
                class="px-3 py-1.5 text-sm font-medium text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-md transition-all duration-200"
              >
                3
              </button>
              <button
                class="px-3 py-1.5 text-sm font-medium text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-md transition-all duration-200"
              >
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5l7 7-7 7"
                  />
                </svg>
              </button>
            </nav>
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
import Header from '@/components/Header.vue'
import TicketForm from '@/components/admin/TicketForm.vue'

export default {
  name: 'TicketsPage',
  components: {
    Header,
    TicketForm,
  },
  data() {
    return {
      tickets: [],
      searchQuery: '',
      priorityFilter: '',
      statusFilter: '',
      showTicketForm: false,
      currentTicket: null,
      nextTicketId: 1,
      isLoading: false,
    }
  },
  computed: {
    filteredTickets() {
      return this.tickets.filter((ticket) => {
        // Поиск по тексту
        const matchesSearch =
          !this.searchQuery ||
          ticket.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          ticket.department.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          ticket.team.toLowerCase().includes(this.searchQuery.toLowerCase())

        // Фильтр по приоритету
        const matchesPriority = !this.priorityFilter || ticket.priority === this.priorityFilter

        // Фильтр по статусу
        const matchesStatus = !this.statusFilter || ticket.status === this.statusFilter

        return matchesSearch && matchesPriority && matchesStatus
      })
    },
    highPriorityCount() {
      return this.tickets.filter((ticket) => ticket.priority === 'Высокий').length
    },
    criticalCount() {
      return this.tickets.filter((ticket) => ticket.status === 'Критический').length
    },
  },
  methods: {
    async fetchTickets() {
      this.isLoading = true

      try {
        // Здесь должен быть запрос к API
        // const response = await axios.get(`${import.meta.env.VITE_API_URL}/tickets`);
        // this.tickets = response.data;

        // Демо-данные для примера
        this.tickets = [
          {
            id: this.nextTicketId++,
            title: 'Ошибка авторизации в системе',
            description: 'Пользователи не могут войти в систему',
            priority: 'Высокий',
            status: 'В работе',
            department: 'IT отдел',
            team: 'Backend команда',
            timeRemaining: '2 часа',
            deadline: '2024-01-15T18:00:00',
            departmentId: 1,
            teamId: 4,
          },
          {
            id: this.nextTicketId++,
            title: 'Запрос на добавление темной темы',
            description: 'Пользователи просят добавить темную тему в интерфейс',
            priority: 'Средний',
            status: 'Ожидание',
            department: 'Разработка',
            team: 'Frontend команда',
            timeRemaining: '1 день',
            deadline: '2024-01-20T12:00:00',
            departmentId: 2,
            teamId: 3,
          },
          {
            id: this.nextTicketId++,
            title: 'Проблема с загрузкой файлов',
            description: 'Файлы загружаются медленно или не загружаются вовсе',
            priority: 'Низкий',
            status: 'Назначен',
            department: 'QA отдел',
            team: 'Тестирование',
            timeRemaining: '3 дня',
            deadline: '',
            departmentId: 3,
            teamId: 6,
          },
          {
            id: this.nextTicketId++,
            title: 'Критическая ошибка в базе данных',
            description: 'База данных периодически недоступна',
            priority: 'Высокий',
            status: 'Критический',
            department: 'IT отдел',
            team: 'DevOps команда',
            timeRemaining: 'Просрочен на 30 минут',
            deadline: '2024-01-10T10:00:00',
            departmentId: 1,
            teamId: 5,
          },
          {
            id: this.nextTicketId++,
            title: 'Обновление документации API',
            description: 'Необходимо обновить документацию API для новых эндпоинтов',
            priority: 'Средний',
            status: 'Назначен',
            department: 'Р��зработка',
            team: 'Backend команда',
            timeRemaining: '5 дней',
            deadline: '2024-01-25T18:00:00',
            departmentId: 2,
            teamId: 4,
          },
        ]
      } catch (error) {
        console.error('Ошибка при загрузке тикетов:', error)
      } finally {
        this.isLoading = false
      }
    },
    openCreateForm() {
      this.currentTicket = {
        id: null,
        title: '',
        description: '',
        priority: 'Средний',
        deadline: '',
        departmentId: '',
        teamId: '',
      }
      this.showTicketForm = true
    },
    editTicket(ticket) {
      // Преобразуем дедлайн обратно в формат datetime-local
      let deadline = ''
      if (ticket.deadline) {
        const date = new Date(ticket.deadline)
        deadline = date.toISOString().slice(0, 16)
      }

      this.currentTicket = {
        ...ticket,
        deadline,
      }
      this.showTicketForm = true
    },
    closeTicketForm() {
      this.showTicketForm = false
      this.currentTicket = null
    },
    saveTicket(ticketData) {
      if (ticketData.id) {
        // Редактирование существующего тикета
        const index = this.tickets.findIndex((t) => t.id === ticketData.id)
        if (index !== -1) {
          // Добавляем дополнительные поля для отображения
          const updatedTicket = {
            ...ticketData,
            status: this.tickets[index].status, // Сохраняем текущий статус
            timeRemaining: this.calculateTimeRemaining(ticketData.deadline),
          }
          this.tickets.splice(index, 1, updatedTicket)
        }
      } else {
        // Создание нового тикета
        const newTicket = {
          ...ticketData,
          id: this.nextTicketId++,
          status: 'Назначен',
          timeRemaining: this.calculateTimeRemaining(ticketData.deadline),
        }
        this.tickets.push(newTicket)
      }

      this.closeTicketForm()

      // Показываем уведомление об успехе
      this.$nextTick(() => {
        // Здесь можно добавить toast уведомление
        console.log(ticketData.id ? 'Тикет обновлен' : 'Тикет создан')
      })
    },
    deleteTicket(id) {
      if (confirm('Вы уверены, что хотите удалить этот тикет?')) {
        this.tickets = this.tickets.filter((t) => t.id !== id)
      }
    },
    calculateTimeRemaining(deadline) {
      if (!deadline) return 'Не указан'

      const now = new Date()
      const deadlineDate = new Date(deadline)
      const diff = deadlineDate - now

      if (diff < 0) {
        const hours = Math.floor(Math.abs(diff) / (1000 * 60 * 60))
        return `Просрочен на ${hours} час${hours === 1 ? '' : hours < 5 ? 'а' : 'ов'}`
      }

      const days = Math.floor(diff / (1000 * 60 * 60 * 24))
      const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))

      if (days > 0) {
        return `${days} день${days === 1 ? '' : days < 5 ? 'я' : 'ей'}`
      } else if (hours > 0) {
        return `${hours} час${hours === 1 ? '' : hours < 5 ? 'а' : 'ов'}`
      } else {
        const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
        return `${minutes} минут${minutes === 1 ? 'а' : minutes < 5 ? 'ы' : ''}`
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
    this.fetchTickets()
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

/* Анимация для пульсации */
@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.6;
  }
}

.animate-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
