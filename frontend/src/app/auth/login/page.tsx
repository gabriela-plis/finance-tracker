"use client";
import { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { HiOutlineMail, HiOutlineLockClosed } from 'react-icons/hi';
import { IData } from "@/types/typesLogin";
import Link from "next/link";

import styles from './Login.module.css';
import Button from "@/components/Button";


export default function Page() {
    const { register, handleSubmit, formState: { errors } } = useForm<IData>();
    const [loading, setLoading] = useState(false);

    const onSubmit: SubmitHandler<IData> = async (data) => {
        setLoading(true);
        try {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            });
            const json = await res.json();
            if (!res.ok) throw Error(json.message);
            console.log(json);
        }
        catch (e) {
            throw Error("Error login");
        }
        finally {
            setLoading(false);
        }
    }
    return (
        <section className={`min-h-screen flex items-center justify-center ${styles.login}`}>
          <div className={`max-w-md w-full space-y-8 bg-white p-8 rounded shadow-lg ${styles.loginContainer}`}>
            <div>
              <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">Zaloguj się do konta</h2>
            </div>
            <form className="mt-8 space-y-6" onSubmit={handleSubmit(onSubmit)}>
              <input type="hidden" name="remember" defaultValue="true" />
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
                    {errors.email && (
                      <span className={`${styles.error} text-red-500 text-sm`}>Te pole jest wymagane</span>
                    )}
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
                        autoComplete="current-password"
                        required
                        className={`appearance-none rounded-none relative block w-full px-3 py-2 border ${
                          errors.password ? 'border-red-300' : 'border-gray-300'
                        } placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm ${styles.input}`}
                        placeholder="Twoje hasło"
                      />
                    </div>
                    {errors.password && (
                      <span className={`${styles.error} text-red-500 text-sm`}>To pole jest wymagane</span>
                    )}
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
                  <Button type="submit" loading={loading} buttonFunction="login"/>
                </div>
              </div>
            </form>
          </div>
        </section>
      );
}