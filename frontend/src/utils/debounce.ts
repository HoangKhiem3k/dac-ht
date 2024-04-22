export const debounce = (callback: Function, delay: number) => {
    let timerId: ReturnType<typeof setTimeout> | null;
    return (...args: any[]) => {
      if (timerId) clearTimeout(timerId);
      timerId = setTimeout(() => {
        callback(...args);
        timerId = null;
      }, delay);
    };
  };
  