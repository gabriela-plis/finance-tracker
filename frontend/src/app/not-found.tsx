"use client"
import { useRouter } from 'next/navigation';
import { HiArrowLeft } from 'react-icons/hi';

export default function Page() {
  const router = useRouter();

  const goBack = () => {
    router.back();
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="max-w-md w-full space-y-2 bg-white p-8 rounded shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">404 - Nie znaleziono strony</h2>
        </div>
        <div className="flex items-center justify-center mt-8">
          <button
            onClick={goBack}
            className={`group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm transition-all font-medium rounded-md text-white bg-gradient-to-r from-indigo-600 to-purple-500 hover:from-indigo-700 hover:to-purple-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500`}
          >
            <HiArrowLeft className="h-5 w-5" />
            <span>Wróć do poprzedniej strony</span>
          </button>
        </div>
      </div>
    </div>
  );
}
