import {
    createBrowserRouter,
    createRoutesFromElements,
    RouterProvider,
    Route,
} from "react-router-dom";

import Home from "./Home/Home";
import BoardHome, { boardLoader as boardloader } from "./Board/BoardHome";
import BoardWrite from "./BoardWrite/BoardWrite";
import BoardRead, { boardReadLoader } from "./BoardRead/BoardRead";
import RootLayout from "./RootLayout";

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route path="/" element={<RootLayout />}>
                <Route index element={<Home />} />
                <Route
                    path="board"
                    loader={boardloader}
                    element={<BoardHome />}
                />
                <Route path="write" element={<BoardWrite />} />
				<Route
					path="board/:pageId"
					loader={boardReadLoader}
					element={<BoardRead />}
				/>
            </Route>
        </>
    )
);

export default function Navigator() {
    return <RouterProvider router={router} />;
}
