import { createBrowserRouter } from 'react-router-dom';

//pages
import Home from '../pages/Home';

const router = createBrowserRouter([
    //every object represents one route
    {
        path: '/',//pathname
        element: <Home />//JSX element (component)

    }
])

export default router;