"use client"
import { useState } from 'react';
import { HiHome, HiCash, HiUserGroup } from 'react-icons/hi';
import Link from 'next/link';
import { RootState } from '@/store/store';
import { useAppDispatch, useAppSelector } from '@/store/hooks/hooks';
import { setActive } from '@/store/features/sidebarState';

export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);

  const active = useAppSelector((state: RootState) => state.sidebarReducer.active);
  const dispatch = useAppDispatch();

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  const handleActive = (name: string) => {
    dispatch(setActive(name));
  }


  return (
    <aside
      className={`bg-white text-gray-800 ${
        isOpen ? 'w-64' : 'w-12'
      } min-h-screen transition-all duration-300 ease-in-out focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500`}
    >
      <div className="flex items-center justify-between p-4">
        <Link className="flex items-center" href="/">
          {!isOpen && <span className="text-xl font-bold"></span>}
          {isOpen && <span className="text-xl font-bold">
            Tracker Finansowy
            </span>}
        </Link>
        <button
          onClick={toggleSidebar}
          className="text-gray-400 hover:text-gray-100 focus:text-gray-100 focus:outline-none"
        >
          <svg
            className={`h-6 w-6 transition-transform duration-300 transform ${
              isOpen ? 'rotate-0 ml-4' : '-rotate-90 -ml-1'
            }`}
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d={isOpen ? 'M6 18L18 6M6 6l12 12' : 'M4 6h16M4 12h16M4 18h16'}
            />
          </svg>
        </button>
      </div>
      {isOpen && (
        <nav>
          <ul className="px-2 py-4">
            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${
                active === 'home' ? 'bg-gray-200 text-gray-800' : ''
              }`}
            >
              <HiHome className="h-6 w-6 text-indigo-600" />
              <Link href="/dashboard" className="hover:text-gray-800 rounded" onClick={() => {
                handleActive('home');
              }}>
                Home
              </Link>
            </li>
            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${
                active === 'expenses' ? 'bg-gray-200 text-gray-800' : ''
              }`}
            >
              <HiCash className="h-6 w-6 text-indigo-600" />
              <Link href="/dashboard/finance" className="hover:text-gray-800" onClick={() => {
                handleActive('expenses');
              }}>
                Wydatki
              </Link>
            </li>
            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${
                active === 'family' ? 'bg-gray-200 text-gray-800' : ''
              }`}
            >
              <HiUserGroup className="h-6 w-6 text-indigo-600" />
              <Link href="/dashboard/family" className="hover:text-gray-800" onClick={() => {
                handleActive('family');
              }}>
                Rodzina
              </Link>
            </li>
          </ul>
        </nav>
      )}
    </aside>
  );
}