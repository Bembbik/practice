<template>
  <div
    class="fixed inset-0 z-50 flex items-center justify-center p-4 backdrop-blur-md bg-slate-900/20 animate-fadeIn"
    @click.self="$emit('close')"
  >
    <div
      class="bg-white w-full max-w-2xl rounded-2xl shadow-2xl border border-slate-200 animate-slideUp max-h-[90vh] overflow-y-auto"
    >
      <!-- Заголовок -->
      <div
        class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100 rounded-t-2xl sticky top-0"
      >
        <div class="flex items-center justify-between">
          <h2 class="text-xl font-bold text-slate-900 flex items-center">
            <div
              class="w-8 h-8 bg-gradient-to-br from-blue-500 to-indigo-500 rounded-lg flex items-center justify-center mr-3 shadow-lg"
            >
              <svg class="w-4 h-4 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  v-if="editing"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                />
                <path
                  v-else
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
            </div>
            {{ editing ? 'Редактировать тикет' : 'Новый тикет' }}
          </h2>
          <button
            @click="$emit('close')"
            class="p-2 hover:bg-slate-200 rounded-lg transition-all duration-200 group"
          >
            <svg
              class="w-5 h-5 text-slate-400 group-hover:text-slate-600"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>
      </div>

      <!-- Форма -->
      <form @submit.prevent="handleSubmit" class="p-6">
        <div class="space-y-5">
          <!-- Заголовок тикета -->
          <div>
            <label class="block text-sm font-semibold text-slate-700 mb-2 flex items-center">
              <svg
                class="w-4 h-4 mr-1 text-slate-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"
                />
              </svg>
              Заголовок тикета
              <span class="text-red-500 ml-1">*</span>
            </label>
            <input
              v-model="formData.title"
              type="text"
              required
              class="w-full rounded-xl border border-slate-300 px-4 py-3 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 hover:border-slate-400"
              placeholder="Кратко опишите проблему или запрос"
            />
          </div>

          <!-- Описание -->
          <div>
            <label class="block text-sm font-semibold text-slate-700 mb-2 flex items-center">
              <svg
                class="w-4 h-4 mr-1 text-slate-500"
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
              Описание
              <span class="text-red-500 ml-1">*</span>
            </label>
            <textarea
              v-model="formData.description"
              required
              rows="4"
              class="w-full rounded-xl border border-slate-300 px-4 py-3 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 hover:border-slate-400 resize-vertical"
              placeholder="Подробно опишите проблему, шаги для воспроизведения, ожидаемый результат..."
            ></textarea>
          </div>

          <!-- Приоритет и Дедлайн -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-semibold text-slate-700 mb-2 flex items-center">
                <svg
                  class="w-4 h-4 mr-1 text-slate-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L4.082 16.5c-.77.833.192 2.5 1.732 2.5z"
                  />
                </svg>
                Приоритет
                <span class="text-red-500 ml-1">*</span>
              </label>
              <div class="relative">
                <select
                  v-model="formData.priority"
                  required
                  class="w-full rounded-xl border border-slate-300 px-4 py-3 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 hover:border-slate-400 appearance-none bg-white"
                >
                  <option value="Низкий">Низкий</option>
                  <option value="Средний">Средний</option>
                  <option value="Высокий">Высокий</option>
                </select>
                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
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
                      d="M19 9l-7 7-7-7"
                    />
                  </svg>
                </div>
              </div>
            </div>
            <div>
              <label class="block text-sm font-semibold text-slate-700 mb-2 flex items-center">
                <svg
                  class="w-4 h-4 mr-1 text-slate-500"
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
                Дедлайн
                <span class="text-slate-400 text-xs ml-1">(опционально)</span>
              </label>
              <input
                v-model="formData.deadline"
                type="datetime-local"
                class="w-full rounded-xl border border-slate-300 px-4 py-3 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 hover:border-slate-400"
              />
            </div>
            <!-- Команда -->
            <div v-if="!editing">
              <label class="block text-sm font-semibold text-slate-700 mb-2 flex items-center">
                <svg
                  class="w-4 h-4 mr-1 text-slate-500"
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
                Команда
                <span class="text-red-500 ml-1">*</span>
              </label>
              <div class="relative">
                <select
                  v-model="formData.teamId"
                  required
                  :disabled="!availableTeams.length"
                  class="w-full rounded-xl border border-slate-300 px-4 py-3 text-sm focus:border-blue-500 focus:ring-2 focus:ring-blue-200 focus:outline-none transition-all duration-200 hover:border-slate-400 appearance-none bg-white disabled:bg-slate-100 disabled:cursor-not-allowed"
                >
                  <option value="">Выберите команду</option>
                  <option v-for="team in availableTeams" :key="team.id" :value="team.id">
                    {{ team.name }}
                  </option>
                </select>
                <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
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
                      d="M19 9l-7 7-7-7"
                    />
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Кнопки действий -->
        <div class="mt-8 flex flex-col sm:flex-row justify-end space-y-3 sm:space-y-0 sm:space-x-3">
          <button
            type="button"
            @click="$emit('close')"
            class="px-6 py-3 bg-slate-100 hover:bg-slate-200 text-slate-700 font-medium rounded-xl transition-all duration-200 hover:shadow-md transform hover:scale-105 flex items-center justify-center"
          >
            <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
            Отмена
          </button>
          <button
            type="submit"
            :disabled="isSubmitting"
            class="px-6 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-medium rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 transform hover:scale-105 flex items-center justify-center disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <svg
              v-if="!isSubmitting"
              class="w-4 h-4 mr-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                v-if="editing"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 13l4 4L19 7"
              />
              <path
                v-else
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
            <svg v-else class="w-4 h-4 mr-2 animate-spin" fill="none" viewBox="0 0 24 24">
              <circle
                class="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                stroke-width="4"
              ></circle>
              <path
                class="opacity-75"
                fill="currentColor"
                d="m4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
              ></path>
            </svg>
            {{ isSubmitting ? 'Сохранение...' : editing ? 'Сохранить изменения' : 'Создать тикет' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'TicketForm',
  props: {
    ticket: {
      type: Object,
      default: () => ({
        id: null,
        title: '',
        description: '',
        priority: 'Средний',
        status: 'OPEN', // добавлено по умолчанию
        deadline: '',
        teamId: '',
      }),
    },
  },
  data() {
    return {
      isSubmitting: false,
      departments: [],
      formData: { ...this.ticket },
      availableTeams: [],
    }
  },
  computed: {
    editing() {
      return !!this.ticket && !!this.ticket.id
    },
  },
  availableTeams() {
    try {
      const teamsData = localStorage.getItem('teams')
      if (!teamsData) return []

      const teams = JSON.parse(teamsData)
      // Возвращаем только активные команды с id и name
      return teams.map((team) => ({
        id: team.id,
        name: team.name,
      }))
    } catch (error) {
      console.error('Ошибка при загрузке команд из localStorage:', error)
      return []
    }
  },
  watch: {
    ticket: {
      handler(newTicket) {
        this.formData = { ...newTicket }
      },
      deep: true,
      immediate: true,
    },
  },
  created() {
    this.loadDepartmentsFromStorage()
    this.loadTeamsFromLocalStorage()
  },
  methods: {
    loadTeamsFromLocalStorage() {
      try {
        const teamsData = localStorage.getItem('teams') // меняем 'teams_tickets' на 'teams'
        if (!teamsData) {
          console.warn('Команды не найдены в localStorage')
          this.availableTeams = []
          return
        }

        const teams = JSON.parse(teamsData)

        // Формируем список доступных команд
        this.availableTeams = teams.map((team) => ({
          id: team.id,
          name: team.name,
        }))
      } catch (error) {
        console.error('Ошибка при загрузке команд:', error)
        this.availableTeams = []
      }
    },

    loadDepartmentsFromStorage() {
      try {
        const ticketHierarchy = localStorage.getItem('ticketHierarchy')
        if (ticketHierarchy) {
          this.departments = JSON.parse(ticketHierarchy)
        } else {
          console.warn('Данные о структуре отделов и команд не найдены в localStorage')
          this.departments = []
        }
      } catch (error) {
        console.error('Ошибка при загрузке данных из localStorage:', error)
        this.departments = []
      }
    },
    async handleSubmit() {
      if (
        !this.formData.title ||
        !this.formData.description ||
        !this.formData.priority ||
        (!this.editing && !this.formData.teamId)
      ) {
        alert('Пожалуйста, заполните все обязательные поля')
        return
      }

      this.isSubmitting = true
      try {
        const submitData = { ...this.formData }

        // Переводим приоритет на английский
        const priorityMap = {
          Низкий: 'LOW',
          Средний: 'MEDIUM',
          Высокий: 'HIGH',
        }
        submitData.priority = priorityMap[submitData.priority]

        // Добавляем статус "OPEN"
        submitData.status = 'OPEN'

        // Форматируем дедлайн, если указан
        if (submitData.deadline) {
          const deadlineDate = new Date(submitData.deadline)
          submitData.deadline = deadlineDate.toISOString()
        }

        // Получаем departmentId из localStorage
        const user = JSON.parse(localStorage.getItem('user'))
        const departmentId = user?.departmentId

        if (!departmentId) {
          throw new Error('departmentId не найден в localStorage')
        }

        let url
        if (this.editing) {
          // PUT /department/{departmentId}/tickets/{ticketId}
          url = `${import.meta.env.VITE_API_URL}/department/${departmentId}/tickets/${submitData.id}`
        } else {
          // POST /department/{departmentId}/tickets/create
          url = `${import.meta.env.VITE_API_URL}/department/${departmentId}/tickets/create`
        }

        await axios({
          method: this.editing ? 'put' : 'post',
          url,
          data: submitData,
          headers: {
            'Content-Type': 'application/json',
          },
        })

        this.$emit('save', submitData)
      } catch (err) {
        console.error('Ошибка при сохранении тикета:', err)
        alert('Не удалось сохранить тикет. Попробуйте еще раз.')
      } finally {
        this.isSubmitting = false
      }
    },
  },
}
</script>

<style scoped>
/* Анимации */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.animate-fadeIn {
  animation: fadeIn 0.3s ease-out forwards;
}

.animate-slideUp {
  animation: slideUp 0.3s ease-out forwards;
}

/* Улучшенные стили для полей ввода */
input:focus,
select:focus,
textarea:focus {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.15);
}

/* Стили для кнопок */
button:active {
  transform: scale(0.98);
}

/* Кастомные стили для select */
select {
  background-image: none;
}

/* Стили для textarea */
textarea {
  min-height: 100px;
}

/* Стили для backdrop blur поддержки в старых браузерах */
@supports not (backdrop-filter: blur(12px)) {
  .backdrop-blur-md {
    background-color: rgba(15, 23, 42, 0.8);
  }
}

/* Стили для скролла */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}
</style>
