export const notificationStore = { unreadCount: (items = []) => items.filter((item) => !item.lu).length };
