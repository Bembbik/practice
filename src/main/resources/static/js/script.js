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












let CURRENT_USER_ID = null;

// ==== Получение текущего пользователя ====
async function getCurrentUser() {
  try {
    const res = await fetch('/api/users/me');
    if (!res.ok) throw new Error('Не удалось получить данные');

    const user = await res.json();
    CURRENT_USER_ID = user.id;
    console.log('Текущий пользователь:', user);
  } catch (err) {
    console.error('Ошибка получения текущего пользователя', err);
    alert('Вы не авторизованы');
  }
}

// ==== Загрузка друзей ====
async function loadFriends(userId) {
  try {
    const res = await fetch(`/api/friends/user/${userId}`);
    if (!res.ok) throw new Error('Ошибка загрузки');

    const friends = await res.json(); // получаем List<User>
    const container = document.getElementById('friends-list');
    container.innerHTML = '';

    if (friends.length === 0) {
      const p = document.createElement('p');
      p.textContent = 'У вас пока нет друзей.';
      p.style.textAlign = 'center';
      container.appendChild(p);
      return;
    }

    friends.forEach(friend => {
      const div = document.createElement('div');
      div.className = 'friend-item';
      div.textContent = friend.fullName || friend.username;
      container.appendChild(div);
    });

  } catch (err) {
    console.error('Не удалось загрузить друзей:', err);
    alert('Ошибка при загрузке списка друзей.');
  }
}

// ==== Добавление друга ====
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
      loadFriends(userId);
    } else {
      const errorText = await res.text();
      alert(`Ошибка: ${errorText}`);
    }
  } catch (err) {
    console.error('Ошибка сети:', err);
    alert('Не удалось подключиться к серверу');
  }
}

// ==== Инициализация событий ====
document.addEventListener('DOMContentLoaded', async () => {
  await getCurrentUser(); // Получаем ID текущего пользователя

  const input = document.getElementById('friend-id-input');
  const addFriendBtn = document.getElementById('addFriendBtn');

  if (input) {
    input.addEventListener('keypress', e => {
      if (e.key === 'Enter' && CURRENT_USER_ID) {
        addFriend(CURRENT_USER_ID);
      }
    });
  }

  if (addFriendBtn) {
    addFriendBtn.addEventListener('click', () => {
      if (CURRENT_USER_ID) {
        addFriend(CURRENT_USER_ID);
      }
    });
  }

  document.getElementById('openBtn')?.addEventListener('click', () => {
    if (CURRENT_USER_ID) {
      loadFriends(CURRENT_USER_ID);
    }
  });
});


/*-----------------------профиль-----------------------------*/
document.addEventListener('DOMContentLoaded', function () {
  const openBtnprofile = document.getElementById('openBtnprofile');
  const closeBtnprofile = document.getElementById('closeBtnprofile');
  const popup_profile = document.getElementById('popup_profile');
  const popupContent_profile = popup.querySelector('.popup-content_profile');

  // Открытие окна
  openBtnprofile.addEventListener('click', function (event) {
    popup_profile.style.display = 'flex';
  });

  // Закрытие по кнопке
  closeBtnprofile.addEventListener('click', function () {
    popup_profile.style.display = 'none';
  });

  // Закрытие при клике вне окна
  document.addEventListener('click', function (event) {
    const isClickInsideprofile = popupContent_profile.contains(event.target);
    const isClickOnOpenBtnprofile = openBtnprofile.contains(event.target);

    // Если клик вне окна и popup открыт — закрыть
    if (!isClickInsideprofile && !isClickOnOpenBtnprofile && popup_profile.style.display === 'flex') {
      popup_profile.style.display = 'none';
    }
  });
});



document.addEventListener('DOMContentLoaded', function() {
  const openBtnprofile = document.getElementById('openBtnprofile');
  const closeBtnprofile = document.getElementById('closeBtnprofile');


  openBtnprofile.addEventListener('click', function() {
    popup_profile.style.display = 'flex';
  });

  closeBtnprofile.addEventListener('click', function() {
    popup_profile.style.display = 'none';
  });
});
