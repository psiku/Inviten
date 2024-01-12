export const getDayName = (date: Date) =>
    `${date.toLocaleDateString('en-US', {
        weekday: 'short',
        month: 'numeric',
        day: 'numeric',
    })}`;

export const getHour = (date: Date) =>
    `${String(date.getHours()).padStart(2, '0')}:${String(
        date.getMinutes(),
    ).padStart(2, '0')}`;
