document.addEventListener('DOMContentLoaded', function () {
  const openBtn = document.getElementById('openBtn');
  const closeBtn = document.getElementById('closeBtn');
  const popup = document.getElementById('popup');
  const popupContent = popup.querySelector('.popup-content');

  // Открытие окна
  openBtn.addEventListener('click', function (event) {
    popup.style.display = 'flex';
  });

  // Закрытие по кнопке
  closeBtn.addEventListener('click', function () {
    popup.style.display = 'none';
  });

  // Закрытие при клике вне окна
  document.addEventListener('click', function (event) {
    const isClickInside = popupContent.contains(event.target);
    const isClickOnOpenBtn = openBtn.contains(event.target);

    // Если клик вне окна и popup открыт — закрыть
    if (!isClickInside && !isClickOnOpenBtn && popup.style.display === 'flex') {
      popup.style.display = 'none';
    }
  });
});








document.addEventListener('DOMContentLoaded', async function () {
  // ===================== ПОЛЬЗОВАТЕЛЬ =====================
  async function getCurrentUser() {
    try {
      const res = await fetch('/api/users/me');
      if (!res.ok) throw new Error('Не удалось получить данные');

      const user = await res.json();
      CURRENT_USER_ID = user.id;

      // Вставляем данные в DOM
      const nameField = document.querySelector('.popup-content_profile .user_name');
      const idField = document.querySelector('.popup-content_profile .id');
      const pointsField = document.querySelector('.popup-content_profile .point');

      if (nameField) nameField.textContent = user.fullName || user.username;
      if (idField) idField.textContent = user.id;
      if (pointsField && user.points !== undefined) pointsField.textContent = user.points;

      console.log('Текущий пользователь:', user);
    } catch (err) {
      console.error('Ошибка получения текущего пользователя', err);
      alert('Вы не авторизованы');
    }
  }


  // ***НОВЫЙ КОД***//
  // Универсальная функция загрузки друзей в заданный контейнер
  async function loadFriendsToContainer(userId, containerId) {
    try {
      const res = await fetch(`/api/friends/user/${userId}`);
      if (!res.ok) throw new Error('Ошибка загрузки');

      const friends = await res.json();
      const container = document.getElementById(containerId);
      container.innerHTML = '';

      if (friends.length === 0) {
        const p = document.createElement('p');
        p.textContent = containerId === 'friends-list'
            ? 'У вас пока нет друзей.'
            : 'Нет доступных друзей.';
        p.style.textAlign = 'center';
        container.appendChild(p);
        return;
      }

      friends.forEach(friend => {
        const div = document.createElement('div');
        div.className = containerId === 'friends-list' ? 'friend-item' : 'friend-item-in-task';
        div.textContent = friend.fullName || friend.username;

        if (containerId === 'friend-list-in-task') {
          const assignBtn = document.createElement('button');
          assignBtn.className = 'assign_friend_button';
          assignBtn.innerHTML = '<img src="/image/button_friends.svg" alt="Выбрать">';
          assignBtn.onclick = () => {
            alert(`Задача назначена ${friend.fullName || friend.username}`);
            document.getElementById('popup_friend_list').style.display = 'none';
          };
          div.appendChild(assignBtn);
        }

        container.appendChild(div);
      });
    } catch (err) {
      console.error('Ошибка загрузки друзей:', err);
      document.getElementById(containerId).innerHTML = '<p>Не удалось загрузить друзей.</p>';
    }
  }
  // ***КОНЕЦ НОВОГО КОДА***//

  // ===================== МОДАЛЬНЫЕ ОКНА =====================
  function setupPopup(openBtnId, closeBtnId, popupId, popupContentClass) {
    const openBtn = document.getElementById(openBtnId);
    const closeBtn = document.getElementById(closeBtnId);
    const popup = document.getElementById(popupId);
    const popupContent = popup.querySelector(popupContentClass);

    openBtn?.addEventListener('click', () => {
      popup.style.display = 'flex';
    });

    closeBtn?.addEventListener('click', () => {
      popup.style.display = 'none';
    });

    document.addEventListener('click', event => {
      const isClickInside = popupContent.contains(event.target);
      const isClickOnBtn = openBtn?.contains(event.target);

      if (!isClickInside && !isClickOnBtn && popup.style.display === 'flex') {
        popup.style.display = 'none';
      }
    });
  }

  setupPopup('openBtn', 'closeBtn', 'popup', '.popup-content');
  setupPopup('openBtnprofile', 'closeBtnprofile', 'popup_profile', '.popup-content_profile');

  // ===================== ДРУЖБА — В ОКНЕ ДОБАВЛЕНИЯ ЗАДАЧИ =====================
  const openFriendBtn = document.getElementById('openBtnfriend');
  const popupFriendList = document.getElementById('popup_friend_list');
  const closeFriendListBtn = document.getElementById('closeFriendListBtn');
  const popupCreate = document.getElementById('popup_create');

  // ***ИЗМЕНЁННЫЙ ОБРАБОТЧИК ОТКРЫТИЯ СПИСКА ДРУЗЕЙ В ОКНЕ ЗАДАЧИ***
  openFriendBtn?.addEventListener('click', () => {
    console.log("Кнопка открытия списка друзей внутри создания задачи нажата!");
    popupFriendList.style.display = 'block';

    // Загружаем друзей в контейнер списка для задачи
    if (CURRENT_USER_ID) {
      loadFriendsToContainer(CURRENT_USER_ID, 'friend-list-in-task');
    }
  });

  closeFriendListBtn?.addEventListener('click', () => {
    popupFriendList.style.display = 'none';
  });

  // ===================== ДОБАВЛЕНИЕ ЗАДАЧ =====================
  const addNewTaskButtons = document.querySelectorAll('.add_new_tasks');
  const closeBtnCreate = document.getElementById('closeBtnCreate');

  addNewTaskButtons.forEach(button => {
    button.addEventListener('click', () => {
      popupCreate.style.display = 'block';
    });
  });

  closeBtnCreate?.addEventListener('click', () => {
    popupCreate.style.display = 'none';
  });

  popupCreate?.addEventListener('click', (e) => {
    if (e.target === popupCreate) {
      popupCreate.style.display = 'none';
    }
  });

  // ===================== ИНИЦИАЛИЗАЦИЯ СТРАНИЦЫ =====================
  await getCurrentUser();

  const input = document.getElementById('friend-id-input');
  const addFriendBtn = document.getElementById('addFriendBtn');

  input?.addEventListener('keypress', e => {
    if (e.key === 'Enter' && CURRENT_USER_ID) {
      addFriend(CURRENT_USER_ID);
    }
  });

  addFriendBtn?.addEventListener('click', () => {
    if (CURRENT_USER_ID) {
      addFriend(CURRENT_USER_ID);
    }
  });

  // ***ИЗМЕНЁННЫЙ ОБРАБОТЧИК КНОПКИ ОТКРЫТИЯ СПИСКА ДРУЗЕЙ В ОСНОВНОМ ОКНЕ***
  document.getElementById('openBtn')?.addEventListener('click', () => {
    if (CURRENT_USER_ID) {
      loadFriendsToContainer(CURRENT_USER_ID, 'friends-list');
    }
  });

  // ===================== РЕДАКТИРОВАНИЕ ЗАДАЧ =====================
  const editBtns = document.querySelectorAll('.editing_button');

  editBtns.forEach(editBtn => {
    editBtn.addEventListener('click', function () {
      const taskContainer = this.closest('.editable-container');
      const editableTitle = taskContainer.querySelector('h1');
      const editableText = taskContainer.querySelector('.task h2');
      const editableDate = taskContainer.querySelector('.date');

      const isEditable = editableTitle.contentEditable === 'true';

      if (isEditable) {
        editableTitle.contentEditable = 'false';
        editableText.contentEditable = 'false';
        editableDate.contentEditable = 'false';
        this.innerHTML = '<img src="/front/index__main/pensil.svg" alt="editing_button">';
        taskContainer.classList.remove('editing');

        console.log('Новый заголовок:', editableTitle.textContent);
        console.log('Новый текст:', editableText.textContent);
      } else {
        editableTitle.contentEditable = 'true';
        editableText.contentEditable = 'true';
        editableDate.contentEditable = 'true';
        this.textContent = '✔️';
        taskContainer.classList.add('editing');
      }
    });
  });

  // ===================== ФУНКЦИИ ДОБАВЛЕНИЯ ДРУЗЕЙ =====================
  async function addFriend(userId) {
    const input = document.getElementById('friend-id-input');
    const friendId = input.value.trim();

    if (!friendId) {
      alert('Введите ID друга');
      return;
    }

    try {
      const res = await fetch(`/api/friends/user/${userId}/add/${friendId}`, {
        method: 'POST'
      });

      if (res.ok) {
        input.value = '';
        // Обновляем список друзей в основном окне после добавления
        loadFriendsToContainer(userId, 'friends-list');
      } else {
        const errorText = await res.text();
        alert(`Ошибка: ${errorText}`);
      }
    } catch (err) {
      console.error('Ошибка сети:', err);
      alert('Не удалось подключиться к серверу');
    }
  }
// Закрытие окна выбора друга при клике вне содержимого
  popupFriendList?.addEventListener('click', (e) => {
    const popupContent = popupFriendList.querySelector('.popup-content_friend');
    if (!popupContent.contains(e.target)) {
      popupFriendList.style.display = 'none';
    }
  });

});
