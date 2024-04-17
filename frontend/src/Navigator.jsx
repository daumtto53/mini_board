import {
    createBrowserRouter,
    Link,
    NavLink,
    createRoutesFromElements,
    RouterProvider,
    Route,
    Outlet,
} from "react-router-dom";

import Home from "./Home/Home";
import BoardHome, { boardLoader as boardloader } from "./Board/BoardHome";
import BoardWrite from "./BoardWrite/BoardWrite";
import RootLayout from "./RootLayout";

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route path="/" element={<RootLayout />}>
                <Route index element={<Home />} />
                <Route
                    path="board"
                    element={<BoardHome />}
                    loader={boardloader}
                />
                <Route path="write" element={<BoardWrite />} />
            </Route>
        </>
    )
);

export default function Navigator() {
    return <RouterProvider router={router} />;
}
