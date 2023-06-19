"use client"
import { useState } from 'react';
import { HiHome, HiCash, HiUserGroup, HiMenuAlt3, HiX, HiPlus } from 'react-icons/hi';
import Link from 'next/link';
import { RootState } from '@/store/store';
import { useAppDispatch, useAppSelector } from '@/store/hooks/hooks';
import { setActive } from '@/store/features/sidebarState';
import Image from 'next/image';


export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);

  const active = useAppSelector((state: RootState) => state.sidebarReducer.active);
  const dispatch = useAppDispatch();

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  const handleActive = (name: string) => {
    dispatch(setActive(name));
  };

  return (
    <aside
      className={`bg-white text-black ${isOpen ? 'w-48' : 'w-12'
        } min-h-screen transition-all duration-300 ease-in-out focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 fixed`}
    >
      <div className="flex items-center justify-between p-4">
        <Link className="flex items-center" href="/">
          {!isOpen && <span className="text-xl font-bold"></span>}
          {isOpen && (
            <span className="text-xl font-bold">Tracker Finansowy</span>
          )}
        </Link>
        <button
          onClick={toggleSidebar}
          className={`text-gray-400 hover:text-gray-100 focus:text-gray-100 focus:outline-none ${isOpen ? 'ml-2' : ''
            }`}
        >
          {isOpen ? (
            <HiX className="h-6 w-6 text-gray-400 ml-4 mt-1" />
          ) : (
            <HiMenuAlt3 className="h-6 w-6 text-gray-400" />
          )}
        </button>
      </div>
      {isOpen && (
        <nav>
          <ul className="px-2 py-4">
            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${active === 'home' ? 'bg-gray-200 text-gray-800' : ''
                }`}
            >
              <HiHome className="h-6 w-6 text-indigo-600" />
              <Link
                href="/dashboard"
                className="hover:text-gray-800 rounded"
                onClick={() => {
                  handleActive('home');
                }}
              >
                Home
              </Link>
            </li>
            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${active === 'expenses' ? 'bg-gray-200 text-gray-800' : ''
                }`}
            >
              <HiCash className="h-6 w-6 text-indigo-600" />
              <Link
                href="/dashboard/finance"
                className="hover:text-gray-800"
                onClick={() => {
                  handleActive('expenses');
                }}
              >
                Wydatki
              </Link>
            </li>
            {active === 'expenses' && (
              <>
                <li
                  className="flex items-center justify-end space-x-2 p-2 hover:bg-gray-300 rounded"
                >
                  <HiPlus className="h-5 w-5 text-indigo-600" />
                  <Link
                    href="/dashboard/finance/add-expense"
                    className="text-sm hover:text-gray-800"
                  >
                    Dodaj wydatek
                  </Link>
                </li>
                <li
                  className="flex items-center justify-end space-x-2 p-2 hover:bg-gray-300 rounded"
                >
                  <HiPlus className="h-5 w-5 text-indigo-600" />
                  <Link
                    href="/dashboard/finance/add-income"
                    className="text-sm hover:text-gray-800"
                  >
                    Dodaj dochody
                  </Link>
                </li>
              </>
            )}

            <li
              className={`flex items-center space-x-2 p-2 hover:bg-gray-300 rounded ${active === 'family' ? 'bg-gray-200 text-gray-800' : ''
                }`}
            >
              <HiUserGroup className="h-6 w-6 text-indigo-600" />
              <Link
                href="/dashboard/family"
                className="hover:text-gray-800"
                onClick={() => {
                  handleActive('family');
                }}
              >
                Rodzina
              </Link>
            </li>
          </ul>
          <div className="px-4 py-2 border-t mt-auto">
            <div className="flex items-center space-x-3">
              <Image
                src="/images/avatar.png"
                alt="avatar"
                width={40}
                height={40}
                className="w-8 h-8 rounded-full object-cover"
              />
              <span className="text-sm font-medium">Barbados King</span>
            </div>
          </div>
        </nav>
      )}
    </aside>
  );
}
