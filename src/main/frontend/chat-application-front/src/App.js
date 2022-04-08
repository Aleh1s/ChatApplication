import {Route, Routes} from "react-router-dom";
import NavBar from "./page/NavBar";
import RegistrationPage from "./page/RegistrationPage";
import NotFoundedPage from "./page/NotFoundedPage";
import LoginPage from "./page/LoginPage";
import UsersPage from "./page/UsersPage";
import ChatRoom from "./page/ChatRoom";
import ChatsPage from "./page/ChatsPage";

function App() {

  return (
      <div>
        <NavBar />
        <Routes>
          <Route path={'/register'} element={<RegistrationPage />}/>
          <Route path={'/login'} element={<LoginPage />}/>
          <Route path={'/users'} element={<UsersPage />}/>
          <Route path={'/chat-room'} element={<ChatRoom />}/>
          <Route path={'/profile'} element={<NotFoundedPage />}/>
          <Route path={'/chats'} element={<ChatsPage />}/>
          <Route path={'*'} element={<NotFoundedPage />}/>
        </Routes>
      </div>
  );
}

export default App;
