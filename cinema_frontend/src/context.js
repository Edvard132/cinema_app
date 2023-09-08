import React, { useState, useContext, useEffect } from 'react'

const AppContext = React.createContext()

const AppProvider = ({ children }) => {
    const [authorized, setAuthorized] = useState(false)
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [isLogin, setIsLogin] = useState(false);
    const [userEmail, setUserEmail] = useState('');
    const [watchListId, setWatchListId] = useState(0);

    const closeModal = () => {
      setIsModalOpen(false);
    }

    const openModal = () => {
      setIsModalOpen(true);
  }

 

    return (
        <AppContext.Provider
          value={{ 
             authorized, setAuthorized,
              isModalOpen, closeModal, 
              openModal,
              isLogin, setIsLogin, 
              userEmail, setUserEmail,
              watchListId, setWatchListId,
            }}
        >
          {children}
        </AppContext.Provider>
      )
    }
    
    export const useGlobalContext = () => {
      return useContext(AppContext)
    }

export { AppContext, AppProvider }
