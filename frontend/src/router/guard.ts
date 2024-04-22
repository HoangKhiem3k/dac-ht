import { useAuthStore } from "@/stores/auth";

export function registerGuard(router) {
	router.beforeEach(async (to, from) => {
		if (to.meta.requiresAuth) {
			const store = useAuthStore();
            const { checkAuth } = store;
            const shouldProceed = await checkAuth();
			return shouldProceed || "login";
		}
		return true;
	});
}
