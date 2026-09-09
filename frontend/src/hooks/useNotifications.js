import { useEffect, useState } from 'react';
import { getNotifications } from '../api/notifications.api';

export function useNotifications() { const [notifications, setNotifications] = useState([]); const [loading, setLoading] = useState(true); useEffect(() => { getNotifications().then(setNotifications).catch(() => setNotifications([])).finally(() => setLoading(false)); }, []); return { notifications, unreadCount: notifications.filter((item) => !item.lu).length, loading, setNotifications }; }
