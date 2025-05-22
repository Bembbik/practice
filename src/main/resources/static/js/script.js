document.addEventListener('DOMContentLoaded', function() {
  const openBtn = document.getElementById('openBtn');
  const closeBtn = document.getElementById('closeBtn');
  
  openBtn.addEventListener('click', function() {
    popup.style.display = 'flex';
  });
  
  closeBtn.addEventListener('click', function() {
    popup.style.display = 'none';
  });
});
