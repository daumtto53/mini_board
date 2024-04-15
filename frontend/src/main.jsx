import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import BoardRead from './BoardRead/BoardRead.jsx';
import Navigator from './Navigator.jsx';
import Base from './Base.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    {/*<BoardRead />*/}
	<Base />
	{/*<Navigator />*/}
  </React.StrictMode>,
)
