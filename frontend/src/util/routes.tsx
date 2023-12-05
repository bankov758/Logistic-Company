import { createBrowserRouter } from 'react-router-dom';

//root layout
import RootLayout from '../components/Root';

//pages
import Home from '../pages/Home';
import Auth from '../pages/Auth';


const router = createBrowserRouter([
    {
        path: '/',
        element: <RootLayout />,
        children: [
        //every object represents one route
            {
                path: '/',//pathname
                element: <Home />//JSX element (component)
            },
            {
                path: '/auth',//pathname
                element: <Auth />//JSX element (component)
            }
        ]
    }
])

export default router;