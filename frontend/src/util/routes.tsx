import { createBrowserRouter } from 'react-router-dom';

//root layout
import RootLayout from '../components/Root';

//pages
import Home from '../pages/Home';
import Auth from '../pages/Auth';
import ProductPage from '../pages/Products';
import ProductDetails from '../pages/ProductDetails';
import ErrorPage from '../components/Error';


const router = createBrowserRouter([
    {
        path: '/',
        element: <RootLayout />,
        errorElement: <ErrorPage />,
        children: [
        //every object represents one route
        //using a relative paths, not an absolute path
            {
                index: true,//turns this route to so called index route (default route) that should be displayed if the parent route's path is currently loaded
                // path: '',//pathname
                element: <Home />,//JSX element (component)
            },
            {
                path: 'products',//pathname
                element: <ProductPage />,//JSX element (component)
            },
            {
                path: 'products/:productId',//dynamic pathname segment
                element: <ProductDetails />//JSX element (component)
            },
            {
                path: 'auth',//pathname
                element: <Auth />//JSX element (component)
            }
        ]
    }
])

export default router;