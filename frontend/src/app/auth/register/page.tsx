"use client"
import { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { HiOutlineMail, HiOutlineLockClosed } from 'react-icons/hi';
import Link from 'next/link';
import { IData } from '@/types/typesRegister';


import styles from './Register.module.css';

export default function Page() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<IData>();
  const [loading, setLoading] = useState(false);

  const onSubmit: SubmitHandler<IData> = async (data) => {
    setLoading(true);
    try {
      const res = await fetch('/api/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });
      const json = await res.json();
      if (!res.ok) throw Error(json.message);
      console.log(json);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="min-h-screen flex items-center justify-center bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8 bg-white p-8 rounded shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">Stwórz konto</h2>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit(onSubmit)}>
          <div className="rounded-md shadow-sm -space-y-px">
            <div className="flex flex-col gap-2">
              <label htmlFor="email-address" className="sr-only">
                Adres email
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <HiOutlineMail className="h-5 w-5 text-gray-400" />
                </div>
                <input
                  id="email-address"
                  {...register('email', { required: true })}
                  type="email"
                  autoComplete="email"
                  required
                  className={`appearance-none rounded-none relative block w-full px-3 py-2 border ${
                    errors.email ? 'border-red-300' : 'border-gray-300'
                  } placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm ${styles.input}`}
                  placeholder="Adres email"
                />
                {errors.email && <span className="text-red-500 text-sm">To pole jest wymagane</span>}
              </div>
              <div>
                <label htmlFor="password" className="sr-only">
                  Hasło
                </label>
                <div className="relative mb-2">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <HiOutlineLockClosed className="h-5 w-5 text-gray-400" />
                  </div>
                  <input
                    id="password"
                    {...register('password', { required: true })}
                    type="password"
                    autoComplete="new-password"
                    required
                    className={`appearance-none rounded-none relative block w-full px-3 py-2 border ${
                      errors.password ? 'border-red-300' : 'border-gray-300'
                    } placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm`}
                    placeholder="Hasło"
                  />
                </div>
                {errors.password && <span className="text-red-500 text-sm">To pole jest wymagane</span>}
              </div>
            </div>
          </div>
          <div className="flex items-center justify-between">
            <div className="text-sm mb-2">
              <Link href="/auth/reset" className="font-medium text-indigo-600 hover:text-indigo-500">
                Zapomniałeś hasła?
              </Link>
            </div>
          </div>
          <div>
            <button
              type="submit"
              disabled={loading}
              className={`group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-gradient-to-r from-indigo-600 to-purple-500 hover:from-indigo-700 hover:to-purple-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${
                loading && 'opacity-50 cursor-not-allowed'
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
              {loading ? 'Registering...' : 'Register'}
            </button>
          </div>
        </form>
        <div className="flex items-center justify-center mt-4">
          <p className="text-sm text-gray-600">
            Masz juz konto?{' '}
            <Link href="/auth/login" className="font-medium text-indigo-600 hover:text-indigo-500">
              Zaloguj się
            </Link>
          </p>
        </div>
      </div>
    </section>
  );
}
