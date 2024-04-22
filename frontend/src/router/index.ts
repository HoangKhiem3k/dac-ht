import { createRouter, createWebHistory } from 'vue-router';
import { registerGuard } from './guard';
import routes from './routes';

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: routes
});

registerGuard(router);

export default router;
