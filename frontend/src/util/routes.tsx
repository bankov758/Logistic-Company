import { createBrowserRouter } from 'react-router-dom';

//pages
import Home from '../pages/Home';
import Auth from '../pages/Auth';

const router = createBrowserRouter([
    //every object represents one route
    {
        path: '/',//pathname
        element: <Home />//JSX element (component)
    },
    {
        path: '/auth',//pathname
        element: <Auth />//JSX element (component)
    }
])

export default router;