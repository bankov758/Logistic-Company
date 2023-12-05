import { createBrowserRouter } from 'react-router-dom';

//root layout
import RootLayout from '../components/Root';

//pages
import Home from '../pages/Home';
import Auth from '../pages/Auth';
import ErrorPage from '../components/Error';


const router = createBrowserRouter([
    {
        path: '/',
        element: <RootLayout />,
        errorElement: <ErrorPage />,
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