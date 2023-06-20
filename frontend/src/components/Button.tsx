"use client";
import { IButton } from '@/types/typesButton';


export default function Button({ type, loading, buttonFunction }: IButton) {

    return (
        <button
            type={type}
            disabled={loading}
            className={`group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-gradient-to-r from-indigo-600 to-purple-500 hover:from-indigo-700 hover:to-purple-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${loading && 'opacity-50 cursor-not-allowed'
                }`}
        >
            <span className="absolute left-0 inset-y-0 flex items-center pl-3">
                <svg
                    className={`h-5 w-5 text-indigo-500 group-hover:text-indigo-400 ${loading && 'animate-spin'}`}
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
            </span>
            {loading ? 'Proszę czekać...' : buttonFunction === 'register' ? 'Zarejestruj się' : 'Zaloguj się'}
        </button>
    )
}