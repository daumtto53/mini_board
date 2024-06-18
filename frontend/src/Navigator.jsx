import {
    createBrowserRouter,
    createRoutesFromElements,
    RouterProvider,
    Route,
} from "react-router-dom";

import Home from "./Home/Home";
import BoardWrite from "./BoardWrite/BoardWrite";
import RootLayout from "./RootLayout";
import RootErrorBoundary from "./RootErrorBoundary";

import BoardHome, { boardLoader as boardloader } from "./Board/BoardHome";
import BoardRead, { boardReadLoader } from "./BoardRead/BoardRead";
import { replyPostAction } from "./BoardRead/BoardReply";
import { Login } from "./Practice/googleOAuth2";

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route
                path="/"
                errorElement={<RootErrorBoundary />}
                element={<RootLayout />}
            >
                <Route index element={<Home />} />
                <Route
                    path="board"
                    element={<BoardHome />}
                    loader={boardloader}
                />
                <Route
                    path="write"
                    element={<BoardWrite />}
                    action={replyPostAction}
                />
                <Route
                    path="board/:postId"
                    element={<BoardRead />}
                    loader={boardReadLoader}
                    action={replyPostAction}
                />

				{/* Currently Not Used */}
				<Route
					path="login"
					element={<Login />}
				></Route>

            </Route>
        </>
    )
);

export default function Navigator() {
    return <RouterProvider router={router} />;
}
